package kdg.programming3.RestaurantApp.service;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.presentation.controller.CustomerController;
import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
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
    public List<Customer> searchCustomers(String name, String email, LocalDate creationDate, Long id) {
        boolean isEmptySearch =
                (name == null || name.isBlank()) &&
                        (email == null || email.isBlank()) &&
                        creationDate == null &&
                        id == null;


        return isEmptySearch
                ? customerRepository.findAll()
                : customerRepository.search(name, email, creationDate, id);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        log.debug("Checking if it exists: {}", customer.getName());
        List<Customer> customerEmail = findByEmail(customer.getEmail());
        if (!customerEmail.isEmpty()) {
            log.debug("Customer already exists: {}", customer.getName());
            return null;
        }
        log.debug("Adding new customer with name: {}", customer.getName());
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}
