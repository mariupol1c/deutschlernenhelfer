package com.mariupol.deutschlernenhelfer.controllers;

import com.mariupol.deutschlernenhelfer.models.Verb;
import com.mariupol.deutschlernenhelfer.repo.VerbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerbsController {
    @Autowired
    private VerbRepository verbRepository;

    @GetMapping("/verbs/add")
    public String verbAdd(Model model) {
        model.addAttribute("abName", "Add Verb");
        return "verb-add";
    }

    @PostMapping("/verbs/add")
    public String verbAddPost(@RequestParam String infinitive, @RequestParam String preteritum,
                              @RequestParam String partyzip2, @RequestParam String translate, Model model) {
        partyzip2 = partyzip2.replaceAll("   ", " ").replaceAll("  ", " ");
        Verb verb = new Verb(infinitive.trim(), preteritum.trim(), partyzip2.trim(), translate.trim());
        verbRepository.save(verb);
        return "redirect:/verbs";
    }

    @GetMapping("/verbs/{id}/edit")
    public String verbAdd(@PathVariable(value = "id") long id, Model model) {
        if (!verbRepository.existsById(id)) {
            return "redirect:/verbs";
        }
        Verb verb = verbRepository.findById(id).orElseThrow();
        model.addAttribute("verb", verb);
        return "verb-edit";
    }

    @PostMapping("/verbs/{id}/edit")
    public String verbEditPost(@PathVariable(value = "id") long id, @RequestParam String infinitive, @RequestParam String preteritum,
                               @RequestParam String partyzip2, @RequestParam String translate, Model model) {
        Verb verb = verbRepository.findById(id).orElseThrow();
        verb.setInfinitive(infinitive.trim());
        verb.setPreteritum(preteritum.trim());
        verb.setPartyzip2(partyzip2.trim());
        verb.setTranslate(translate.trim());
        verbRepository.save(verb);
        return "redirect:/verbs";
    }

    @GetMapping("/verbs/{id}/delete")
    public String verbDelete(@PathVariable(value = "id") long id, Model model) {
        verbRepository.deleteById(id);
        return "redirect:/verbs";
    }
}
