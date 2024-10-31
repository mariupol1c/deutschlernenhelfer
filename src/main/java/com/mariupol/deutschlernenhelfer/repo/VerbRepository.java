package com.mariupol.deutschlernenhelfer.repo;


import com.mariupol.deutschlernenhelfer.models.Verb;
import org.springframework.data.repository.CrudRepository;

public interface VerbRepository extends CrudRepository<Verb, Long> {
    Verb findByTranslate(String translate);
    Verb findByInfinitive(String infinitive);
    default void saveVerb(Verb verb) {
        if (findByInfinitive(verb.getInfinitive()) == null) save(verb);
    }
}
