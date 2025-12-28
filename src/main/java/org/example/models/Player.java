package org.example.models;

import org.example.common.Utils;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private final String name;
    private final List<Hotel> hotels;
    int position;

    public Player(String name) {
        this.name = name;
        this.hotels = new ArrayList<>();
        this.money=1000;
        this.position=-1;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "money=" + money +
                ", name='" + name + '\'' +
                ", hotel=" + getHotels() +
                ", position=" + position +
                '}';
    }

    public String getAssets() {
        int assets=getHotelWorth();
        return "Player{" +
                "money=" + money +
                ", name='" + name + '\'' +
                ", hotel=" + getHotels() +
                ", position=" + position +
                ", assets=" + assets +
                '}';
    }

    private int getHotelWorth(){
        int value=0;
        for(Hotel hotel:this.getHotels()){
            int index=Utils.hotelTypes.indexOf(hotel.getType());
            value+=Utils.hotelCost.get(index);
        }

        return value;
    }
}
