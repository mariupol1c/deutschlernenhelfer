package com.mariupol.deutschlernenhelfer.repo;

import com.mariupol.deutschlernenhelfer.models.Nomen;
import org.springframework.data.repository.CrudRepository;

public interface NomenRepository extends CrudRepository<Nomen, Long> {
    Nomen findByTranslate(String translate);

    Nomen findByArticleAndName(String article, String name);

    default void saveNomen(Nomen nomen) {
        if (findByArticleAndName(nomen.getArticle(), nomen.getName()) == null) save(nomen);
    }
}
