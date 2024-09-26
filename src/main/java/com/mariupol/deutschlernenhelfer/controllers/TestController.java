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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    NomenRepository nomenRepository;
    @Autowired
    VerbRepository verbRepository;
    @Autowired
    AndereRepository andereRepository;


    private String wort;
    private String message = "";
    private String message2 = "";
    private String color = "color:black";
    private byte typeW = 1;

    @GetMapping("/tests")
    public String testAuswehlen(Model model) {
        return "tests";
    }
    @PostMapping("/tests")
    public String testStart(@RequestParam(value = "type") String type, @RequestParam(value = "sprache") String sprache, Model model){
        message = "";
        message2 = "";
        if (type.equals("nomens")){
            if (sprache.equals("ru")) {
                return "redirect:/test-nomen-ru";
            }
            else if (sprache.equals("de")) {
                return "test-nomen-de";
            }
        }
        else if (type.equals("verbs")){
            if (sprache.equals("ru")) {
                return "redirect:/test-verb-ru";
            }
            else if (sprache.equals("de")) {
                return "anders";
            }
        }
        else if (type.equals("andersWort")){
            if (sprache.equals("ru")) {
                return "redirect:/test-andere-ru";
            }
            else if (sprache.equals("de")) {
                return "anders";
            }
        }
        else if (type.equals("andersSatz")){
            if (sprache.equals("ru")) {
                typeW = 2;
                return "redirect:/test-andere-ru";
            }
            else if (sprache.equals("de")) {
                return "anders";
            }
        }
        return "home";
    }

    //Nomens
   @GetMapping("/test-nomen-ru")
   public String testNomenRu(Model model){
       model.addAttribute("wort", getNewNomen("ru"));
       model.addAttribute("message", message);
       model.addAttribute("message2", message2);
       model.addAttribute("color", color);
       return "test-nomen-ru";
   }

    @PostMapping("/test-nomen-ru")
    public String testNomenRuPost(@RequestParam(value = "article") String article, @RequestParam(value = "name") String name,
                                  @RequestParam(value = "plural") String plural){
        Nomen nomen = nomenRepository.findByTranslate(wort);

        message = String.format("%s! %s ist - %s %s (%s)", "%s", wort, nomen.getArticle(), nomen.getName(), nomen.getPlural());
        if (article.trim().equals(nomen.getArticle()) && name.trim().equals(nomen.getName()) && plural.trim().equals(nomen.getPlural())) {
            message =  String.format(message, "RICHTIG");
            message2 = "";
            color = "green";
        }
        else{
            message = String.format(message, "FALSCH");
            message2 = String.format("Deine Antwort ist - %s %s (%s)", article, name, plural);
            color = "red";
        }
        return "redirect:/test-nomen-ru";
    }

    private String getNewNomen(String type){
        List<Nomen> nomens = (List<Nomen>) nomenRepository.findAll();
        int l = (int) (Math.random() * nomens.size());
        if (type.equals("ru")) {
            wort = nomens.get(l).getTranslate();
            return wort;
        }
        return "ошибка";
    }

    //Verbs
    @GetMapping("/test-verb-ru")
    public String testVerbRu(Model model){
        model.addAttribute("wort", getNewVerb("ru"));
        model.addAttribute("message", message);
        model.addAttribute("message2", message2);
        model.addAttribute("color", color);
        return "test-verb-ru";
    }

    @PostMapping("/test-verb-ru")
    public String testVerbRuPost(@RequestParam(value = "infinitive") String infinitive, @RequestParam(value = "preteritum") String preteritum,
                                  @RequestParam(value = "partyzip2") String partyzip2){
        Verb verb = verbRepository.findByTranslate(wort);

        message = String.format("%s! %s ist - %s %s %s", "%s", wort, verb.getInfinitive(), verb.getPreteritum(), verb.getPartyzip2());
        if (infinitive.trim().equals(verb.getInfinitive()) && preteritum.trim().equals(verb.getPreteritum()) && partyzip2.trim().equals(verb.getPartyzip2())) {
            message =  String.format(message, "RICHTIG");
            message2 = "";
            color = "green";
        }
        else{
            message = String.format(message, "FALSCH");
            message2 = String.format("Deine Antwort ist - %s %s %s", infinitive, preteritum, partyzip2);
            color = "red";
        }
        return "redirect:/test-verb-ru";
    }

    private String getNewVerb(String type){
        List<Verb> verbs = (List<Verb>) verbRepository.findAll();
        int l = (int) (Math.random() * verbs.size());
        if (type.equals("ru")) {
            wort = verbs.get(l).getTranslate();
            return wort;
        }
        return "ошибка";
    }

    //Anders
    @GetMapping("/test-andere-ru")
    public String testAndereRu(Model model){
        model.addAttribute("wort", getNewAndere("ru"));
        model.addAttribute("message", message);
        model.addAttribute("message2", message2);
        model.addAttribute("color", color);
        return "test-andere-ru";
    }

    @PostMapping("/test-andere-ru")
    public String testAndereRuPost(@RequestParam(value = "wortA") String wortA){
        Andere andere = andereRepository.findByTranslate(wort);

        message = String.format("%s! %s ist - %s", "%s", wort, andere.getWort());
        if (wortA.trim().equals(andere.getWort())) {
            message =  String.format(message, "RICHTIG");
            message2 = "";
            color = "green";
        }
        else{
            message = String.format(message, "FALSCH");
            message2 = String.format("Deine Antwort ist - %s", wortA);
            color = "red";
        }
        return "redirect:/test-andere-ru";
    }

    private String getNewAndere(String type){
        List<Andere> anders = andereRepository.findByType(typeW);
        int l = (int) (Math.random() * anders.size());
        if (type.equals("ru")) {
            wort = anders.get(l).getTranslate();
            return wort;
        }
        return "ошибка";
    }
}
