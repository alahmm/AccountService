package authexercices.presistence;

import authexercices.businesslayout.AppUser;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    Optional<AppUser> findAppUserByUsername(String username);
}
