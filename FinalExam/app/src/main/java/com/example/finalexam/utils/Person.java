package com.example.finalexam.utils;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshehab on 5/6/18.
 */

public class Person implements Serializable, Comparable<Person> {
    String id;
    String name;
    int totalBudget;
    int totalBought;
    int giftCount;

    List<Gift> gifts;

    public Person(String id, String name, Integer totalBudget) {
        this.id = id;
        this.name = name;
        this.totalBudget = totalBudget;
        this.totalBought = 0;
        this.giftCount = 0;
        gifts = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(int totalBudget) {
        this.totalBudget = totalBudget;
    }

    public int getTotalBought() {
        return totalBought;
    }

    public void setTotalBought(int totalBought) {
        this.totalBought = totalBought;
    }

    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }

    public List<Gift> getGifts() {
        if(gifts == null) {
            gifts = new ArrayList<>();
        }
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    @Override
    public int compareTo(@NonNull Person person) {
        return getName().compareTo(person.getName());
    }

    public void incrementGiftCount() {
        giftCount++;
    }

    public void incrementItemsBought(int price) {
        totalBought += price;
    }
}
