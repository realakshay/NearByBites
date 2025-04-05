package com.foodapp.utils;

import com.foodapp.models.MenuItem;
import com.foodapp.models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class to generate sample data for testing
 */
public class DataGenerator {

    public static List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        
        // Create sample restaurants
        Restaurant r1 = new Restaurant(
                1,
                "Green Leaf Vegetarian",
                "Vegetarian",
                4.7f,
                1.2f,
                "20-30 min",
                "2 Hinjewadi Road, Phase 1",
                "https://images.unsplash.com/photo-1615719413546-198b25453f85?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=736&q=80",
                "$$",
                getMenuItemsForRestaurant(1),
                Arrays.asList("20% off on first order", "Free delivery on orders above $20")
        );
        
        Restaurant r2 = new Restaurant(
                2,
                "Pizza Palace",
                "Italian",
                4.5f,
                0.8f,
                "15-25 min",
                "5 IT Park, Phase 2",
                "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
                "$$$",
                getMenuItemsForRestaurant(2),
                Arrays.asList("Buy 1 Get 1 Free on Tuesdays")
        );
        
        Restaurant r3 = new Restaurant(
                3,
                "Spice Junction",
                "Indian",
                4.8f,
                2.5f,
                "30-40 min",
                "15 MG Road, Wakad",
                "https://images.unsplash.com/photo-1585937421612-70a008356cf4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=736&q=80",
                "$$",
                getMenuItemsForRestaurant(3),
                Arrays.asList("Free naan with every curry")
        );
        
        Restaurant r4 = new Restaurant(
                4,
                "Burger Barn",
                "Fast Food",
                4.3f,
                1.0f,
                "15-20 min",
                "10 Main Street, Baner",
                "https://images.unsplash.com/photo-1572802419224-296b0aeee0d9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1115&q=80",
                "$",
                getMenuItemsForRestaurant(4),
                Arrays.asList("Free fries on orders above $15")
        );
        
        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);
        restaurants.add(r4);
        
        return restaurants;
    }
    
    private static List<MenuItem> getMenuItemsForRestaurant(int restaurantId) {
        List<MenuItem> menuItems = new ArrayList<>();
        
        switch (restaurantId) {
            case 1: // Green Leaf Vegetarian
                menuItems.add(new MenuItem(101, "Garden Fresh Salad", "Mixed greens, tomatoes, cucumber with olive oil dressing", 7.99, "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80", "Salad", true, false));
                menuItems.add(new MenuItem(102, "Veggie Burger", "Plant-based patty with lettuce, tomato, and special sauce", 10.99, "https://images.unsplash.com/photo-1520072959219-c595dc870360?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1290&q=80", "Burger", false, true));
                menuItems.add(new MenuItem(103, "Tofu Stir Fry", "Tofu and vegetables in savory sauce with rice", 12.99, "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1160&q=80", "Main Course", false, false));
                break;
            case 2: // Pizza Palace
                menuItems.add(new MenuItem(201, "Margherita Pizza", "Classic cheese and tomato sauce", 14.99, "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80", "Pizza", true, true));
                menuItems.add(new MenuItem(202, "Pepperoni Pizza", "Classic pepperoni on cheese pizza", 16.99, "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1176&q=80", "Pizza", false, true));
                menuItems.add(new MenuItem(203, "Garlic Breadsticks", "Freshly baked with garlic butter", 6.99, "https://images.unsplash.com/photo-1619531038896-deaff53d151a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1160&q=80", "Sides", false, false));
                break;
            case 3: // Spice Junction
                menuItems.add(new MenuItem(301, "Butter Chicken", "Chicken in creamy tomato sauce", 15.99, "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80", "Main Course", true, true));
                menuItems.add(new MenuItem(302, "Palak Paneer", "Cottage cheese in spinach gravy", 13.99, "https://images.unsplash.com/photo-1596797038530-2c107aa7ad9c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1025&q=80", "Main Course", false, false));
                menuItems.add(new MenuItem(303, "Garlic Naan", "Flatbread with garlic", 3.99, "https://images.unsplash.com/photo-1565557623262-b51c2513a641?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1071&q=80", "Bread", false, true));
                break;
            case 4: // Burger Barn
                menuItems.add(new MenuItem(401, "Classic Cheeseburger", "Beef patty with cheese and fixings", 8.99, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1899&q=80", "Burger", true, true));
                menuItems.add(new MenuItem(402, "Crispy Chicken Sandwich", "Fried chicken fillet with mayo and lettuce", 7.99, "https://images.unsplash.com/photo-1606755962773-d324e0a13086?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1287&q=80", "Sandwich", false, true));
                menuItems.add(new MenuItem(403, "French Fries", "Crispy golden fries", 3.99, "https://images.unsplash.com/photo-1576107232684-1279f390859f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1160&q=80", "Sides", false, false));
                break;
        }
        
        return menuItems;
    }
    
    public static boolean addToCart(int restaurantId, int menuItemId, int quantity, CartManager cartManager) {
        List<Restaurant> restaurants = getRestaurants();
        
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == restaurantId) {
                for (MenuItem menuItem : restaurant.getMenuItems()) {
                    if (menuItem.getId() == menuItemId) {
                        cartManager.addItemToCart(menuItem, quantity);
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}
