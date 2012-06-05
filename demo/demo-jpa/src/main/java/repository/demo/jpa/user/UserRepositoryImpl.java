package repository.demo.jpa.user;

import model.demo.jpa.User;
import net.big2.webrunner.core.jpa.crud.BaseWebRunnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserRepositoryImpl extends BaseWebRunnerRepository<User> implements UserRepositoryCustom {
    @Override
    public Page<User> list(String q, Pageable pageable) {
        UserRepository repository = (UserRepository) getJpaRepository();

        String param = "%" + q + "%";
        return repository.findByUsernameLikeOrEmailLikeOrFirstNameLikeOrLastNameLike(param, param, param, param, pageable);
    }

    @Override
    public User testCreateEntity() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        return user;
    }
}
