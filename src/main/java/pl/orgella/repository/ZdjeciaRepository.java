package pl.orgella.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.orgella.model.Zdjecia;

@Repository
public interface ZdjeciaRepository extends JpaRepository<Zdjecia,Long> {
}
