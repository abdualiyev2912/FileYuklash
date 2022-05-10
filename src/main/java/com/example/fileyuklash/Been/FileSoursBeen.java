package com.example.fileyuklash.Been;

import com.example.fileyuklash.Model.FileSours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileSoursBeen extends JpaRepository<FileSours , Integer> {
    Optional<FileSours> findByFileSoursId(Integer fileSoursId);
}
