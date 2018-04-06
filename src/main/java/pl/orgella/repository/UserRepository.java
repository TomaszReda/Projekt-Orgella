package pl.orgella.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.orgella.model.User;

@Repository
public interface  UserRepository extends JpaRepository<User,Long> {
    User findFirstByLogin(String login);


}
