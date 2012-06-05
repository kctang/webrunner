package repository.demo.jpa.group;

import model.demo.jpa.Group;
import net.big2.webrunner.core.jpa.crud.BaseWebRunnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GroupRepositoryImpl extends BaseWebRunnerRepository<Group> implements GroupRepositoryCustom {
    @Override
    public Page<Group> list(String q, Pageable pageable) {
        GroupRepository repository = (GroupRepository) getJpaRepository();

        return repository.findByNameLike("%" + q + "%", pageable);
    }

    @Override
    public Group testCreateEntity() {
        Group group = new Group();
        group.setName("group");
        return group;
    }
}
