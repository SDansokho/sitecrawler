package fr.aves.sitecrawler.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prefecture", schema = "crawler")
public class Prefecture {
        @Column(name = "prefecture_id")
        @Id
        private Long prefectureId;

        @Column(name = "nom")
        private String nom;

        @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "prefecture")
        private List<Arrete> arretes;

        @Column(name = "source")
        private ArreteSource source;

        @Column(name = "target_path")
        private String targetPagePath;

        @Column(name = "root_path")
        private String rootPath;

        @Column(name = "target_element")
        private String targetElement;

}
