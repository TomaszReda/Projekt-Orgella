package pl.orgella.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Length(min =10,max = 25,message = "{pl.orgella.model.product.length.10-25.message}")
    private String header;

    @Length(min =10,max = 200,message = "{pl.orgella.model.product.length.10-200.message}")
    private String opis;

    @Min(value = 1,message ="{pl.orgella.model.product.min.1.message}")
    private double cena;

    private String kategoria;

    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<Zdjecia> zdjeciaSet=new ArrayList<>();


}