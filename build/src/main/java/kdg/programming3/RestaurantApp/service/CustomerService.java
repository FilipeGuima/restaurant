package kdg.programming3.RestaurantApp.service;

import kdg.programming3.RestaurantApp.domain.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long Id);
    List<Customer> searchCustomers(String name, String email, LocalDate creationDate, Long id);
    Customer addCustomer(Customer customer);
    List<Customer> findByEmail(String email);
    void deleteCustomer(Long id);
}
