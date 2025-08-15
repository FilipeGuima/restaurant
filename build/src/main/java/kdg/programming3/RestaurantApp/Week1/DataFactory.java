package kdg.programming3.RestaurantApp.Week1;

import kdg.programming3.RestaurantApp.Week1.Week1Customer;
import kdg.programming3.RestaurantApp.Week1.Week1MenuItem;
import kdg.programming3.RestaurantApp.Week1.Week1Order;
import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    public static List<Week1Order> orders = new ArrayList<>();
    public static List<Week1MenuItem> menuItems = new ArrayList<>();
    public static List<Week1Customer> customers = new ArrayList<>();

    public static void seed(){
        Week1MenuItem burger = new Week1MenuItem(1L, "Burger", 9.99);
        Week1MenuItem pizza = new Week1MenuItem(2L, "Pizza", 12.99);
        Week1MenuItem salad = new Week1MenuItem(3L, "Salad", 6.99);
        Week1MenuItem soda = new Week1MenuItem(4L, "Soda", 1.99);
        Week1MenuItem souvlaki = new Week1MenuItem(5L, "Souvlaki", 12.99);
        Week1MenuItem iedTea = new Week1MenuItem(6L, "Ied Tea", 1.99);

        menuItems.add(souvlaki);
        menuItems.add(iedTea);
        menuItems.add(burger);
        menuItems.add(pizza);
        menuItems.add(salad);
        menuItems.add(soda);

        Week1Customer john = new Week1Customer(1L, "John Doe", "john@gmail.com",LocalDate.of(2025,03,27));
        Week1Customer jane = new Week1Customer(2L, "Jane Mary", "jane@gmail.com",LocalDate.of(2025,06,15));
        Week1Customer jasmine = new Week1Customer(3L, "Jasmine Tadeu", "jasmine@gmail.com",LocalDate.of(2024,12,4));

        customers.add(john);
        customers.add(jane);
        customers.add(jasmine);

        Week1Order order1 = new Week1Order(1L, LocalDate.now(), OrderStatus.PENDING, john);
        order1.addWeek1MenuItem(burger);
        order1.addWeek1MenuItem(soda);

        Week1Order order2 = new Week1Order(2L, LocalDate.now().minusDays(1), OrderStatus.DELIVERED, jane);
        order2.addWeek1MenuItem(pizza);
        order2.addWeek1MenuItem(salad);

        Week1Order order3 = new Week1Order(3L, LocalDate.now().minusDays(2), OrderStatus.PREPARING, john);
        order3.addWeek1MenuItem(burger);
        order3.addWeek1MenuItem(pizza);

        Week1Order order4 = new Week1Order(4L,LocalDate.now().minusDays(1), OrderStatus.DELIVERED, jasmine);
        order4.addWeek1MenuItem(iedTea);
        order4.addWeek1MenuItem(souvlaki);

        john.addOrder(order1);
        john.addOrder(order3);
        jane.addOrder(order2);
        jasmine.addOrder(order4);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
    }
}
