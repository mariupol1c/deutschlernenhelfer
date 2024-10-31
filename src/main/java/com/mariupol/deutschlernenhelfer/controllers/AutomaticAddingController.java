package com.mariupol.deutschlernenhelfer.controllers;

import com.mariupol.deutschlernenhelfer.models.Andere;
import com.mariupol.deutschlernenhelfer.models.Nomen;
import com.mariupol.deutschlernenhelfer.models.Template;
import com.mariupol.deutschlernenhelfer.models.Verb;
import com.mariupol.deutschlernenhelfer.repo.AndereRepository;
import com.mariupol.deutschlernenhelfer.repo.NomenRepository;
import com.mariupol.deutschlernenhelfer.repo.VerbRepository;
import com.mariupol.deutschlernenhelfer.servises.Connector;
import com.mariupol.deutschlernenhelfer.servises.ParcingServises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


@Controller
public class AutomaticAddingController {
    @Autowired
    private AndereRepository andereRepository;
    @Autowired
    private NomenRepository nomenRepository;
    @Autowired
    private VerbRepository verbRepository;

    @PostMapping("/add")
    public String addFromFile(@RequestParam(value = "myFile") String myFile, Model model) {
        File[] file = new File[1];
        findFile(new File("D:\\"), file, myFile);
        workWithFileT(file[0].getAbsolutePath());
        //System.out.println(file[0].getAbsolutePath());
        return "redirect:/";
    }

    private void findFile(File rootDir, File[] file, String myFile) {
        if (rootDir.isDirectory()){
            File[] files = rootDir.listFiles();
            if (files != null){
                for (File f : files){
                    if (f.isDirectory()) {
                        findFile(f, file, myFile);
                    }
                    else{
                        if (f.getName().equals(myFile)) {
                            file[0] = f;
                            break;
                        }
                    }
                }
            }
        }
    }


    public String checkWordType(String wort) {
        return ParcingServises.getType(wort);
    }

    public void workWithFileT(String fileStr) {
        Path path = Path.of(fileStr);
        try {
            List<String> tempList = Files.readAllLines(path);
            List<String> list = processFileT(tempList);
            addListToDataBase(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> processFileT(List<String> tempList) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < tempList.size(); i++) {
            String str = tempList.get(i);
            str = ParcingServises.prepareWord(str);
            if (str.charAt(0) == ',') str = str.substring(1);
            if (!str.isEmpty()) list.add(str);
        }
        return list;
    }

    public void workWithFileK(String fileStr) {
        Path path = Path.of("d:\\Temp\\" + fileStr);
        try {
            List<String> tempList = Files.readAllLines(path);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addListToDataBase(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            try {
                Thread.sleep(2000); // site restrictions
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Template temp = ParcingServises.getTemplate(list.get(i));
            if (temp.getWort() == null) continue;
            if (temp.getType().equals("прилагательное")) {
                Andere andere = Connector.getAndere1FromTemplate(temp);
                if (andere.getTranslate() == null) continue;
                andereRepository.saveAndere(andere);
            } else if (temp.getType().equals("существительное")) {
                Nomen nomen = Connector.getNomenFromTemplate(temp);
                if (nomen.getTranslate() == null) continue;
                nomenRepository.saveNomen(nomen);
            } else if (temp.getType().equals("глагол")) {
                Verb verb = Connector.getVerbFromTemplate(temp);
                if (verb.getTranslate() == null) continue;
                verbRepository.saveVerb(verb);
            }
        }
    }

}
