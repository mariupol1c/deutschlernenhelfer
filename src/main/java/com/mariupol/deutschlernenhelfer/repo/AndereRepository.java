package com.mariupol.deutschlernenhelfer.repo;


import com.mariupol.deutschlernenhelfer.models.Andere;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AndereRepository extends CrudRepository<Andere, Long> {
    Andere findByTranslate(String translate);

     List<Andere> findByType(byte type);

    Andere findByWort(String wort);

    default void saveAndere(Andere andere) {
        if (findByWort(andere.getWort()) == null) save(andere);
    }
}
