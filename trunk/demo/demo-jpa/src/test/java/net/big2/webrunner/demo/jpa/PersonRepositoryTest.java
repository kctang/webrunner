package net.big2.webrunner.demo.jpa;


import net.big2.webrunner.core.jpa.BaseJpaTest;
import net.big2.webrunner.demo.jpa.model.Person;
import net.big2.webrunner.demo.jpa.model.PersonRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class PersonRepositoryTest extends BaseJpaTest {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    PersonRepository personRepository;
    
    @Test
    public void thatPersonRepositoryWorks() {
        List<Person> personList = personRepository.findAll();

        assertEquals(3, personList.size());
    }

}
