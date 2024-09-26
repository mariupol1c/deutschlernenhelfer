package com.mariupol.deutschlernenhelfer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Nomen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String article;
    private String name;
    private String plural;
    private String translate;

    public Nomen(String article, String name, String plural, String translate) {
        this.article = article;
        this.name = name;
        this.plural = plural;
        this.translate = translate;
    }
}
