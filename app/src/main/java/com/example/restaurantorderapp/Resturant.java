package com.example.restaurantorderapp;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Resturant
{
    private Map<String, Double> mainCourse = new HashMap<>();
    private Map<String, Double> sideDish = new HashMap<>();
    private Map<String, Double> drink = new HashMap<>();
    private Map<String, Double> extras = new HashMap<>();
    private Map<String, Double> size = new HashMap<>();
    public Resturant() {

        mainCourse.put("Burger", 6.49);
        mainCourse.put("Pasta", 7.99);
        mainCourse.put("Pizza", 8.99);

        sideDish.put("None", 0.0);
        sideDish.put("French Fries", 2.49);
        sideDish.put("Onion Rings", 2.99);
        sideDish.put("Salad", 3.49);

        drink.put("None", 0.0);
        drink.put("Water", 1.00);
        drink.put("Coke", 1.49);
        drink.put("Pepsi", 4.49);
        drink.put("Orange Juice", 5.49);
        drink.put("Lemonade", 9.49);


        size.put("Small", 1.00);
        size.put("Medium", 1.50);
        size.put("Large", 2.00);

        extras.put("None", 0.0);
        extras.put("Sauce", 0.30);
        extras.put("Cheese", 0.50);
        extras.put("Add-ons", 0.75);


    }
    private int quantity = 0;
    private double totalCost = 0.0;
    public double calculateTotalCost(String main, String side, String drinkChoice, String sizeChoice, String extra) {
        totalCost = 0.0;

        if (mainCourse.containsKey(main)) totalCost += mainCourse.get(main);
        if (sideDish.containsKey(side)) totalCost += sideDish.get(side);
        if (drinkChoice != null && !drinkChoice.isEmpty() && drink.containsKey(drinkChoice)) {
            totalCost += drink.get(drinkChoice);
        }
        if (sizeChoice != null && !sizeChoice.isEmpty() && size.containsKey(sizeChoice)) {
            totalCost += size.get(sizeChoice);
        }
        if (extra != null && !extra.isEmpty() && extras.containsKey(extra)) {
            totalCost += extras.get(extra);
        }

        return totalCost;
    }
    public List<String> getMainCourseItemsWithPrices() {
        List<String> itemsWithPrices = new ArrayList<>();
        for (Map.Entry<String, Double> entry : mainCourse.entrySet()) {
            itemsWithPrices.add(entry.getKey() + " - $" + String.format("%.2f", entry.getValue()));
        }
        return itemsWithPrices;
    }
  public List<String> getSizeItemsWithPrices() {
        List<String> sizesWithPrices = new ArrayList<>();
        for (Map.Entry<String, Double> entry : size.entrySet()) {
            sizesWithPrices.add(entry.getKey() + " - $" + String.format("%.2f", entry.getValue()));
        }
        return sizesWithPrices;
    }

  public List<String> getextrasItemsWithPrices() {
        List<String> extrastionWithPrices = new ArrayList<>();
        for (Map.Entry<String, Double> entry : extras.entrySet()) {
            extrastionWithPrices.add(entry.getKey() + " - $" + String.format("%.2f", entry.getValue()));
        }
        return extrastionWithPrices ;
    }
 public List<String> getexDrinkItemsWithPrices() {
        List<String> DrinlItemsWithPrices = new ArrayList<>();
        for (Map.Entry<String, Double> entry : drink.entrySet()) {
            DrinlItemsWithPrices.add(entry.getKey() + " - $" + String.format("%.2f", entry.getValue()));
        }
        return DrinlItemsWithPrices ;
    }
public List<String> getexSideDishWithPrices() {
        List<String> SideDishWithPrices = new ArrayList<>();
        for (Map.Entry<String, Double> entry : sideDish.entrySet()) {
            SideDishWithPrices.add(entry.getKey() + " - $" + String.format("%.2f", entry.getValue()));
}
        return SideDishWithPrices ;
    }


}
