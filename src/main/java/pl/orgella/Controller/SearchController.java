package pl.orgella.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.orgella.model.Product;
import pl.orgella.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class SearchController {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "0") int page, @RequestParam String kategoria, Model model)
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


        Page<Product> products=productRepository.findAllByKategoria(kategoria,new PageRequest(page,10));
        int ile = productRepository.findAllByKategoria(kategoria).size();

        if (ile % 10 == 0) {
            ile = ile / 10;
        } else {
            ile = ile / 10 + 1;
        }
        int tab[] = new int[ile];
        for (int i = 0; i < ile; i++) {
            tab[i] = i;

        }

        model.addAttribute("ile",tab);
        model.addAttribute("kategoria",kategoria);
        model.addAttribute("products", products);





        return "searchForm";
    }


    @GetMapping("/searchh")
    public String searchh(Model model,@RequestParam(defaultValue = "0") int page,@RequestParam(required = false) String word){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("user",name);
        Collection<? extends GrantedAuthority> allss=auth.getAuthorities();
        GrantedAuthority admins=new SimpleGrantedAuthority("ADMIN_ROLE");
        if(allss.contains(admins))
        {
            model.addAttribute("admin","admin");
        }

        String word2=word.toUpperCase();

        List<String> list = Arrays.asList(word2.split(" "));
        List<Product> allProducts = new ArrayList<>();



            allProducts = productRepository.findAll();



        List<Product> searchProducts = new ArrayList<>();
        if(!word.equals("")) {
            for (Product pp : allProducts) {
                String header = pp.getHeader();
                header = header.toUpperCase();
                List<String> headerList = Arrays.asList(header.split(" "));
                for (String s : headerList) {
                    if (list.contains(s)) {
                        searchProducts.add(pp);
                        break;
                    }
                }
            }
        }


        else {
            searchProducts=allProducts;
        }
        System.err.println("search"+searchProducts);




        Page<Product> alls = new PageImpl<>(searchProducts,new PageRequest(page,10),searchProducts.size());

        List<Product> all=new ArrayList<>();
        int n;
        int ilee=0;
        if(searchProducts.isEmpty())
        {
            ilee=0;
        }
        else {
            if(page==0)
            {
                if(searchProducts.size()<10)
                {
                    ilee=searchProducts.size()%10;
                }
                else
                {
                    ilee=10;
                }
            }
            else
            {
                if(searchProducts.size()<(page+1)*10)
                {
                    ilee=searchProducts.size()%10+page*10;
                }
                else
                {
                    ilee=(page+1)*10;
                }

            }
        }

        if(page==0)
        {
            n=0;
        }
        else {
            n=page*10;
        }
        for(int i=n;i<ilee;i++)
        {
            all.add(searchProducts.get(i));
        }



        int ile = searchProducts.size();

        if (ile % 10 == 0) {
            ile = ile / 10;
        } else {
            ile = ile / 10 + 1;
        }
        int tab[] = new int[ile];
        for (int i = 0; i < ile; i++) {
            tab[i] = i;

        }

        model.addAttribute("ile",tab);
        model.addAttribute("word",word);
        model.addAttribute("products", all);
        return "searchByWordForm";
    }


}
