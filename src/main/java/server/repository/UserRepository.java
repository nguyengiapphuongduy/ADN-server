package server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import server.document.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
