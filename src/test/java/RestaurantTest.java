import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class RestaurantTest {
    Restaurant restaurant;

    // Added common function to add Dummy Restaurant
    public Restaurant createRestaurantWithMenuForTesting(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        return restaurant;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        // Arrange
        restaurant = createRestaurantWithMenuForTesting();
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        // Mock Local time to fall under the Opening and Closing time
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("13:00:00"));

        // Act
        boolean isRestaurantOpen = spiedRestaurant.isRestaurantOpen();

        // Assert
        assertEquals(true, isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        // Arrange
        restaurant = createRestaurantWithMenuForTesting();
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        // Mock Local time to fall under the Opening and Closing time
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("09:00:00"));

        // Act
        boolean isRestaurantOpen = spiedRestaurant.isRestaurantOpen();

        // Assert
        assertEquals(false, isRestaurantOpen);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant = createRestaurantWithMenuForTesting();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant = createRestaurantWithMenuForTesting();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant = createRestaurantWithMenuForTesting();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /*<<<<<<<<<<<<<<<<<<<<<<<Get Total Cost - Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    @Test
    public void get_total_cost_should_return_correct_sum_of_all_the_items_added_in_the_list(){
        // Arrange
        restaurant = createRestaurantWithMenuForTesting();
        restaurant.addToMenu("Steak with mashed potatoes",326);
        restaurant.addToMenu("French Fries", 80);
        restaurant.addToMenu("Tiramisu", 180);

        int expectedTotal = 326 + 80 + 180;
        List<String> menuItemsToBeTotaled = new ArrayList<String>();
        menuItemsToBeTotaled.add("Steak with mashed potatoes");
        menuItemsToBeTotaled.add("French Fries");
        menuItemsToBeTotaled.add("Tiramisu");

        // Act
        int totalCost = restaurant.calculateTotalOrderCost(menuItemsToBeTotaled);

        // Assert
        assertEquals(expectedTotal, totalCost);
    }

    @Test
    public void get_total_cost_should_return_zero_if_list_passed_is_empty(){
        // Arrange
        restaurant = createRestaurantWithMenuForTesting();
        List<String> menuItemsToBeTotaled = new ArrayList<String>();

        // Act
        int totalCost = restaurant.calculateTotalOrderCost(menuItemsToBeTotaled);

        // Assert
        assertEquals(0, totalCost);
    }
    /*<<<<<<<<<<<<<<<<<<<<<<<Get Total Cost - End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
}