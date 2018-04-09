package pl.orgella.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.orgella.model.Product;

import java.util.Set;

@Repository
public interface  ProductRepository extends JpaRepository<Product,Long> {

    Set<Product> findAllByKategoria(String kategoria);
}
