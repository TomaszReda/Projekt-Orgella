package pl.orgella.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.orgella.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    UserRole findFirstByRole(String role);
}
