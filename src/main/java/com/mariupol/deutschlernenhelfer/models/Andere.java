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
public class Andere {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String wort;
    private byte type;
    private String translate;

    public Andere(String wort, byte type, String translate) {
        this.wort = wort;
        this.type = type;
        this.translate = translate;
    }
}
