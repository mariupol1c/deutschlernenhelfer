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
public class Verb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String infinitive;
    private String preteritum;
    private String partyzip2;
    private String translate;

    public Verb(String infinitive, String preteritum, String partyzip2, String translate) {
        this.infinitive = infinitive;
        this.preteritum = preteritum;
        this.partyzip2 = partyzip2;
        this.translate = translate;
    }
}
