package kdg.programming3.RestaurantApp.Week1;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;

import java.time.LocalDate;
import java.util.*;

public class ConsoleApp {
    public static void main(String[] args) {
        DataFactory.seed();
        ConsoleApp app = new ConsoleApp();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                System.out.println("What would you like to do?");
                System.out.println("==========================");
                System.out.println("0) Quit");
                System.out.println("1) Show all customers");
                System.out.println("2) Show Customers with orders");
                System.out.println("3) Show all menu items");
                System.out.println("4) Show orders with status and date filter");
                System.out.println("5) Show orders by customer");
                System.out.print("Choice (0-5): ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        app.showAllCustomers();
                        break;
                    case 2:
                        app.showCustomersWithOrders(scanner);
                        break;
                    case 3:
                        app.showAllMenuItems();
                        break;
                    case 4:
                        app.showOrdersWithFilters(scanner);
                        break;
                    case 5:
                        app.showOrdersByCustomer(scanner);
                    default:
                        System.out.println("Invalid choice");
                }

            } catch (IllegalStateException | InputMismatchException e){
                System.out.println("Invalid input! Please enter a number between 0 and 5.");
                scanner.nextLine();
            }

            }
        scanner.close();

    }

    private void showAllCustomers() {
        DataFactory.orders.stream()
                .map(Week1Order::getCustomer)
                .distinct()
                .forEach(System.out::println);
    }

    private void showCustomersWithOrders(Scanner scanner) {
     DataFactory.customers.stream()
             .forEach(customer -> System.out.println(customer.getName() + " with id: " + customer.getId()));
     System.out.print("Choose an ID to view the orders: ");
     int selectedCustomerId = scanner.nextInt();
     scanner.nextLine();

     DataFactory.customers.stream()
             .filter(customer -> customer.getId() == selectedCustomerId)
             .findFirst()
             .ifPresentOrElse(
                     customer -> {
                         System.out.println(customer.getOrders());
                     },
                     () -> System.out.println("No customer found with ID " + selectedCustomerId)
             );
    }

    private void showAllMenuItems() {
        DataFactory.menuItems.stream()
                .forEach(menuItem ->
                        System.out.println(menuItem.getName()));
    }

    private void showOrdersWithFilters(Scanner scanner) {
        System.out.print("Enter order status to filter by (or press Enter to skip) ");
        String statusInput = scanner.nextLine().trim();

        System.out.print("Enter earliest order date (yyyy-MM-dd) to filter by (or press Enter to skip): ");
        String dateInput = scanner.nextLine().trim();

        OrderStatus filterStatus = null;
        if (!statusInput.isEmpty()) {
            try {
                filterStatus = OrderStatus.valueOf(statusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status, ignoring status filter.");
            }
        }

        LocalDate filterDate = null;
        if (!dateInput.isEmpty()) {
            try {
                filterDate = LocalDate.parse(dateInput);
            } catch (Exception e) {
                System.out.println("Invalid date format, ignoring date filter.");
            }
        }

        OrderStatus finalFilterStatus = filterStatus;
        LocalDate finalFilterDate = filterDate;
        DataFactory.customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> (finalFilterStatus == null || order.getStatus() == finalFilterStatus))
                .filter(order -> (finalFilterDate == null || order.getDate().isAfter(finalFilterDate)))
                .forEach(order -> System.out.println(order));

    }


    private void showOrdersByCustomer(Scanner scanner){
        DataFactory.customers.stream()
                .forEach(customer -> System.out.println(customer.getName() + " with id: " + customer.getId()));

        System.out.print("Choose an ID to view the order(s): ");
        int selectedCustomerId = scanner.nextInt();
        scanner.nextLine();

        Optional<Week1Customer> selectedCustomerOpt = DataFactory.customers.stream()
                .filter(customer -> customer.getId() == selectedCustomerId)
                .findFirst();

        if (selectedCustomerOpt.isPresent()) {
            Week1Customer selectedCustomer = selectedCustomerOpt.get();

            System.out.println("Orders placed by " + selectedCustomer.getName() + ":");
            selectedCustomer.getOrders().forEach(order ->
                    System.out.println("Order ID: " + order.getId())
            );

            System.out.print("Choose an ID to view the order: ");
            int orderId = scanner.nextInt();
            scanner.nextLine();

            Optional<Week1Order> foundOrder = selectedCustomer.getOrders().stream()
                    .filter(order -> order.getId() == orderId)
                    .findFirst();

            foundOrder.ifPresentOrElse(
                    order -> System.out.println(order),
                    () -> System.out.println("No order with ID " + orderId + " found for customer " + selectedCustomer.getName())
            );
        } else {
            System.out.println("No customer found with ID " + selectedCustomerId);
        }

    }


}
