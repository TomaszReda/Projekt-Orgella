package pl.orgella.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.orgella.model.User;
import pl.orgella.repository.UserRepository;

import java.util.Collection;

@Controller
public class MyAccountController {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/account")
    public String myAcount(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("user",name);
        Collection<? extends GrantedAuthority> all=auth.getAuthorities();
        GrantedAuthority admins=new SimpleGrantedAuthority("ADMIN_ROLE");
        if(all.contains(admins))
        {
            model.addAttribute("admin","admin");
        }


        return "MyAccountForm";
    }

    @PostMapping("/changePassword")
    public String change(Model model, @RequestParam String oldPassword,@RequestParam String newPassword1,@RequestParam String newPassword2)
    {




        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("user",name);
        Collection<? extends GrantedAuthority> all=auth.getAuthorities();
        GrantedAuthority admins=new SimpleGrantedAuthority("ADMIN_ROLE");
        if(all.contains(admins))
        {
            model.addAttribute("admin","admin");
        }
        User user=userRepository.findFirstByLogin(name);




        if(!BCrypt.hashpw(oldPassword,user.getPassword()).equals(user.getPassword()))
        {
            model.addAttribute("badOld","Złe hasło");
            return "MyAccountForm";
        }
        if(!newPassword1.equals(newPassword2))
        {
            model.addAttribute("NotTheSame","Hasła musza byc takie samo");
            return "MyAccountForm";
        }
        if(newPassword1.length()<9)
        {
            model.addAttribute("badLength","Hasło musi byc dłuzszse niz 9 znaków");
            return "MyAccountForm";
        }

        user.setPassword(passwordEncoder.encode(newPassword1));
        userRepository.save(user);
        model.addAttribute("succes","Pomyslnie zmieniono hasło");
        return "MyAccountForm";
    }




}
