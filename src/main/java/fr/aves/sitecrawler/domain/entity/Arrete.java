package fr.aves.sitecrawler.domain.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Arrete {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(name = "description")
    String description;

    @Column(name = "date")
    Date date;
}

