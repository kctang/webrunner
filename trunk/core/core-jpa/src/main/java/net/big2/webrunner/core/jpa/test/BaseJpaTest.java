package net.big2.webrunner.core.jpa.test;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.WebRunnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

import static java.lang.String.format;
import static org.junit.Assert.*;


@ContextConfiguration(locations = {"classpath*:conf/webrunner.xml"})
@ActiveProfiles("test")
public abstract class BaseJpaTest extends AbstractTransactionalJUnit4SpringContextTests {
    protected Logger log = LoggerFactory.getLogger(BaseJpaTest.class);

    @BeforeTransaction
    public void loadFixtures() throws Exception {
        try {
            executeSqlScript("classpath:fixtures-load.sql", false);
        } catch (DataAccessResourceFailureException e) {
            log.debug("SQL queries not executed [fixtures.sql]");
        }
    }


    @AfterTransaction
    public void unloadFixtures() throws Exception {
        try {
            executeSqlScript("classpath:fixtures-unload.sql", false);
        } catch (DataAccessResourceFailureException e) {
            log.debug("SQL queries not executed [fixtures-unload.sql]");
        }
    }

    @SuppressWarnings("unchecked")
    protected void performCrudTests(JpaRepository... jpaRepositories) {
        for (JpaRepository jpaRepository : jpaRepositories) {
            assertEquals(0, jpaRepository.count());

            if (jpaRepository instanceof WebRunnerRepository) {
                WebRunnerRepository webRunnerRepository = (WebRunnerRepository) jpaRepository;

                // --- test create
                CrudEntity entity = webRunnerRepository.testCreateEntity();
                assertNull(entity.getId());
                entity = (CrudEntity) jpaRepository.save(entity);
                assertNotNull(entity.getId());

                // --- test read
                entity = (CrudEntity) jpaRepository.findOne(entity.getId());
                assertNotNull(entity);
                assertTrue(
                        format("testEntityCreated() failed for repository [%s]",
                                jpaRepository.getClass().getInterfaces()),
                        webRunnerRepository.testEntityCreated(entity));

                // --- update
                webRunnerRepository.testUpdateEntity(entity);
                entity = (CrudEntity) jpaRepository.save(entity);
                assertTrue(
                        format("testEntityUpdated() failed for repository [%s]",
                                jpaRepository.getClass().getInterfaces()),
                        webRunnerRepository.testEntityUpdated(entity));

                // --- delete
                entity = (CrudEntity) jpaRepository.findOne(entity.getId()); // get entity
                assertNotNull(entity); // found entity?
                jpaRepository.delete(entity.getId()); // delete entity
                assertNotNull(entity.getId()); // deleted entity's object still in memory?
                entity = (CrudEntity) jpaRepository.findOne(entity.getId()); // find again
                assertNull(entity); // cannot find deleted entity

                // --- find all
                jpaRepository.save(webRunnerRepository.testCreateEntity());
                jpaRepository.save(webRunnerRepository.testCreateEntity());
                jpaRepository.save(webRunnerRepository.testCreateEntity());
                Page page = webRunnerRepository.list("", new PageRequest(0, 10));
                assertEquals(3, page.getTotalElements());

            } else {
                fail(String.format("Not a testable repository [%s]", jpaRepository.getClass().getInterfaces()));
            }
        }
    }

}
