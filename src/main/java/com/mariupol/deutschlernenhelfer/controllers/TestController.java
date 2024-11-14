package com.mariupol.deutschlernenhelfer.controllers;

import com.mariupol.deutschlernenhelfer.models.Andere;
import com.mariupol.deutschlernenhelfer.models.Nomen;
import com.mariupol.deutschlernenhelfer.models.Template;
import com.mariupol.deutschlernenhelfer.models.Verb;
import com.mariupol.deutschlernenhelfer.repo.AndereRepository;
import com.mariupol.deutschlernenhelfer.repo.NomenRepository;
import com.mariupol.deutschlernenhelfer.repo.VerbRepository;
import com.mariupol.deutschlernenhelfer.servises.Connector;
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
    private String testAntwort;
    private Template t1;
    private Template t2;
    private Template t3;
    private String message = "";
    private String message2 = "";
    private String color = "color:black";
//    private byte typeW = 1;
    private WortArt wordType;

    private Template template;

    private TestArt difficult = TestArt.Allgemeine;

    @GetMapping("/tests")
    public String testAuswehlen(Model model) {
        return "tests";
    }

    @PostMapping("/tests")
    public String testStart(@RequestParam(value = "type") String type, @RequestParam(value = "komp") String komp, @RequestParam(value = "sprache") String sprache, Model model) {
        difficult = TestArt.valueOf(komp);
        message = "";
        message2 = "";
        if (difficult == TestArt.Allgemeine) {
            return "redirect:/test-all-ru";
        } else if (difficult == TestArt.Test) {
            return "redirect:/test-ru";
        } else {
            if (type.equals("nomens")) {
                wordType = WortArt.Nomen;
                if (sprache.equals("ru")) {
                    if (difficult == TestArt.Schwierig) return "redirect:/test-nomen-ru";
                    else return "redirect:/test-all-ru";
                } else if (sprache.equals("de")) {
                    return "test-nomen-de";
                }
            } else if (type.equals("verbs")) {
                wordType = WortArt.Verb;
                if (sprache.equals("ru")) {
                    if (difficult == TestArt.Schwierig) return "redirect:/test-verb-ru";
                    else return "redirect:/test-all-ru";
                } else if (sprache.equals("de")) {
                    return "anders";
                }
            } else if (type.equals("andersWort")) {
                wordType = WortArt.AndereWort;
                if (sprache.equals("ru")) {
                    if (difficult == TestArt.Schwierig) return "redirect:/test-andere-ru";
                    else return "redirect:/test-all-ru";
                } else if (sprache.equals("de")) {
                    return "anders";
                }
            } else if (type.equals("andersSatz")) {
                wordType = WortArt.AndereSatz;
                if (sprache.equals("ru")) {
                    if (difficult == TestArt.Schwierig) return "redirect:/test-andere-ru";
                    else return "redirect:/test-all-ru";
                } else if (sprache.equals("de")) {
                    return "anders";
                }
            }
        }
        return "home";
    }

    //Nomens
    @GetMapping("/test-nomen-ru")
    public String testNomenRu(Model model) {
        model.addAttribute("wort", getNewNomen("ru"));
        model.addAttribute("message", message);
        model.addAttribute("message2", message2);
        model.addAttribute("color", color);
        return "test-nomen-ru";
    }

    @PostMapping("/test-nomen-ru")
    public String testNomenRuPost(@RequestParam(value = "article") String article, @RequestParam(value = "name") String name,
                                  @RequestParam(value = "plural") String plural) {
        Nomen nomen = nomenRepository.findByTranslate(wort);

        message = String.format("%s! %s ist - %s %s (%s)", "%s", wort, nomen.getArticle(), nomen.getName(), nomen.getPlural());
        if (isTestNomenRichtig(difficult, nomen, article, name, plural)) {
            message = String.format(message, "RICHTIG");
            message2 = "";
            color = "green";

        } else {
            message = String.format(message, "FALSCH");
            message2 = String.format("Deine Antwort ist - %s %s (%s)", article, name, plural);
            color = "red";
        }
        return "redirect:/test-nomen-ru";
    }

    private boolean isTestNomenRichtig(TestArt difficult, Nomen nomen, String article, String name, String plural) {
        boolean result = false;
        if (difficult == TestArt.Schwierig) {
            if (article.trim().equals(nomen.getArticle()) && name.trim().equals(nomen.getName()) && plural.trim().equals(nomen.getPlural())) {
                result = true;
            }
        } else {
            if (name.toLowerCase().trim().equals(nomen.getName().toLowerCase())) {
                result = true;
            }
        }
        return result;
    }

    private String getNewNomen(String type) {
        List<Nomen> nomens = (List<Nomen>) nomenRepository.findAll();
        int l = (int) (Math.random() * nomens.size());
        if (type.equals("ru")) {
            Nomen nom = nomens.get(l);
            wort = nom.getTranslate();
            template = Connector.getTamplateFromNomen(nom);
            return wort;
        }
        return "ошибка";
    }

    //Verbs
    @GetMapping("/test-verb-ru")
    public String testVerbRu(Model model) {
        model.addAttribute("wort", getNewVerb("ru"));
        model.addAttribute("message", message);
        model.addAttribute("message2", message2);
        model.addAttribute("color", color);
        return "test-verb-ru";
    }

    @PostMapping("/test-verb-ru")
    public String testVerbRuPost(@RequestParam(value = "infinitive") String infinitive, @RequestParam(value = "preteritum") String preteritum,
                                 @RequestParam(value = "partyzip2") String partyzip2) {
        Verb verb = verbRepository.findByTranslate(wort);

        message = String.format("%s! %s ist - %s %s %s", "%s", wort, verb.getInfinitive(), verb.getPreteritum(), verb.getPartyzip2());
        if (isTestVerbenRichtig(difficult, verb, infinitive, preteritum, partyzip2)) {
            message = String.format(message, "RICHTIG");
            message2 = "";
            color = "green";
        } else {
            message = String.format(message, "FALSCH");
            message2 = String.format("Deine Antwort ist - %s %s %s", infinitive, preteritum, partyzip2);
            color = "red";
        }
        return "redirect:/test-verb-ru";
    }

    private boolean isTestVerbenRichtig(TestArt difficult, Verb verb, String infinitive, String preteritum, String partyzip2) {
        boolean result = false;
        if (difficult == TestArt.Schwierig) {
            if (infinitive.trim().equals(verb.getInfinitive()) && preteritum.trim().equals(verb.getPreteritum()) && partyzip2.trim().equals(verb.getPartyzip2())) {
                result = true;
            }
        } else {
            if (infinitive.toLowerCase().trim().equals(verb.getInfinitive().toLowerCase())) {
                result = true;
            }
        }
        return result;
    }

    private String getNewVerb(String type) {
        List<Verb> verbs = (List<Verb>) verbRepository.findAll();
        int l = (int) (Math.random() * verbs.size());
        if (type.equals("ru")) {
            Verb verb = verbs.get(l);
            wort = verb.getTranslate();
            template = Connector.getTamplateFromVerb(verb);
            return wort;
        }
        return "ошибка";
    }

    //Anders
    @GetMapping("/test-andere-ru")
    public String testAndereRu(Model model) {
        model.addAttribute("wort", getNewAndere("ru"));
        model.addAttribute("message", message);
        model.addAttribute("message2", message2);
        model.addAttribute("color", color);
        return "test-andere-ru";
    }

    @PostMapping("/test-andere-ru")
    public String testAndereRuPost(@RequestParam(value = "wortA") String wortA) {
//        Andere andere = andereRepository.findByTranslate(wort);
//        template = Connector.getTemplateFromAndere(andere);
        boolean answer = checkAnswer(wortA, template);
        setAnswer(wortA, answer, template.getWort());
        return "redirect:/test-andere-ru";
    }

    private boolean checkAnswer(String wortA, Template template) {
        return template.getWort().trim().equalsIgnoreCase(wortA.trim());
    }

    private String getNewAndere(String type) {
        byte typeW = 1;
        if (wordType == WortArt.AndereSatz) typeW = 2;
        List<Andere> anders = andereRepository.findByType(typeW);
        int l = (int) (Math.random() * anders.size());
        if (type.equals("ru")) {
            Andere andere = anders.get(l);
            wort = andere.getTranslate();
            template = Connector.getTemplateFromAndere(andere);
            return wort;
        }
        return "ошибка";
    }

    public void setAnswer(String word, boolean answer, String translate) {
        message = String.format("%s! %s ist - %s", "%s", wort, translate);
        if (answer) {
            message = String.format(message, "RICHTIG");
            message2 = "";
            color = "green";
        } else {
            message = String.format(message, "FALSCH");
            message2 = String.format("Deine Antwort ist - %s", word);
            color = "red";
        }
    }

    //All
    @GetMapping("/test-all-ru")
    public String testAllRu(Model model) {
        int tempType = 0;
        if (difficult == TestArt.Leicht) {
            tempType = wordType.ordinal() + 1;
        }
        model.addAttribute("wort", getNew("ru", tempType));
        model.addAttribute("message", message);
        model.addAttribute("message2", message2);
        model.addAttribute("color", color);
        return "test-all-ru";
    }



    private String getNew(String lang, int type) {
        String result = "";
        if (type > 3) type = 3;
        else if (type == 0) type = getRandomInt(1, 3);
        if (type == 1) result = getNewNomen(lang);
        else if (type == 2) result = getNewVerb(lang);
        else if (type == 3) result = getNewAndere(lang);
        return result;
    }

    @PostMapping("/test-all-ru")
    public String testAllRuPost(@RequestParam(value = "name") String wortA) {
        boolean answer = checkAnswer(wortA, template);
        setAnswer(wortA, answer, template.getWort());
        return "redirect:/test-all-ru";
    }

    //Test
    @GetMapping("/test-ru")
    public String testTestRu(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("message2", message2);
        model.addAttribute("color", color);
        getNewTest("ru");
        model.addAttribute("wort", wort);
        model.addAttribute("t1", t1.getWort());
        model.addAttribute("t2", t2.getWort());
        model.addAttribute("t3", t3.getWort());
        return "test-ru";
    }

    private void getNewTest(String ru) {
        int type = getRandomInt(1, 3);
        int num = getRandomInt(1, 3);
        testAntwort = "t" + num;
        String wortTemp = "";
        Template templateTemp = new Template();
        getNew("ru", type);
        t1 = template;
        if (num == 1) {
            wortTemp = wort;
            templateTemp = template;
        }
        getNew("ru", type);
        if (t1.getWort().equalsIgnoreCase(template.getWort())) getNew("ru", type);
        t2 = template;
        if (num == 2) {
            wortTemp = wort;
            templateTemp = template;
        }
        getNew("ru", type);
        if (t1.getWort().equalsIgnoreCase(template.getWort()) ||
                t2.getWort().equalsIgnoreCase(template.getWort())) getNew("ru", type);
        t3 = template;
        if (num == 3) {
            wortTemp = wort;
            templateTemp = template;
        }
        wort = wortTemp;
        template = templateTemp;
    }

    @PostMapping("/test-ru")
    public String testTestRuPost(@RequestParam(value = "an") String an) {
        boolean answer = testAntwort.equalsIgnoreCase(an);
        String wortT = "";
        if (an.equals("t1")) wortT = t1.getWort();
        else if (an.equals("t2")) wortT = t2.getWort();
        if (an.equals("t3")) wortT = t3.getWort();
        setAnswer(wortT, answer, template.getWort());
        return "redirect:/test-ru";
    }

    public int getRandomInt(int from, int to) {
        return from + (int) (Math.random() * to);
    }
}
