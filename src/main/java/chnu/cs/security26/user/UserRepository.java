package chnu.cs.security26.user;

/*
    @author     owner
    @project    security26
    @class      UserRepository
    @version    1.0.0
    @since      22.11.2026 - 0:31.31
*/

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
