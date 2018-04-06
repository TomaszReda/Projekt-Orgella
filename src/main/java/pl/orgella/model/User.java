package pl.orgella.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Length(min = 3,max = 12,message = "{pl.orgella.model.User.min.3-12.message}")
    private String firstname;
    @Length(min = 3,max = 12,message = "{pl.orgella.model.User.min.3-12.message}")
    private String lastname;
    @Email
    private String email;
    @Length(min=6,max=12,message = "{pl.orgella.model.User.min.6-12.message}")
    private String login;
    @Length(min = 9,max = 15,message = "{pl.orgella.model.User.min.9-15.message}")
    private String password ;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private Set<UserRole> userRole=new HashSet<>();

    public User() {
    }

    public User(@Length(min = 3, max = 12, message = "{pl.orgella.model.User.min.3-12.message}") String firstname, @Length(min = 3, max = 12, message = "{pl.orgella.model.User.min.3-12.message}") String lastname, @Email String email, @Length(min = 6, max = 12, message = "{pl.orgella.model.User.min.6-12.message}") String login, @Length(min = 9, max = 15, message = "{pl.orgella.model.User.min.9-15.message}") String password, boolean regulamin1, boolean regulamin2) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;

        this.regulamin1 = regulamin1;
        this.regulamin2 = regulamin2;
    }

    private boolean regulamin1;
    private  boolean regulamin2;

}
