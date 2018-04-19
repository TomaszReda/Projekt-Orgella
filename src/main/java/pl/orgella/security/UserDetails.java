package pl.orgella.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.orgella.model.User;
import pl.orgella.model.UserRole;
import pl.orgella.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;


public class UserDetails implements UserDetailsService {

    private UserRepository userRepository;



    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userRepository.findFirstByLogin(s);
        org.springframework.security.core.userdetails.User user1=new org.springframework.security.core.userdetails.User(
                user.getLogin(),
               user.getPassword(),
                convert(user.getUserRole())
        );
        return user1;
    }

    private Set<GrantedAuthority> convert(Set<UserRole> userRoleSet)
    {
        Set<GrantedAuthority> grantedAuthorities=new HashSet<>();
        for(UserRole u:userRoleSet)
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(u.getRole()));
        }
        return grantedAuthorities;
    }
}
