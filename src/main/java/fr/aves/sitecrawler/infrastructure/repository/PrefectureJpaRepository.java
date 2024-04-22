package fr.aves.sitecrawler.infrastructure.repository;

import fr.aves.sitecrawler.domain.entity.Prefecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PrefectureJpaRepository extends JpaRepository<Prefecture, Long> {
}
