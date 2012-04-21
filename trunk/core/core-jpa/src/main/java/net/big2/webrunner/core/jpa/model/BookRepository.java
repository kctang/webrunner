package net.big2.webrunner.core.jpa.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
