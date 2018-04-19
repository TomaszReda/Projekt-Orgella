package pl.orgella.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.orgella.model.User;
import pl.orgella.repository.UserRepository;
import pl.orgella.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RegisterController {

    private UserService userService;

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;






    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new User());
        return "zalozkonto";
    }

    @PostMapping("/register")
    public String zalozkonto(@Valid @ModelAttribute User user, BindingResult bindingResult,Model model){




    if(bindingResult.hasErrors())
    return "zalozkonto";

     List<User> all= userRepository.findAll();
        for(User u:all)
        {
            if(u.getLogin().equals(user.getLogin()))
            {
                model.addAttribute("loginn","Konto o podanych Nicku juz istnieje");
                return "zalozkonto";
            }
            if(u.getEmail().equals(user.getEmail()))
            {
                model.addAttribute("emaill","Konto o podanych emailu juz istnieje ");
                return "zalozkonto";
            }
        }
        if(user.isRegulamin1()==false || user.isRegulamin2()==false)
        {
            model.addAttribute("regulamin","Musisz zaakceptowac regulamin");
            return "zalozkonto";
        }
        if(user.getPassword().length()>15)
        {
            model.addAttribute("badLength","Hasło musi miec min 9 a max 15 znaków");
            return "zalozkonto";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addwithDefaultRole(user);

        return "redirect:succes";

    }

    @GetMapping("/succes")
    public String succes()
    {
        return "pomyslnieZalozonoKonto";
    }



}
