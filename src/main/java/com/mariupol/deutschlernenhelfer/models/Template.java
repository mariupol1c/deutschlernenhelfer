package com.mariupol.deutschlernenhelfer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template {
    private String suchende;
    private String type;
    private String wort;
    private String[] konjugieren;
    private String translate;


}
