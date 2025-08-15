package kdg.programming3.RestaurantApp.repository.Memory;

import kdg.programming3.RestaurantApp.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryDataFactory {
    private static final List<Order> orders = new ArrayList<>();
    private static final List<MenuItem> menuItems = new ArrayList<>();
    private static final List<Customer> customers = new ArrayList<>();

    static {
        MenuItem chickenPoke = new MenuItem(1L, "chicken_poke_bowl", 13.50, List.of(Allergen.SOY, Allergen.SESAME, Allergen.GLUTEN), 650,"chicken.png",ItemType.FOOD);
        MenuItem salmonPoke = new MenuItem(2L, "salmon_poke_bowl", 15.00, List.of(Allergen.FISH, Allergen.SOY, Allergen.SESAME), 700,"salmon.png",ItemType.FOOD);
        MenuItem shrimpPoke = new MenuItem(3L, "shrimp_poke_bowl", 14.50, List.of(Allergen.CRUSTACEANS, Allergen.SOY, Allergen.SESAME), 620,"shrimp.png",ItemType.FOOD);
        MenuItem tofuPoke = new MenuItem(4L, "tofu_poke_bowl", 12.50, List.of(Allergen.SOY, Allergen.SESAME, Allergen.GLUTEN), 580,"tofu.png",ItemType.FOOD);
        MenuItem tunaPoke = new MenuItem(5L, "tuna_poke_bowl", 15.50, List.of(Allergen.FISH, Allergen.SOY, Allergen.SESAME), 680,"tuna.png",ItemType.FOOD);

        MenuItem combo1 = new MenuItem(6L, "sushi_combo_1", 18.00, List.of(Allergen.FISH, Allergen.CRUSTACEANS, Allergen.SOY), 800,"combo1.png",ItemType.FOOD);
        MenuItem combo2 = new MenuItem(7L, "sushi_combo_2", 22.00, List.of(Allergen.FISH, Allergen.CRUSTACEANS, Allergen.SOY), 950,"combo2.png",ItemType.FOOD);

        MenuItem caipirinha = new MenuItem(8L, "caipirinha", 9.00, List.of(), 250,"caipirinha.png",ItemType.DRINK);
        MenuItem espressoMartini = new MenuItem(9L, "espresso_martini", 11.00, List.of(), 280,"espresso_martini.png",ItemType.DRINK);
        MenuItem paloma = new MenuItem(10L, "paloma", 10.00, List.of(), 200,"paloma.png",ItemType.DRINK);
        MenuItem pinaColada = new MenuItem(11L, "pina_colada", 10.50, List.of(Allergen.DAIRY), 350,"pina.png",ItemType.DRINK);

        menuItems.addAll(List.of(chickenPoke, salmonPoke, shrimpPoke, tofuPoke, tunaPoke, combo1, combo2, caipirinha, espressoMartini, paloma, pinaColada));


        Customer john = new Customer(1L, "John", "Doe",  "john@gmail.com",LocalDate.of(2025,5,23));
        Customer jane = new Customer(2L, "Jane",  "Stewart",  "jane@gmail.com",LocalDate.of(2025,6,14));
        Customer jasmine = new Customer(3L, "Jasmine",  "Tadeu",  "jasmine@gmail.com",LocalDate.of(2024,7,28));

        customers.addAll(List.of(john, jane, jasmine));

        Order order1 = new Order(1L, LocalDate.now(), OrderStatus.PENDING, john);
        order1.addMenuItem(chickenPoke);
        order1.addMenuItem(paloma);

        Order order2 = new Order(2L, LocalDate.now().minusDays(1), OrderStatus.DELIVERED, jane);
        order2.addMenuItem(combo1);
        order2.addMenuItem(combo2);

        Order order3 = new Order(3L, LocalDate.now().minusDays(2), OrderStatus.PREPARING, john);
        order3.addMenuItem(tunaPoke);
        order3.addMenuItem(pinaColada);

        Order order4 = new Order(4L,LocalDate.now().minusDays(1), OrderStatus.DELIVERED, jasmine);
        order4.addMenuItem(salmonPoke);
        order4.addMenuItem(espressoMartini);

        orders.addAll(List.of(order1, order2, order3, order4));
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static List<Customer> getCustomers() {
        return customers;
    }

    public static List<MenuItem> getMenuItems() {
        return menuItems;
    }
}