package fr.aves.sitecrawler.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
public class Prefecture {
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        Long id;

        @Column(name = "nom")
        String nom;

        @ManyToOne(targetEntity = Arrete.class)
        @JoinColumn(name = "arretes")
        List<Arrete> arretes;

        @Column(name = "source")
        ArreteSource source;

        @Column(name = "targetPath")
        String targetPath;

}
