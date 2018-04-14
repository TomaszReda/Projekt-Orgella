package pl.orgella.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.orgella.model.Product;


import java.util.List;
import java.util.Set;


public interface  ProductRepository extends JpaRepository<Product,Long> {

    Set<Product> findAllByKategoria(String kategoria);
    Page<Product> findAllByKategoria(String kageria, Pageable pageable);

}
