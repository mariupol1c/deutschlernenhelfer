package com.mariupol.deutschlernenhelfer.controllers;

import com.mariupol.deutschlernenhelfer.DeutschlernenhelferApplication;
import com.mariupol.deutschlernenhelfer.models.Nomen;
import com.mariupol.deutschlernenhelfer.models.Template;
import com.mariupol.deutschlernenhelfer.repo.NomenRepository;
import com.mariupol.deutschlernenhelfer.servises.Connector;
import com.mariupol.deutschlernenhelfer.servises.ParcingServises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NomensController {
    @Autowired
    private NomenRepository nomenRepository;

    @GetMapping("/nomens/add")
    public String nomenAdd(Model model){
        Nomen nomen = new Nomen();
        String suchen = DeutschlernenhelferApplication.getSuchende();
        if (!suchen.equals("")) {
            DeutschlernenhelferApplication.setSuchende("");
            Template temp = ParcingServises.getTemplate(suchen);
            nomen = Connector.getNomenFromTemplate(temp);
        }
        model.addAttribute("nomen", nomen);
        return "nomen-add";
    }

    @PostMapping("/nomens/add")
    public String nomenAddPost(@RequestParam String article, @RequestParam String name,
                               @RequestParam String plural, @RequestParam String translate, Model model){
        Nomen nom = new Nomen(article.trim(), name.trim(), plural.trim(), translate.trim());
        nomenRepository.saveNomen(nom);
        return "redirect:/nomens";
    }

    @GetMapping("/nomens/{id}/edit")
    public String nomenAdd(@PathVariable(value = "id") long id, Model model){
        if (!nomenRepository.existsById(id)){
            return "redirect:/nomens";
        }
        Nomen nomen = nomenRepository.findById(id).orElseThrow();
        model.addAttribute("nomen", nomen);
        return "nomen-edit";
    }

    @PostMapping("/nomens/{id}/edit")
    public String nomenEditPost(@PathVariable(value = "id") long id,@RequestParam String article, @RequestParam String name,
                                @RequestParam String plural, @RequestParam String translate, Model model){
        Nomen nom = nomenRepository.findById(id).orElseThrow();
        nom.setArticle(article.trim());
        nom.setName(name.trim());
        nom.setPlural(plural.trim());
        nom.setTranslate(translate.trim());
        nomenRepository.save(nom);
        return "redirect:/nomens";
    }

    @GetMapping("/nomens/{id}/delete")
    public String nomenDelete(@PathVariable(value = "id") long id, Model model){
        nomenRepository.deleteById(id);
        return "redirect:/nomens";
    }
}
