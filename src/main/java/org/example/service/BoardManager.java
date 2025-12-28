package org.example.service;

import org.example.models.Player;
import org.example.common.Utils;

public class BoardManager {
    private final String board;
    private final PlayerManager playerManager;
    private int bankMoney;

    public int getBankMoney() {
        return bankMoney;
    }

    public void setBankMoney(int bankMoney) {
        this.bankMoney = bankMoney;
    }

    public  BoardManager(String board, int players){
        this.board=board;
        this.playerManager=new PlayerManager(players);
        this.bankMoney=5000;
    }

    private void printDetails(){
        playerManager.printDetailsWithAssets();
    }

    private int getNewPosition(String str){
        Player currentPlayer=playerManager.getPlayer();
        int position=currentPlayer.getPosition();
        int newPosition=position+Integer.parseInt(str);
        return newPosition %board.length();
    }

    private boolean isValid(String diceOutput){
        int size=playerManager.getSize();
        return diceOutput.length() == Utils.Turns * size;
    }
    public void pass(String diceOutput){
        if(!isValid(diceOutput)){
            return;
        }
        for(int i=0;i<diceOutput.length();i++){
            Player currentPlayer=playerManager.getPlayer();
            String landValue=String.valueOf(diceOutput.charAt(i));
            int newPosition=getNewPosition(landValue);
            takeActionOnLand(board.charAt(newPosition),newPosition);
            currentPlayer.setPosition(newPosition);
            playerManager.updateTurn();
            printDetails();
        }
    }

    private void runJailFunctionality(){
        boolean status=playerManager.deductDueToJail();
        if(status){
            this.setBankMoney(this.getBankMoney()+150);
        }
        else{
            System.out.println("Can not able to do that");
        }
    }

    private void runLotteryFunctionality(){
        if(bankMoney>=200){
            playerManager.getBonus();
            this.setBankMoney(this.getBankMoney()-200);
        }
        else{
            System.out.println("Can not able to do that");
        }
    }

    private void jailAndLotteryFunctionality(Character element ){
        if(element=='J'){
            runJailFunctionality();
        }
        else{
            runLotteryFunctionality();
        }
    }

    private void takeActionOnLand(Character element,int newPosition){
        if(element=='J' || element=='L'){
            jailAndLotteryFunctionality(element);
        }
        else if(element=='H'){
            int value=playerManager.getMoneyByReachingHotel(newPosition);
            this.setBankMoney(this.getBankMoney()+value);
        }
    }

    public void printDetailsWithAssets() {
        playerManager.printDetailsWithAssets();
        System.out.println("Money in the bank "+this.getBankMoney());
    }

}
