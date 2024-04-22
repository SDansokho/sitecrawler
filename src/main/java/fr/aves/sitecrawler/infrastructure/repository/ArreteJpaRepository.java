package fr.aves.sitecrawler.infrastructure.repository;

import fr.aves.sitecrawler.domain.entity.Arrete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ArreteJpaRepository extends JpaRepository<Arrete, Long> {
}
