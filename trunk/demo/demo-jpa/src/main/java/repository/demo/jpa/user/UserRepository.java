package repository.demo.jpa.user;

import model.demo.jpa.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Page<User> findByUsernameLikeOrEmailLikeOrFirstNameLikeOrLastNameLike(
            String username, String email, String firstName, String lastName, Pageable pageable);
}
