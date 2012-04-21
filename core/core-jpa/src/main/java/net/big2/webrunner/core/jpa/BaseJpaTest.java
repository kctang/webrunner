package net.big2.webrunner.core.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

@ContextConfiguration("classpath:webrunner.xml")
@ActiveProfiles(value = "test")
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

}
