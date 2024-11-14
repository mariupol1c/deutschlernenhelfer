package com.mariupol.deutschlernenhelfer.controllers;

import com.mariupol.deutschlernenhelfer.DeutschlernenhelferApplication;
import com.mariupol.deutschlernenhelfer.models.Andere;
import com.mariupol.deutschlernenhelfer.models.Nomen;
import com.mariupol.deutschlernenhelfer.models.Verb;
import com.mariupol.deutschlernenhelfer.repo.AndereRepository;
import com.mariupol.deutschlernenhelfer.repo.NomenRepository;
import com.mariupol.deutschlernenhelfer.repo.VerbRepository;
import com.mariupol.deutschlernenhelfer.servises.ParcingServises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
@ConfigurationProperties(prefix = "deutsch")
public class MainController {

//    Work with properties files and some languages
    @Value("${pr}")
    private String pr;
//    System.out.println(pr);

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Locale locale;
//    System.out.println(messageSource.getMessage("prDE", null, locale));
//  End properties

    @Autowired
    private NomenRepository nomenRepository;
    @Autowired
    private VerbRepository verbRepository;
    @Autowired
    private AndereRepository andereRepository;
    private String message = "";

    @GetMapping("/")
    public String home(Model model) {
        message = "";
        model.addAttribute("message", message);
        return "home";
    }

    @PostMapping("/")
    public String homePost(@RequestParam(value = "wort") String wort, Model model) {
        String link = "redirect:/";
        String  wortRady= ParcingServises.prepareWord(wort);
        String wortType = ParcingServises.getType(wortRady);
        if (wortType == null) {
            message = "Ich kann nich das Wort \"" + wort + "\" finden.";
        }
        else{
            link = getLink(wortType);
            DeutschlernenhelferApplication.setSuchende(wortRady);
        }
        return link;
    }

    private String getLink(String wortType) {
        String link = "";
        if (wortType.equals("прилагательное")) link = "redirect:/anders/add";
        else if (wortType.equals("существительное")) link = "redirect:/nomens/add";
        else if (wortType.equals("глагол")) link = "redirect:/verbs/add";
        return link;
    }

    @GetMapping("/nomens")
    public String nomens(Model model) {
        List<Nomen> nomens = (List<Nomen>) nomenRepository.findAll();
        model.addAttribute("abName", "Nomens");
        model.addAttribute("nomens", nomens);
        return "nomens";
    }

    @GetMapping("/verbs")
    public String verbs(Model model) {
        List<Verb> verbs = (List<Verb>) verbRepository.findAll();
        model.addAttribute("verbs", verbs);
        return "verbs";
    }

    @GetMapping("/anders")
    public String anders(Model model) {
        List<Andere> anders = (List<Andere>) andereRepository.findAll();
        model.addAttribute("anders", anders);
        return "anders";
    }

    @GetMapping("/add")
    public String addFromFile(Model model) {
        return "add";
    }

}
