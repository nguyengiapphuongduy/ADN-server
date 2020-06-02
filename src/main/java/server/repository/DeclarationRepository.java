package server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import server.document.Declaration;

@Repository
public interface DeclarationRepository extends MongoRepository<Declaration, String> {
}
