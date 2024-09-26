package com.mariupol.deutschlernenhelfer.repo;


import com.mariupol.deutschlernenhelfer.models.Andere;
import com.mariupol.deutschlernenhelfer.models.Verb;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface AndereRepository extends CrudRepository<Andere, Long> {
    Andere findByTranslate(String translate);

    List<Andere> findByType(byte type);
}
