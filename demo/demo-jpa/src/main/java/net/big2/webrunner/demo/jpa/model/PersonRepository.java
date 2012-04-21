package net.big2.webrunner.demo.jpa.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
//    List<Person> findByName(String name);
}
