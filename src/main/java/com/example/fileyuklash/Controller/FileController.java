package com.example.fileyuklash.Controller;

import com.example.fileyuklash.Been.FileBeen;
import com.example.fileyuklash.Been.FileSoursBeen;
import com.example.fileyuklash.Model.File;
import com.example.fileyuklash.Model.FileSours;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.SneakyThrows;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileBeen fileBeen;

    @Autowired
    FileSoursBeen fileSoursBeen;

    @SneakyThrows

    @PostMapping("/yuklash")
    public String insertFile(MultipartHttpServletRequest request) throws IOException {
        System.out.println(System.currentTimeMillis());
        Iterator<String> nomlari = request.getFileNames();
        MultipartFile file = request.getFile(nomlari.next());

        List<File> fileList = fileBeen.findAll();
        for(File i : fileList){
            if (i.getOriginalFileNomi().equals(file.getOriginalFilename())) return "Bunday malumot mavjud";
        }

        if (file!=null){
            String fileNomi = file.getOriginalFilename();
            long hajmi = file.getSize();
            String turi = file.getContentType();
            byte[] bytes = file.getBytes();
            File file1 = new File();
            file1.setOriginalFileNomi(fileNomi);
            file1.setHajm(hajmi);
            file1.setMalumotTuri(turi);
            File file2 = fileBeen.save(file1);

            FileSours fileSours = new FileSours();
            fileSours.setBytes(bytes);
            fileSours.setFile(file2);
            fileSoursBeen.save(fileSours);
            System.out.println(System.currentTimeMillis());
            return "Malumot joylandi \nid = " + file2.getFileId();
        }
        return "Malumot topilmadi";
    }



    @GetMapping("/yuklash/{id}")
    public void yuklashFile(@PathVariable Integer id , HttpServletResponse response){
        Optional<File> optionalFile = fileBeen.findById(id);
        if(optionalFile.isPresent()){
            File file = optionalFile.get();
            Optional<FileSours> optionalFileSours = fileSoursBeen.findByFileSoursId(id);
            if(optionalFileSours.isPresent()){
                FileSours fileSours = optionalFileSours.get();
                response.setContentType(file.getMalumotTuri());
                //response.setHeader("Content-Disposition" , "attachment;filename = \""+file.getOriginalFileNomi()+"\"");
                response.setHeader("Content-Disposition" , "attachment:filename = \""+file.getOriginalFileNomi()+"\"");
                try {
                    FileCopyUtils.copy(fileSours.getBytes(), response.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    String manzil = "baza";
    @PostMapping("/yuklash/papka")
    public String papkagayuklash(MultipartHttpServletRequest request) throws IOException {
        System.out.println(System.currentTimeMillis());
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());

        if (file!=null){
           File file1 = new File();
           file1.setOriginalFileNomi(file.getOriginalFilename());
           file1.setHajm(file.getSize());
           file1.setMalumotTuri(file.getContentType());
           String yangiNom = file.getOriginalFilename();
           String[] split1 = yangiNom.split("\\.");
           String s = UUID.randomUUID().toString() + "." + split1[split1.length-1];
           file1.setNomi(s);
           fileBeen.save(file1);
           Path path = Paths.get(manzil+"/"+s);
           Files.copy(file.getInputStream(),path);
            System.out.println(System.currentTimeMillis());
           return "Joylandi!"+file1.getFileId();

        }
        return null;

    }

    @GetMapping("/yuklash/papka/{id}")
    public void papkadanYuklash(@PathVariable Integer id,HttpServletResponse response) throws IOException {
        Optional<File> optionalFile=fileBeen.findById(id);
        if (optionalFile.isPresent()){
            File file = optionalFile.get();
            response.setContentType(file.getMalumotTuri());
            response.setHeader("Content-Disposition" , " attachment; filename = \""+file.getOriginalFileNomi()+"\"");
            FileInputStream fileInputStream=new FileInputStream(manzil+"/"+file.getNomi());
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        }
    }


    @PostMapping("/yuklash/bazaandpapka")
    public String insertFileALl(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> nomlari = request.getFileNames();
        MultipartFile file = request.getFile(nomlari.next());

        List<File> fileList = fileBeen.findAll();
        for(File i : fileList){
            if (i.getOriginalFileNomi().equals(file.getOriginalFilename())) return "Bunday malumot mavjud";
        }

        if (file!=null){
            String fileNomi = file.getOriginalFilename();
            long hajmi = file.getSize();
            String turi = file.getContentType();
            byte[] bytes = file.getBytes();
            File file1 = new File();
            file1.setOriginalFileNomi(fileNomi);
            file1.setHajm(hajmi);
            file1.setMalumotTuri(turi);

            String yangiNom = file.getOriginalFilename();
            String[] split1 = yangiNom.split("\\.");
            String s = UUID.randomUUID().toString() + "." + split1[split1.length-1];
            file1.setNomi(s);
            File file2 = fileBeen.save(file1);
            FileSours fileSours = new FileSours();
            fileSours.setBytes(bytes);
            fileSours.setFile(file2);
            fileSoursBeen.save(fileSours);
            Path path = Paths.get(manzil+"/"+s);
            Files.copy(file.getInputStream(),path);
            return "Malumot joylandi \nid = " + file2.getFileId();
        }
        return "Malumot topilmadi";
    }

}

