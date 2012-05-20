package net.big2.webrunner.core.jpa.sample;

import net.big2.webrunner.core.jpa.model.Book;
import net.big2.webrunner.core.jpa.model.BookRepository;
import net.big2.webrunner.core.jpa.test.BaseJpaTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class BookRepositoryTest extends BaseJpaTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    public void thatRepositoryWorks() {
        List<Book> personList = bookRepository.findAll();

        assertEquals(3, personList.size());
    }

}
