package org.example;

import org.example.service.BoardManager;


public class Main {
    public static void main(String[] args) {
//        String board= "J,H,L,H,E,L,H,L,H,J";
//        String diceOutput = "2,2,1,4,4,2,4,4,2,2,2,1,4,4,2,4,4,2,2,2,1";

        String board="J,H,L,H,E,L,H,L,H,J";
        String diceOutput = "2,2,1,4,2,3,4,1,3,2,2,7,4,7,2,4,4,2,2,2,2";

        int players= 3;
        board = board.replace(",", "");
        diceOutput = diceOutput.replace(",", "");

        BoardManager boardManager=new BoardManager(board,players);

        boardManager.pass(diceOutput);

        boardManager.printDetailsWithAssets();

    }
}