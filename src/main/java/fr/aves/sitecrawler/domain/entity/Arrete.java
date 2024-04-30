package fr.aves.sitecrawler.domain.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "arrete", schema = "crawler")
public class Arrete {

    @Column(name = "arrete_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long arreteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prefecture_id")
    @JsonBackReference
    private Prefecture prefecture;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "url")
    private String url;
}

