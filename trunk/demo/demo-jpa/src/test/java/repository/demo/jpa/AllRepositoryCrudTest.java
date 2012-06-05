package repository.demo.jpa;


import net.big2.webrunner.core.jpa.test.BaseJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import repository.demo.jpa.datalist.DataListRepository;
import repository.demo.jpa.group.GroupRepository;
import repository.demo.jpa.user.UserRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AllRepositoryCrudTest extends BaseJpaTest {
    @Autowired
    DataListRepository dataListRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testAllRepositoryCrud() {
        performCrudTests(dataListRepository, groupRepository, userRepository);
    }

}
