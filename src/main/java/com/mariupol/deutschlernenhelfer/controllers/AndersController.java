package com.mariupol.deutschlernenhelfer.controllers;

import com.mariupol.deutschlernenhelfer.DeutschlernenhelferApplication;
import com.mariupol.deutschlernenhelfer.models.Andere;
import com.mariupol.deutschlernenhelfer.models.Template;
import com.mariupol.deutschlernenhelfer.repo.AndereRepository;
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
public class AndersController {
    @Autowired
    private AndereRepository andereRepository;

    @GetMapping("/anders/add")
    public String andereAdd(Model model) {
        Andere andere = new Andere();
        String suchen = DeutschlernenhelferApplication.getSuchende();
        if (!suchen.equals("")) {
            DeutschlernenhelferApplication.setSuchende("");
            Template temp = ParcingServises.getTemplate(suchen);
            andere = Connector.getAndere1FromTemplate(temp);
        }
        model.addAttribute("andere", andere);
        return "andere-add";
    }

    @PostMapping("/anders/add")
    public String andereAddPost(@RequestParam String wort, @RequestParam String translate, Model model) {
        wort = wort.replaceAll("   ", " ").replaceAll("  ", " ");
        Andere andere = new Andere(wort.trim(), getType(wort), translate.trim());
        andereRepository.save(andere);
        return "redirect:/anders";
    }

    private byte getType(String wort) {
        int count  = wort.split(" ").length;
        byte type;
        if (count == 1) type = 1;
        else type = 2;

        return type;
    }

    @GetMapping("/anders/{id}/edit")
    public String andereAdd(@PathVariable(value = "id") long id, Model model) {
        if (!andereRepository.existsById(id)) {
            return "redirect:/anders";
        }
        Andere andere = andereRepository.findById(id).orElseThrow();
        model.addAttribute("andere", andere);
        return "andere-edit";
    }

    @PostMapping("/anders/{id}/edit")
    public String andereEditPost(@PathVariable(value = "id") long id, @RequestParam String wort, @RequestParam String translate, Model model) {
        Andere andere = andereRepository.findById(id).orElseThrow();
        andere.setWort(wort.trim());
        andere.setTranslate(translate.trim());
        andere.setType(getType(wort));
        andereRepository.save(andere);
        return "redirect:/anders";
    }

    @GetMapping("/anders/{id}/delete")
    public String andereDelete(@PathVariable(value = "id") long id, Model model) {
        andereRepository.deleteById(id);
        return "redirect:/anders";
    }
}
