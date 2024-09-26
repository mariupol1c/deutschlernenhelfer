package com.mariupol.deutschlernenhelfer.controllers;

import com.mariupol.deutschlernenhelfer.models.Andere;
import com.mariupol.deutschlernenhelfer.models.Nomen;
import com.mariupol.deutschlernenhelfer.models.Verb;
import com.mariupol.deutschlernenhelfer.repo.AndereRepository;
import com.mariupol.deutschlernenhelfer.repo.NomenRepository;
import com.mariupol.deutschlernenhelfer.repo.VerbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private NomenRepository nomenRepository;

    @Autowired
    private VerbRepository verbRepository;
    @Autowired
    private AndereRepository andereRepository;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
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
        //model.addAttribute("abName", "Verbs");
        model.addAttribute("verbs", verbs);
        return "verbs";
    }

    @GetMapping("/anders")
    public String anders(Model model) {
        List<Andere> anders = (List<Andere>) andereRepository.findAll();
        model.addAttribute("anders", anders);
        return "anders";
    }

}
