package net.big2.webrunner.core.jpa.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WebRunnerRepository<T extends CrudEntity> {
    Page<T> list(String q, Pageable pageable);

    T testCreateEntity();

    boolean testEntityCreated(T t);

    void testUpdateEntity(T t);

    boolean testEntityUpdated(T t);
}
