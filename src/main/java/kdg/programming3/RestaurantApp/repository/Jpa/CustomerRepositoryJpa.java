package kdg.programming3.RestaurantApp.repository.Jpa;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile("jpa")
public interface CustomerRepositoryJpa extends CustomerRepository {

    @Override
    @Query("SELECT c FROM Customer c WHERE " +
            "(:name IS NULL OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:creationDate IS NULL OR c.creationDate = :creationDate) AND " +
            "(:id IS NULL OR c.id = :id)")
    List<Customer> search(
            @Param("name") String name,
            @Param("email") String email,
            @Param("creationDate") LocalDate creationDate,
            @Param("id") Long id
    );
}
