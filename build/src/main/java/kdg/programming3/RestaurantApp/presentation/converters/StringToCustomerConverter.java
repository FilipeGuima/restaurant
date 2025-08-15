package kdg.programming3.RestaurantApp.presentation.converters;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCustomerConverter implements Converter<String, Customer> {

    private final CustomerRepository customerRepository;

    @Autowired
    public StringToCustomerConverter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer convert(String source) {

        if (source == null || source.isEmpty()) {
            return null;
        }
        Long customerId = Long.parseLong(source);
        return customerRepository.findById(customerId).orElse(null);
    }
}