package kdg.programming3.RestaurantApp.presentation.viewmodel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerViewModel {

    @NotEmpty
    @Size(min = 2, max = 35)
    @Pattern(regexp = "^[\\p{L} ]*$", message = "{Pattern.customer.firstName}")
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 35)
    @Pattern(regexp = "^[\\p{L} ]*$", message = "{Pattern.customer.lastName}")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}