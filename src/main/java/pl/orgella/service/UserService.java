package pl.orgella.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.orgella.repository.UserRepository;
import pl.orgella.repository.UserRoleRepository;
import pl.orgella.model.User;
import pl.orgella.model.UserRole;

@Service
public class UserService {
    public static final String DEFAULT_ROLE="DEFAULT_USER";
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    public void addwithDefaultRole(User user)
    {
        UserRole userRole=userRoleRepository.findFirstByRole(DEFAULT_ROLE);

        user.getUserRole().add(userRole);
        userRepository.save(user);

    }
}
