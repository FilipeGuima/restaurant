package kdg.programming3.RestaurantApp.presentation.viewmodel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.domain.OrderStatus;

import java.util.List;

public class OrderViewModel {


    @NotNull(message = "{error.customer_required}")
    private Customer customer;

    @NotNull(message = "{error.status_empty}")
    private OrderStatus status;

    @NotEmpty(message = "{error.order_empty}")
    private List<Long> menuItemIds;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Long> getMenuItemIds() {
        return menuItemIds;
    }

    public void setMenuItemIds(List<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }
}