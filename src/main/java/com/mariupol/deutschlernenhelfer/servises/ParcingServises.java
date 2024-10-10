package com.mariupol.deutschlernenhelfer.servises;

import com.mariupol.deutschlernenhelfer.models.Template;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParcingServises {

    public static String getType(String str) {
        String result = null;
        try {
            Document document = Jsoup.connect("https://www.woerter.ru/?w=" + str).get();
            var elType = document.select("body > article > div > div.rAbschnitt > div > section.rBox.rBoxWht > div.rAufZu > span.rInf");
            result = elType.get(0).text().split("·")[1].trim();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

    public static String getWort(String str) {
        String result = null;
        try {
            Document document = Jsoup.connect("https://www.woerter.ru/?w=" + str).get();
            var elWort = document.select("body > article > div > div.rAbschnitt > div > section.rBox.rBoxWht > div.rAufZu > div#wStckInf > div.rAuf.rCntr > div.rCntr.rClear");
            result = elWort.get(0).text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getTranslate(String str) {
        String result = null;
        try {
            Document document = Jsoup.connect("https://www.woerter.ru/?w=" + str).get();
            var elTranslate = document.select("body > article > div > div.rInfo > section.rBox.rBoxWht > div.rAufZu > dl.wNrn");
            String sTranslate = elTranslate.get(1).text();
            sTranslate = sTranslate.substring(0, sTranslate.indexOf(","));
            result = sTranslate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String[] getKonjugieren(String str) {
        String[] result = null;
        try {
            Document document = Jsoup.connect("https://www.woerter.ru/?w=" + str).get();
            var elKon = document.select("body > article > div > div.rAbschnitt > div > section.rBox.rBoxWht > div.rAufZu > div#wStckInf > div.rAuf.rCntr > p.r1Zeile.rU3px.rO0px");
            String sKon = elKon.get(0).text();
            int ind = sKon.indexOf(",");
            if (ind > 0) sKon = sKon.substring(0, ind);
            result = sKon.split(" · ");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Template getTemplate(String suchen) {
            Template template = new Template();
            template.setSuchende(suchen);
            template.setType(getType(suchen));
            template.setWort(getWort(suchen));
            template.setKonjugieren(getKonjugieren(suchen));
            template.setTranslate(getTranslate(suchen));
        return template;
    }
}
