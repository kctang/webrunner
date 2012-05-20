package net.big2.webrunner.core.jpa.crud;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseWebRunnerRepository<T extends CrudEntity> implements WebRunnerRepository<T> {
    @Autowired
    protected ApplicationContext context;

    private JpaRepository<T, Long> jpaRepository = null;

    JpaRepository<T, Long> getJpaRepository() {
        // this cannot be autowired due to cyclic dependency, hence this init
        if (jpaRepository == null) {
            // TODO: do proper class to repository name conversion
            String repositoryName = getClass().getSimpleName();
            repositoryName = repositoryName.replaceAll("Impl", "");
            repositoryName = WordUtils.uncapitalize(repositoryName);

            //noinspection unchecked
            jpaRepository = context.getBean(repositoryName, JpaRepository.class);
        }

        return jpaRepository;
    }

    @Override
    public Page<T> list(String q, Pageable pageable) {
        return getJpaRepository().findAll(pageable);
    }

    @Override
    public boolean testEntityCreated(T t) {
        return t != null;
    }

    @Override
    public void testUpdateEntity(T t) {
        // do nothing
    }

    @Override
    public boolean testEntityUpdated(T t) {
        return t != null;
    }
}
