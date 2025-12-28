package org.example.service;

import org.example.common.Utils;
import org.example.models.Hotel;
import org.example.models.Player;

import java.util.HashMap;

public class PlayerManager {

    private final HashMap<String, Player> playersList;
    private final HotelManager hotelManager ;
    private String currentPlayerName;
    private int bankMoney=5000;

    public int getBankMoney() {
        return bankMoney;
    }

    public void setBankMoney(int bankMoney) {
        this.bankMoney = bankMoney;
    }

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

    public int getSize(){
        return playersList.size();
    }
    public void printDetailsWithAssets() {
        for(Player player:playersList.values()){
            System.out.println(player.getAssets());
        }
    }

    public Player getPlayer() {
        return playersList.get(currentPlayerName);
    }

    public void deductDueToJail(){
        Player currentPlayer=getPlayer();
        int currentMoney=currentPlayer.getMoney();
        if(currentMoney<150) {
            System.out.println("Player does not have enough amount");
            return;
        }
        int money=currentMoney-150;
        currentPlayer.setMoney(money);
        this.setBankMoney(this.getBankMoney()+150);
    }

    public void runLotteryFunctionality(){
        if(this.getBankMoney()<200){
            System.out.println("Bank does not have enough amount");
            return ;
        }
        Player currentPlayer=playersList.get(currentPlayerName);
        int currentMoney=currentPlayer.getMoney();
        int money=currentMoney+200;
        currentPlayer.setMoney(money);
        this.setBankMoney(this.getBankMoney()-200);
    }

    public void getMoneyByReachingHotel(int newPosition){
        Player currentPlayer=playersList.get(currentPlayerName);
        Hotel hotel=hotelManager.getHotel(newPosition);
        if(hotel==null){
            allocateHotel(currentPlayer,newPosition);
        }
        else{
            if(hotel.getOwner().getName().equals(currentPlayer.getName())){
                updateHotelType(currentPlayer,hotel);
            }
            else{
                payRent(currentPlayer,hotel);
            }
        }
    }

    private void allocateHotel(Player currentPlayer,int newPosition){
        if(currentPlayer.getMoney()>=Utils.hotelCost.getFirst()){
            Hotel newHotel=new Hotel(currentPlayer);
            currentPlayer.getHotels().add(newHotel);
            hotelManager.putHotel(newPosition,newHotel);
            int updatedMoney=currentPlayer.getMoney()-Utils.hotelCost.getFirst();
            currentPlayer.setMoney(updatedMoney);
            this.setBankMoney(this.getBankMoney()+Utils.hotelCost.getFirst());
        }
    }

    private void payRent(Player currentPlayer, Hotel hotel){
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

    private void updateHotelType(Player currentPlayer, Hotel hotel){
        int currentMoney=currentPlayer.getMoney();
        int index=Utils.hotelTypes.indexOf(hotel.getType());
        if(index+1<Utils.hotelTypes.size()){
            int costDifference=Utils.hotelCost.get(index+1)-Utils.hotelCost.get(index);
            if(currentMoney>=costDifference){
                hotel.setType(Utils.hotelTypes.get(index+1));
                currentPlayer.setMoney(currentMoney-costDifference);
                this.setBankMoney(this.getBankMoney()+costDifference);
            }
        }

    }
}
