package com.foodapp.api;

import com.foodapp.model.MenuItem;
import com.foodapp.model.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodService {
    @GET("restaurants")
    Call<List<Restaurant>> getRestaurants();

    @GET("restaurants")
    Call<List<Restaurant>> searchRestaurants(@Query("query") String query);

    @GET("restaurants/{id}/menu")
    Call<List<MenuItem>> getMenuItems(@Path("id") int restaurantId);
}
