package com.mariupol.deutschlernenhelfer.repo;

import com.mariupol.deutschlernenhelfer.models.Nomen;
import org.springframework.data.repository.CrudRepository;

public interface NomenRepository extends CrudRepository<Nomen, Long> {
    Nomen findByTranslate(String translate);
}
