package com.example.myapplication;

public class Orders {
    int mealId;
    String mealName;
    int mealCount;
    int TotalCost;

    public Orders(int mealId, String mealName, int totalCost,int mealCount) {
        this.mealId = mealId;
        this.mealCount=mealCount;
        this.mealName = mealName;
        TotalCost = totalCost;
    }
}
