package repository;


import model.APIKey;
import org.springframework.data.repository.CrudRepository;


public interface KeyRepository extends CrudRepository<APIKey, String> {

}
