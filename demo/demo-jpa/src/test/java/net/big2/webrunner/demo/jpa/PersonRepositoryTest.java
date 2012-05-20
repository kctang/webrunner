package net.big2.webrunner.demo.jpa;


import net.big2.webrunner.core.jpa.test.BaseJpaTest;
import net.big2.webrunner.core.jpa.test.BaseJpaTest;
import net.big2.webrunner.demo.jpa.model.Person;
import net.big2.webrunner.demo.jpa.model.PersonRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath*:webrunner.xml"})
public class PersonRepositoryTest extends BaseJpaTest {
    @Autowired
    PersonRepository personRepository;

    @Test
    public void thatPersonRepositoryWorks() {
        List<Person> personList = personRepository.findAll();

        assertEquals(3, personList.size());
    }

}
