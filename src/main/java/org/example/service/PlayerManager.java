package org.example.service;

import org.example.common.Utils;
import org.example.models.Hotel;
import org.example.models.Player;

import java.util.HashMap;


public class PlayerManager {

    private final HashMap<String, Player> playersList;
    private final HotelManager hotelManager ;
    private String currentPlayerName;

    public PlayerManager(int players){
        playersList=new HashMap<>();
        for(int i=0;i<players;i++){
            playersList.put(Integer.toString(i+1),new Player(Integer.toString(i+1)));
        }
        currentPlayerName="1";
        this.hotelManager=new HotelManager();
    }

    public void updateTurn(){
        int value= Integer.parseInt(currentPlayerName);
        if(value==playersList.size()){
            value=0;
        }
        currentPlayerName=Integer.toString(value+1);
    }

    public boolean deductDueToJail(){
        Player currentPlayer=getPlayer();
        int currentMoney=currentPlayer.getMoney();
        if(currentMoney>=150){
            int money=currentMoney-150;
            currentPlayer.setMoney(money);
            return true;
        }
        return false;
    }

    public int getSize(){
        return playersList.size();
    }

    public void getBonus(){
        Player currentPlayer=playersList.get(currentPlayerName);
        int currentMoney=currentPlayer.getMoney();
        int money=currentMoney+200;
        currentPlayer.setMoney(money);
    }

    public int getMoneyByReachingHotel(int newPosition){
        Player currentPlayer=playersList.get(currentPlayerName);
        Hotel hotel=hotelManager.getHotel(newPosition);
        if(hotel==null){
            if(currentPlayer.getMoney()>=200){
                Hotel newHotel=new Hotel(currentPlayer);
                currentPlayer.getHotels().add(newHotel);
                hotelManager.putHotel(newPosition,newHotel);
                int updatedMoney=currentPlayer.getMoney()-200;
                currentPlayer.setMoney(updatedMoney);
                return 200;
            }
        }
        else{
            if(hotel.getOwner().getName().equals(currentPlayer.getName())){
                return increaseHotelType(currentPlayer,hotel);
            }
            else{
                Player owner=hotel.getOwner();
                int money=currentPlayer.getMoney();
                int index=Utils.hotelTypes.indexOf(hotel.getType());
                int rent=Utils.hotelRent.get(index);
                if(money>=rent){
                    int ownerUpdatedMoney=owner.getMoney()+rent;
                    owner.setMoney(ownerUpdatedMoney);
                    int currentPlayerUpdatedMoney=currentPlayer.getMoney()-rent;
                    currentPlayer.setMoney(currentPlayerUpdatedMoney);
                }

                
            }
        }
        return 0;
    }

    public void printDetailsWithAssets() {
        System.out.println("Final Result");
        for(Player player:playersList.values()){
            System.out.println(player.getAssets());
        }
    }

    public Player getPlayer() {
        return playersList.get(currentPlayerName);
    }

    private int increaseHotelType(Player currentPlayer, Hotel hotel){
        int currentMoney=currentPlayer.getMoney();
        int index=Utils.hotelTypes.indexOf(hotel.getType());
        int newIndex=index+1;
        if(newIndex<Utils.hotelTypes.size()){
            if(currentMoney>=Utils.hotelCost.get(newIndex)-Utils.hotelCost.get(index)){
                hotel.setType(Utils.hotelTypes.get(newIndex));
                int currentPlayerUpdatedMoney=currentMoney-(Utils.hotelCost.get(newIndex)-Utils.hotelCost.get(index));
                currentPlayer.setMoney(currentPlayerUpdatedMoney);
                return Utils.hotelCost.get(newIndex)-Utils.hotelCost.get(index);
            }
        }
        return 0;
    }
}
