package org.example.service;

import org.example.models.Player;
import org.example.common.Utils;

public class BoardManager {
    private final String board;
    private final PlayerManager playerManager;


    public  BoardManager(String board, int players){
        this.board=board;
        this.playerManager=new PlayerManager(players);
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
//        if(!isValid(diceOutput)){
//            return;
//        }
        for(int i=0;i<diceOutput.length();i++){
            Player currentPlayer=playerManager.getPlayer();
            String landValue=String.valueOf(diceOutput.charAt(i));
            int newPosition=getNewPosition(landValue);
            takeActionOnLand(board.charAt(newPosition),newPosition);
            currentPlayer.setPosition(newPosition);
            playerManager.updateTurn();
            System.out.println("After operation "+i+" ");
            printDetailsWithAssets();
        }
    }

    private void runJailFunctionality(){
        playerManager.deductDueToJail();

    }

    private void runLotteryFunctionality(){
        playerManager.runLotteryFunctionality();
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
            playerManager.getMoneyByReachingHotel(newPosition);

        }
    }

    public void printDetailsWithAssets() {
        playerManager.printDetailsWithAssets();
        System.out.println("Money in the bank "+playerManager.getBankMoney());
    }

}
