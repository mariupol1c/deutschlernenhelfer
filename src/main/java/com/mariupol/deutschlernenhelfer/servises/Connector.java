package com.mariupol.deutschlernenhelfer.servises;

import com.mariupol.deutschlernenhelfer.models.Andere;
import com.mariupol.deutschlernenhelfer.models.Nomen;
import com.mariupol.deutschlernenhelfer.models.Template;
import com.mariupol.deutschlernenhelfer.models.Verb;

public class Connector {
    public static Nomen getNomenFromTemplate(Template template){
        Nomen nomen = new Nomen();
        String[] strings = template.getWort().split(",");
        nomen.setArticle(strings[1].trim());
        nomen.setName(strings[0].trim());
        nomen.setPlural(template.getKonjugieren()[1]);
        nomen.setTranslate(template.getTranslate());
        return nomen;
    }

    public static Verb getVerbFromTemplate(Template template){
        Verb verb = new Verb();
        verb.setInfinitive(template.getWort());
        verb.setPreteritum(template.getKonjugieren()[1].trim());
        verb.setPartyzip2(template.getKonjugieren()[2].trim());
        verb.setTranslate(template.getTranslate());
        return verb;
    }

    public static Andere getAndere1FromTemplate(Template template){
        Andere andere = new Andere();
        andere.setWort(template.getWort());
        andere.setType((byte)1);
        andere.setTranslate(template.getTranslate());
        return andere;
    }
}
