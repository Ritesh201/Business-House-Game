package org.example.service;

import org.example.models.Hotel;
import java.util.HashMap;

public class HotelManager {
    HashMap<Integer, Hotel> map=new HashMap<>();

    public  Hotel getHotel(int index){
        if(map.containsKey(index)){
            return map.get(index);
        }
        return null;
    }

    public void putHotel(int index,Hotel hotel) {
        map.put(index,hotel);
    }
}
