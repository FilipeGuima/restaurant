package kdg.programming3.RestaurantApp.service.Jpa;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.repository.Jpa.CustomerRepositoryJpa;
import kdg.programming3.RestaurantApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Profile("jpa")
public class CustomerServiceJpaImpl implements CustomerService {

    private final CustomerRepositoryJpa customerRepository;

    @Autowired
    public CustomerServiceJpaImpl(CustomerRepositoryJpa customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }


    @Override
    public List<Customer> searchCustomers(String name, String email, LocalDate creationDate, Long id) {
        return customerRepository.search(name, email, creationDate, id);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
