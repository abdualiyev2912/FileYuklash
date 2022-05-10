package com.example.fileyuklash.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    @Column(nullable = false)
    private String originalFileNomi;

    @Column(nullable = false)
    private Long hajm;

    @Column(nullable = false)
    private String malumotTuri;


    private String nomi;

}
