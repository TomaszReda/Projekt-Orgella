package pl.orgella.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.orgella.model.Product;
import pl.orgella.model.Zdjecia;
import pl.orgella.repository.ProductRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchDetailsForm {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/detailsSearch")
    public String details(Model model, @RequestParam Long ID) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("user",name);
        Collection<? extends GrantedAuthority> allss=auth.getAuthorities();
        GrantedAuthority admins=new SimpleGrantedAuthority("ADMIN_ROLE");
        if(allss.contains(admins))
        {
            model.addAttribute("admin","admin");
        }

        Product product = productRepository.getOne(ID);
        List<Zdjecia> zdjecia = product.getZdjeciaSet();
        Set<String> zd = new HashSet<>();
        for (Zdjecia z : zdjecia) {
            zd.add(z.getAdres());
        }
        model.addAttribute("zdjecia", zd);
        model.addAttribute("product", product);
        return "searchDetailsForm";

    }


    @GetMapping("/buy")
    public String buy(Model model, @RequestParam Long ID) {


        productRepository.deleteById(ID);


        return "redirect:succesBought";
    }

    @GetMapping("/succesBought")
    public String succes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("user",name);
        Collection<? extends GrantedAuthority> allss=auth.getAuthorities();
        GrantedAuthority admins=new SimpleGrantedAuthority("ADMIN_ROLE");
        if(allss.contains(admins))
        {
            model.addAttribute("admin","admin");
        }




        return "succesBoughtForm";
    }


}
