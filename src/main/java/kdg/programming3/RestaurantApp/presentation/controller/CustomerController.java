package kdg.programming3.RestaurantApp.presentation.controller;

import jakarta.validation.Valid;
import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.presentation.viewmodel.CustomerViewModel;
import kdg.programming3.RestaurantApp.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showCustomers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email",required = false) String email,
            @RequestParam(value = "creationDate",required = false) LocalDate creationDate,
            @RequestParam(value = "id",required = false) Long id,
            Model model) {

        log.debug("Fetching all customers or specific based on name, email, and creationDate: {} {} {} {} ", name, email, creationDate, id);
        List<Customer> customers = customerService.searchCustomers(name, email, creationDate, id);

        model.addAttribute("customers", customers);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("creationDate", creationDate);
        model.addAttribute("id", id);
        model.addAttribute("page", "customers");
        return "customers";
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model) {
        log.debug("Fetching add customer form");

        if (!model.containsAttribute("customerViewModel")) {
            model.addAttribute("customerViewModel", new CustomerViewModel());
        }
        model.addAttribute("page", "addCustomer");
        return "addCustomer";
    }

    @PostMapping("/add")
    public String addCustomer(@Valid @ModelAttribute("customerViewModel") CustomerViewModel customerViewModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        log.debug("POST request to add new customer: {}", customerViewModel.getFirstName());

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors found for customer: {}", customerViewModel.getFirstName());
            redirectAttributes.addFlashAttribute("customerViewModel", customerViewModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customerViewModel", bindingResult);
            return "redirect:/customers/add";
        }

        if (!customerService.findByEmail(customerViewModel.getEmail()).isEmpty()) {
            log.warn("Attempted to add customer with existing email: {}", customerViewModel.getEmail());
            bindingResult.rejectValue("email", "email.exists", "This email address is already taken.");
            redirectAttributes.addFlashAttribute("customerViewModel", customerViewModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customerViewModel", bindingResult);
            return "redirect:/customers/add";
        }

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerViewModel.getFirstName());
        newCustomer.setLastName(customerViewModel.getLastName());
        newCustomer.setEmail(customerViewModel.getEmail());

        customerService.addCustomer(newCustomer);
        return "redirect:/customers";
    }
}