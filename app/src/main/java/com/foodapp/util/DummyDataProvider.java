package com.foodapp.util;

import com.foodapp.models.MenuItem;
import com.foodapp.models.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides dummy data for the application
 * Used for development and testing purposes
 */
public class DummyDataProvider {
    private static DummyDataProvider instance;
    private final List<Restaurant> restaurants;
    private final Map<Integer, List<MenuItem>> menuItemsMap;

    private DummyDataProvider() {
        // Initialize restaurants
        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1, "Italian Delight", "Italian", "123 Main St, New York, NY", 4.5f, 15, 25, true));
        restaurants.add(new Restaurant(2, "Spice Garden", "Indian", "456 Oak Ave, New York, NY", 4.2f, 20, 35, true));
        restaurants.add(new Restaurant(3, "Sushi World", "Japanese", "789 Pine Blvd, New York, NY", 4.7f, 25, 40, false));
        restaurants.add(new Restaurant(4, "Taco Heaven", "Mexican", "101 Elm St, New York, NY", 4.0f, 10, 20, true));
        restaurants.add(new Restaurant(5, "Burger Joint", "American", "202 Maple Dr, New York, NY", 4.3f, 12, 25, true));
        restaurants.add(new Restaurant(6, "Noodle House", "Chinese", "303 Cherry Ln, New York, NY", 4.1f, 15, 30, true));
        restaurants.add(new Restaurant(7, "Mediterranean Palace", "Mediterranean", "404 Olive Rd, New York, NY", 4.6f, 20, 30, true));
        restaurants.add(new Restaurant(8, "Seoul Kitchen", "Korean", "505 Peach St, New York, NY", 4.4f, 25, 35, true));
        restaurants.add(new Restaurant(9, "Thai Spice", "Thai", "606 Walnut Ave, New York, NY", 4.2f, 15, 25, false));
        restaurants.add(new Restaurant(10, "Falafel King", "Middle Eastern", "707 Pineapple Blvd, New York, NY", 4.3f, 20, 30, true));

        // Initialize menu items
        menuItemsMap = new HashMap<>();

        // Italian restaurant menu
        List<MenuItem> italianMenu = new ArrayList<>();
        italianMenu.add(new MenuItem(1, 1, "Margherita Pizza", "Classic pizza with tomato sauce and mozzarella", 12.99));
        italianMenu.add(new MenuItem(2, 1, "Pepperoni Pizza", "Pizza topped with pepperoni slices", 14.99));
        italianMenu.add(new MenuItem(3, 1, "Caesar Salad", "Fresh romaine lettuce with caesar dressing", 8.99));
        italianMenu.add(new MenuItem(4, 1, "Garlic Bread", "Toasted bread with garlic butter", 5.99));
        italianMenu.add(new MenuItem(5, 1, "Spaghetti Bolognese", "Spaghetti with meat sauce", 13.99));
        italianMenu.add(new MenuItem(6, 1, "Tiramisu", "Classic Italian dessert", 7.99));
        menuItemsMap.put(1, italianMenu);

        // Indian restaurant menu
        List<MenuItem> indianMenu = new ArrayList<>();
        indianMenu.add(new MenuItem(7, 2, "Butter Chicken", "Chicken in creamy tomato sauce", 15.99));
        indianMenu.add(new MenuItem(8, 2, "Vegetable Samosas", "Crispy pastry filled with spiced vegetables", 6.99));
        indianMenu.add(new MenuItem(9, 2, "Chicken Biryani", "Fragrant rice dish with chicken and spices", 14.99));
        indianMenu.add(new MenuItem(10, 2, "Naan Bread", "Traditional flatbread", 3.99));
        indianMenu.add(new MenuItem(11, 2, "Paneer Tikka Masala", "Cheese cubes in spiced sauce", 13.99));
        indianMenu.add(new MenuItem(12, 2, "Gulab Jamun", "Sweet milk dumplings in syrup", 5.99));
        menuItemsMap.put(2, indianMenu);

        // Japanese restaurant menu
        List<MenuItem> japaneseMenu = new ArrayList<>();
        japaneseMenu.add(new MenuItem(13, 3, "Salmon Sushi (2 pcs)", "Fresh salmon on rice", 6.99));
        japaneseMenu.add(new MenuItem(14, 3, "California Roll", "Crab, avocado and cucumber roll", 8.99));
        japaneseMenu.add(new MenuItem(15, 3, "Miso Soup", "Traditional Japanese soup", 3.99));
        japaneseMenu.add(new MenuItem(16, 3, "Chicken Teriyaki", "Grilled chicken with teriyaki sauce", 14.99));
        japaneseMenu.add(new MenuItem(17, 3, "Tempura Udon", "Noodle soup with tempura", 12.99));
        japaneseMenu.add(new MenuItem(18, 3, "Green Tea Ice Cream", "Traditional Japanese ice cream", 4.99));
        menuItemsMap.put(3, japaneseMenu);

        // Mexican restaurant menu
        List<MenuItem> mexicanMenu = new ArrayList<>();
        mexicanMenu.add(new MenuItem(19, 4, "Beef Tacos (3)", "Corn tortillas with beef filling", 9.99));
        mexicanMenu.add(new MenuItem(20, 4, "Chicken Quesadilla", "Flour tortilla with chicken and cheese", 10.99));
        mexicanMenu.add(new MenuItem(21, 4, "Guacamole & Chips", "Fresh avocado dip with tortilla chips", 7.99));
        mexicanMenu.add(new MenuItem(22, 4, "Beef Burrito", "Large flour tortilla filled with beef and beans", 12.99));
        mexicanMenu.add(new MenuItem(23, 4, "Nachos Supreme", "Tortilla chips topped with cheese, beans, and meat", 11.99));
        mexicanMenu.add(new MenuItem(24, 4, "Churros", "Fried dough pastry with cinnamon sugar", 5.99));
        menuItemsMap.put(4, mexicanMenu);

        // American restaurant menu
        List<MenuItem> americanMenu = new ArrayList<>();
        americanMenu.add(new MenuItem(25, 5, "Classic Cheeseburger", "Beef patty with cheese on a bun", 10.99));
        americanMenu.add(new MenuItem(26, 5, "Chicken Sandwich", "Grilled chicken with lettuce and mayo", 9.99));
        americanMenu.add(new MenuItem(27, 5, "French Fries", "Crispy potato fries", 4.99));
        americanMenu.add(new MenuItem(28, 5, "Buffalo Wings (8 pcs)", "Spicy chicken wings", 12.99));
        americanMenu.add(new MenuItem(29, 5, "Caesar Wrap", "Grilled chicken with caesar salad in a wrap", 8.99));
        americanMenu.add(new MenuItem(30, 5, "Apple Pie", "Classic American dessert", 6.99));
        menuItemsMap.put(5, americanMenu);

        // Add similar menus for the rest of the restaurants
        // Chinese restaurant menu (6)
        List<MenuItem> chineseMenu = new ArrayList<>();
        chineseMenu.add(new MenuItem(31, 6, "Kung Pao Chicken", "Spicy stir-fried chicken with peanuts", 13.99));
        chineseMenu.add(new MenuItem(32, 6, "Beef Chow Mein", "Stir-fried noodles with beef", 12.99));
        chineseMenu.add(new MenuItem(33, 6, "Vegetable Spring Rolls (4)", "Crispy rolls filled with vegetables", 6.99));
        chineseMenu.add(new MenuItem(34, 6, "Sweet and Sour Pork", "Battered pork in sweet and sour sauce", 14.99));
        chineseMenu.add(new MenuItem(35, 6, "Wonton Soup", "Clear soup with pork dumplings", 7.99));
        menuItemsMap.put(6, chineseMenu);

        // Mediterranean restaurant menu (7)
        List<MenuItem> mediterraneanMenu = new ArrayList<>();
        mediterraneanMenu.add(new MenuItem(36, 7, "Greek Salad", "Fresh vegetables with feta cheese", 9.99));
        mediterraneanMenu.add(new MenuItem(37, 7, "Lamb Gyro", "Pita bread with grilled lamb and tzatziki", 12.99));
        mediterraneanMenu.add(new MenuItem(38, 7, "Hummus with Pita", "Chickpea dip with pita bread", 7.99));
        mediterraneanMenu.add(new MenuItem(39, 7, "Chicken Souvlaki", "Grilled chicken skewers", 14.99));
        mediterraneanMenu.add(new MenuItem(40, 7, "Baklava", "Sweet pastry with nuts and honey", 5.99));
        menuItemsMap.put(7, mediterraneanMenu);

        // Korean restaurant menu (8)
        List<MenuItem> koreanMenu = new ArrayList<>();
        koreanMenu.add(new MenuItem(41, 8, "Beef Bulgogi", "Marinated grilled beef", 15.99));
        koreanMenu.add(new MenuItem(42, 8, "Kimchi Fried Rice", "Fried rice with spicy fermented cabbage", 12.99));
        koreanMenu.add(new MenuItem(43, 8, "Bibimbap", "Rice bowl with vegetables, beef and egg", 14.99));
        koreanMenu.add(new MenuItem(44, 8, "Kimchi Pancake", "Savory pancake with kimchi", 9.99));
        koreanMenu.add(new MenuItem(45, 8, "Japchae", "Sweet potato noodles with vegetables", 13.99));
        menuItemsMap.put(8, koreanMenu);

        // Thai restaurant menu (9)
        List<MenuItem> thaiMenu = new ArrayList<>();
        thaiMenu.add(new MenuItem(46, 9, "Pad Thai", "Stir-fried rice noodles with shrimp", 13.99));
        thaiMenu.add(new MenuItem(47, 9, "Green Curry with Chicken", "Spicy coconut curry", 14.99));
        thaiMenu.add(new MenuItem(48, 9, "Tom Yum Soup", "Hot and sour soup with shrimp", 8.99));
        thaiMenu.add(new MenuItem(49, 9, "Papaya Salad", "Shredded green papaya salad", 9.99));
        thaiMenu.add(new MenuItem(50, 9, "Mango Sticky Rice", "Sweet dessert with coconut milk", 6.99));
        menuItemsMap.put(9, thaiMenu);

        // Middle Eastern restaurant menu (10)
        List<MenuItem> middleEasternMenu = new ArrayList<>();
        middleEasternMenu.add(new MenuItem(51, 10, "Falafel Plate", "Chickpea fritters with rice and salad", 11.99));
        middleEasternMenu.add(new MenuItem(52, 10, "Chicken Shawarma Wrap", "Marinated chicken in pita", 10.99));
        middleEasternMenu.add(new MenuItem(53, 10, "Tabbouleh Salad", "Parsley and bulgur wheat salad", 8.99));
        middleEasternMenu.add(new MenuItem(54, 10, "Baba Ganoush", "Eggplant dip with pita", 7.99));
        middleEasternMenu.add(new MenuItem(55, 10, "Baklava", "Sweet pastry with nuts", 5.99));
        menuItemsMap.put(10, middleEasternMenu);
    }

    public static synchronized DummyDataProvider getInstance() {
        if (instance == null) {
            instance = new DummyDataProvider();
        }
        return instance;
    }

    public List<Restaurant> getAllRestaurants() {
        return new ArrayList<>(restaurants);
    }

    public List<MenuItem> getMenuItemsForRestaurant(int restaurantId) {
        if (menuItemsMap.containsKey(restaurantId)) {
            return new ArrayList<>(menuItemsMap.get(restaurantId));
        }
        return new ArrayList<>(); // Return empty list if restaurant id not found
    }

    public Restaurant getRestaurantById(int id) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == id) {
                return restaurant;
            }
        }
        return null;
    }
}