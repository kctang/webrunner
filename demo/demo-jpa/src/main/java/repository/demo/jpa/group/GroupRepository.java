package repository.demo.jpa.group;

import model.demo.jpa.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, GroupRepositoryCustom {
    Page<Group> findByNameLike(String name, Pageable pageable);
}
