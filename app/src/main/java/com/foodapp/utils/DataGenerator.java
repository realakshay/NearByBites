package com.foodapp.utils;

import com.foodapp.models.MenuItem;
import com.foodapp.models.PromoItem;
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


        List<MenuItem> menuItems = getMenuItemsForRestaurant(1);

        List<PromoItem> promoItems = Arrays.asList(
            new PromoItem(
                1,
                "20% OFF First Order",
                "Get 20% off up to $5 on your first order!",
                "FIRST20",
                20.0,
                "https://example.com/promo1.jpg",
                "2025-12-31"
            ),
            new PromoItem(
                2,
                "Free Delivery",
                "Enjoy free delivery on orders above $20",
                "FREEDEL",
                0.0,
                "https://example.com/promo2.jpg",
                "2025-06-30"
            )
        );

        // Create sample restaurants
        Restaurant r1 = new Restaurant(
                1,
                "Green Leaf Vegetarian",
                "Vegetarian",
                "4.7",
                "5",
                "20-30 min",
                "2 Hinjewadi Road, Phase 1",
                "https://images.unsplash.com/photo-1615719413546-198b25453f85?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=736&q=80",
                "$$",
                menuItems,
                promoItems
        );

        restaurants.add(r1);

        return restaurants;
    }

    private static List<MenuItem> getMenuItemsForRestaurant(int restaurantId) {
        List<MenuItem> menuItems = new ArrayList<>();

        switch (restaurantId) {
            case 1: // Green Leaf Vegetarian
                menuItems.add(new MenuItem(101, restaurantId, "Garden Fresh Salad", "Mixed greens, tomatoes, cucumber with olive oil dressing", 7.99, "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80", "Salad", true, false));
                break;
        };

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
                    };
                };
            };
        };

        return false;
    }
}
