package chnu.cs.security26.repository;

/*
    @author     Anastasiia Shevchenko
    @project    security26
    @class      BookRepository
    @version    1.0.0
    @since      BookRepository - .50
*/

import chnu.cs.security26.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
