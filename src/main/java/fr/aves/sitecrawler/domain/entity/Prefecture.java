package fr.aves.sitecrawler.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name = "prefecture", schema = "crawler")
public class Prefecture {
        @Column(name = "prefecture_id")
        @Id
        private Long prefectureId;

        @Column(name = "nom")
        private String nom;

        @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "prefecture")
        @JsonManagedReference
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
