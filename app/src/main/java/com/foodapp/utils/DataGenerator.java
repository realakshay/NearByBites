package com.foodapp.utils;

import com.foodapp.models.MenuItem;
import com.foodapp.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    
    public static List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        
        // Create Mcdonalds
        Restaurant mcdonalds = new Restaurant(
            "1",
            "McDonalds",
            "Fast Food, American Food, Pasta",
            4.9f,
            "10km",
            "30mins",
            "Cummins India Office Campus, Pune",
            "https://www.halloessen.de/blog/wp-content/uploads/2020/11/isola-pizza-pasta-eiscafe-burger-mfrh-original-scaled.jpg",
            "$$"
        );
        
        // Add promo items
        List<MenuItem> mcdonaldsPromoItems = new ArrayList<>();
        mcdonaldsPromoItems.add(new MenuItem("1", "6-pc. Chicken McShare Box", 10, "-20%", "https://pbs.twimg.com/media/Cvcbo8uXEAA1nED.jpg"));
        mcdonaldsPromoItems.add(new MenuItem("2", "McSpicy Chicken Meal", 18, "-15%", "https://5.imimg.com/data5/NH/WD/MY-33353830/mcspicy-chicken-burger.png"));
        mcdonaldsPromoItems.add(new MenuItem("3", "McNuggets", 26, "-10%", "https://i0.wp.com/www.womansworld.com/wp-content/uploads/2024/11/bowl-of-mcdonalds-1-mcnuggets-deal.jpg?crop=0px%2C93px%2C2121px%2C1210px&resize=1280%2C730&ssl=1&quality=86&strip=all"));
        mcdonalds.setPromoItems(mcdonaldsPromoItems);
        
        // Add menu items
        List<MenuItem> mcdonaldsMenuItems = new ArrayList<>();
        mcdonaldsMenuItems.add(new MenuItem("1", "Big Mac", 35, "https://thelogic.co/wp-content/uploads/2025/02/BigMac_red-TheLogic_Illustration-P_Handout-1920x1280-1.jpg"));
        mcdonaldsMenuItems.add(new MenuItem("2", "McFries", 43, "https://mir-s3-cdn-cf.behance.net/projects/404/6dc733168216917.Y3JvcCwxMzgwLDEwODAsMjcwLDA.png"));
        mcdonaldsMenuItems.add(new MenuItem("3", "McNuggets", 66, "https://i0.wp.com/www.womansworld.com/wp-content/uploads/2024/11/bowl-of-mcdonalds-1-mcnuggets-deal.jpg?crop=0px%2C93px%2C2121px%2C1210px&resize=1280%2C730&ssl=1&quality=86&strip=all"));
        mcdonalds.setMenuItems(mcdonaldsMenuItems);
        
        restaurants.add(mcdonalds);
        
        // Create Jollibee
        Restaurant jollibee = new Restaurant(
            "2",
            "Jollibee",
            "Fast Food, Filipino Food",
            4.3f,
            "5km",
            "25mins",
            "Phoenix Market City, Pune",
            "https://www.jollibeeus.com/wp-content/uploads/2020/01/WebImage_300x250_new.jpg",
            "$"
        );
        
        // Add promo items
        List<MenuItem> jollibeePromoItems = new ArrayList<>();
        jollibeePromoItems.add(new MenuItem("1", "Chickenjoy Bucket", 15, "-15%", "https://www.jollibeeus.com/wp-content/uploads/2020/01/WebImage_300x250_new.jpg"));
        jollibeePromoItems.add(new MenuItem("2", "Jolly Spaghetti Family Pan", 12, "-10%", "https://www.jollibeeus.com/wp-content/uploads/2020/01/WebImage_300x250_new.jpg"));
        jollibee.setPromoItems(jollibeePromoItems);
        
        // Add menu items
        List<MenuItem> jollibeeMenuItems = new ArrayList<>();
        jollibeeMenuItems.add(new MenuItem("1", "Chickenjoy", 25, "https://www.jollibeeus.com/wp-content/uploads/2020/01/WebImage_300x250_new.jpg"));
        jollibeeMenuItems.add(new MenuItem("2", "Jolly Spaghetti", 20, "https://www.jollibeeus.com/wp-content/uploads/2020/01/WebImage_300x250_new.jpg"));
        jollibeeMenuItems.add(new MenuItem("3", "Peach Mango Pie", 15, "https://www.jollibeeus.com/wp-content/uploads/2020/01/WebImage_300x250_new.jpg"));
        jollibee.setMenuItems(jollibeeMenuItems);
        
        restaurants.add(jollibee);
        
        // Create KFC
        Restaurant kfc = new Restaurant(
            "3",
            "KFC",
            "Fast Food, Chicken",
            4.2f,
            "8km",
            "35mins",
            "Amanora Mall, Pune",
            "https://online.kfc.co.in/static/media/SEO_IMAGE.4f84f104.jpg",
            "$$"
        );
        
        // Add promo items
        List<MenuItem> kfcPromoItems = new ArrayList<>();
        kfcPromoItems.add(new MenuItem("1", "Bucket for Two", 20, "-25%", "https://online.kfc.co.in/static/media/SEO_IMAGE.4f84f104.jpg"));
        kfcPromoItems.add(new MenuItem("2", "Zinger Combo", 18, "-20%", "https://online.kfc.co.in/static/media/SEO_IMAGE.4f84f104.jpg"));
        kfc.setPromoItems(kfcPromoItems);
        
        // Add menu items
        List<MenuItem> kfcMenuItems = new ArrayList<>();
        kfcMenuItems.add(new MenuItem("1", "Original Recipe Chicken", 30, "https://online.kfc.co.in/static/media/SEO_IMAGE.4f84f104.jpg"));
        kfcMenuItems.add(new MenuItem("2", "Hot Wings", 25, "https://online.kfc.co.in/static/media/SEO_IMAGE.4f84f104.jpg"));
        kfcMenuItems.add(new MenuItem("3", "Zinger Burger", 28, "https://online.kfc.co.in/static/media/SEO_IMAGE.4f84f104.jpg"));
        kfc.setMenuItems(kfcMenuItems);
        
        restaurants.add(kfc);
        
        // Create Pizza Hut
        Restaurant pizzaHut = new Restaurant(
            "4",
            "Pizza Hut",
            "Pizza, Italian Food",
            4.1f,
            "12km",
            "40mins",
            "Viman Nagar, Pune",
            "https://order.pizzahut.com/assets/w/marketing/images/oh-3133_oh_desktop_hero_v1.jpg",
            "$$"
        );
        
        // Add promo items
        List<MenuItem> pizzaHutPromoItems = new ArrayList<>();
        pizzaHutPromoItems.add(new MenuItem("1", "Family Feast", 25, "-30%", "https://order.pizzahut.com/assets/w/marketing/images/oh-3133_oh_desktop_hero_v1.jpg"));
        pizzaHutPromoItems.add(new MenuItem("2", "Pepperoni Lovers", 22, "-25%", "https://order.pizzahut.com/assets/w/marketing/images/oh-3133_oh_desktop_hero_v1.jpg"));
        pizzaHut.setPromoItems(pizzaHutPromoItems);
        
        // Add menu items
        List<MenuItem> pizzaHutMenuItems = new ArrayList<>();
        pizzaHutMenuItems.add(new MenuItem("1", "Margherita Pizza", 35, "https://order.pizzahut.com/assets/w/marketing/images/oh-3133_oh_desktop_hero_v1.jpg"));
        pizzaHutMenuItems.add(new MenuItem("2", "Chicken Supreme", 42, "https://order.pizzahut.com/assets/w/marketing/images/oh-3133_oh_desktop_hero_v1.jpg"));
        pizzaHutMenuItems.add(new MenuItem("3", "Garlic Bread", 15, "https://order.pizzahut.com/assets/w/marketing/images/oh-3133_oh_desktop_hero_v1.jpg"));
        pizzaHut.setMenuItems(pizzaHutMenuItems);
        
        restaurants.add(pizzaHut);
        
        // Create Burger King
        Restaurant burgerKing = new Restaurant(
            "5",
            "Burger King",
            "Fast Food, Burgers",
            4.0f,
            "7km",
            "30mins",
            "FC Road, Pune",
            "https://s.yimg.com/ny/api/res/1.2/Vrkvl_4j6Z4mGnYjQXzTDw--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MDtoPTQwMA--/https://s.yimg.com/os/creatr-uploaded-images/2023-06/9aaf83a0-111f-11ee-9d9d-2470f99f01da",
            "$$"
        );
        
        // Add promo items
        List<MenuItem> burgerKingPromoItems = new ArrayList<>();
        burgerKingPromoItems.add(new MenuItem("1", "Whopper Meal", 18, "-20%", "https://s.yimg.com/ny/api/res/1.2/Vrkvl_4j6Z4mGnYjQXzTDw--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MDtoPTQwMA--/https://s.yimg.com/os/creatr-uploaded-images/2023-06/9aaf83a0-111f-11ee-9d9d-2470f99f01da"));
        burgerKingPromoItems.add(new MenuItem("2", "King Box", 15, "-15%", "https://s.yimg.com/ny/api/res/1.2/Vrkvl_4j6Z4mGnYjQXzTDw--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MDtoPTQwMA--/https://s.yimg.com/os/creatr-uploaded-images/2023-06/9aaf83a0-111f-11ee-9d9d-2470f99f01da"));
        burgerKing.setPromoItems(burgerKingPromoItems);
        
        // Add menu items
        List<MenuItem> burgerKingMenuItems = new ArrayList<>();
        burgerKingMenuItems.add(new MenuItem("1", "Whopper", 30, "https://s.yimg.com/ny/api/res/1.2/Vrkvl_4j6Z4mGnYjQXzTDw--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MDtoPTQwMA--/https://s.yimg.com/os/creatr-uploaded-images/2023-06/9aaf83a0-111f-11ee-9d9d-2470f99f01da"));
        burgerKingMenuItems.add(new MenuItem("2", "Chicken Fries", 25, "https://s.yimg.com/ny/api/res/1.2/Vrkvl_4j6Z4mGnYjQXzTDw--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MDtoPTQwMA--/https://s.yimg.com/os/creatr-uploaded-images/2023-06/9aaf83a0-111f-11ee-9d9d-2470f99f01da"));
        burgerKingMenuItems.add(new MenuItem("3", "Onion Rings", 20, "https://s.yimg.com/ny/api/res/1.2/Vrkvl_4j6Z4mGnYjQXzTDw--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MDtoPTQwMA--/https://s.yimg.com/os/creatr-uploaded-images/2023-06/9aaf83a0-111f-11ee-9d9d-2470f99f01da"));
        burgerKing.setMenuItems(burgerKingMenuItems);
        
        restaurants.add(burgerKing);
        
        return restaurants;
    }
}
