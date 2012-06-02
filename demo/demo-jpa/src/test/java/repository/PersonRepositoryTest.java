package repository;


import net.big2.webrunner.core.jpa.test.BaseJpaTest;
import model.Person;
import org.springframework.context.annotation.PropertySource;
import repository.PersonRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonRepositoryTest extends BaseJpaTest {
    @Autowired
    PersonRepository personRepository;

    @Test
    public void thatPersonRepositoryWorks() {
        List<Person> personList = personRepository.findAll();

        assertEquals(3, personList.size());
    }

}
