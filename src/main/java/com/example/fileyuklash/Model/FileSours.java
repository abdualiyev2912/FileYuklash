package com.example.fileyuklash.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileSours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileSoursId;

    @Column(nullable = false)
    private byte[] bytes;

    @OneToOne
    File file;
 }
