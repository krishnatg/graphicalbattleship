import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Game.java
 * Created by Krishna Tippur Gururaj and Sanket Agarwal on 11/12/16.
 * This file holds the RMI implementation of the Battleship Game
 */
public class Game extends UnicastRemoteObject implements GameInterface {
    static Player p1 = null ;
    static Player p2 = null ;
    private int playerCount ;
    boolean isGameOver ;
    String winner = null ;
    String errorMsgForP1 = null ;
    String errorMsgForP2 = null ;
    private int P1HitCount ;
    private int P2HitCount ;
    private boolean whoseTurnIsIt = true ; // true -> P1's chance, false -> P2's chance

    public Game() throws RemoteException {
        this.playerCount = 0 ;
        this.isGameOver = false ;
        this.winner = new String("") ;
        this.errorMsgForP1 = new String("") ;
        this.errorMsgForP2 = new String("") ;
        this.P1HitCount = 0 ;
        this.P2HitCount = 0 ;
    }

    public int getPlayerCount() throws RemoteException {
        return this.playerCount ;
    }

    public boolean isGameOver() throws RemoteException {
        return this.isGameOver ;
    }

    public boolean isHit(int row, int col, int playerID) throws RemoteException {
        if (whoseTurnIsIt) { // P1's chance
            if (playerID == 2) {
                errorMsgForP2 = "Not your turn";
                return false ;
            }
        }
        else { // P2's chance
            if (playerID == 1) {
                errorMsgForP1 = "Not your turn" ;
                return false ;
            }
        }
        if (isGameOver) {
            errorMsgForP1 = errorMsgForP2 = "Game over" ;
            return false ;
        }
        Player opponent ;
        boolean ret = false ;
        if (playerID == 1) {
            opponent = p2 ;
        }
        else if (playerID == 2) {
            opponent = p1 ;
        }
        else {
            System.err.println("Invalid player ID " + playerID + " passed to isHit()") ;
            return ret ;
        }
        if (opponent.getValueAt(row, col).equals("S")) {
            opponent.setValueTo(row, col, "D") ;
            if (playerID == 1) {
                P1HitCount++ ;
            }
            else {
                P2HitCount++ ;
            }
            whoseTurnIsIt = !whoseTurnIsIt ;
            ret = true ;
        }
        else if (opponent.getValueAt(row, col).equals("D")) {
            if (playerID == 1) {
                errorMsgForP1 = "This point (" + row + ", " + col + ") already hit";
            }
            if (playerID == 2) {
                errorMsgForP2 = "This point (" + row + ", " + col + ") already hit";
            }
            whoseTurnIsIt = !whoseTurnIsIt ;
            ret = false ;
        }
       else {
            if (playerID == 1) {
                errorMsgForP1 = "Miss at (" + row + ", " + col + ")";
            }
            if (playerID == 2) {
                errorMsgForP2 = "Miss at (" + row + ", " + col + ")";
            }
            whoseTurnIsIt = !whoseTurnIsIt ;
            ret = false;
        }
        if (P1HitCount == 14) {
            winner = p1.getName() ;
            isGameOver = true ;
        }
        if (P2HitCount == 14) {
            winner = p2.getName() ;
            isGameOver = true ;
        }
        return ret ;
    }

    public String getErrorMsg(int playerID) throws RemoteException {
        if (playerID == 1) {
            return this.errorMsgForP1 ;
        }
        else {
            return this.errorMsgForP2 ;
        }
    }

    public void printGrid(int playerID) throws RemoteException {
        Player p ;
        if (playerID == 1) {
            p = p1 ;
        }
        else {
            p = p2 ;
        }
        System.out.println("Grid for Player " + playerID + " : " + p.getName()) ;
        for (int i = 0 ; i < 10 ; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(p.getValueAt(i, j) + " ");
            }
            System.out.println() ;
        }
    }

    public boolean createPlayer(String pName) throws RemoteException {
        boolean ret = false ;
        if (playerCount >= 2) {
            return ret ;
        }
        if (playerCount == 0) {
            p1 = new Player(pName) ;
            ret = true ;
        }
        else if (playerCount == 1) {
            p2 = new Player(pName) ;
            ret = true ;
        }
        playerCount++ ;
        return ret ;
    }

    public boolean setFleet (String shipName, int row, int col, String orientation, int playerID) {
        Player p ;
        if (playerID == 1) {
            p = p1 ;
        }
        else if (playerID == 2) {
            p = p2 ;
        }
        else {
            System.err.println("Invalid player ID " + playerID + " passed to setFleet()");
            if (playerID == 1) {
                errorMsgForP1 = "Invalid player ID " + playerID + " passed to setFleet()" ;
            }
            else if (playerID == 2) {
                errorMsgForP2 = "Invalid player ID " + playerID + " passed to setFleet()" ;
            }
            return false;
        }
        return fleetArrangement(shipName, row, col, orientation, p) ;
    }

    public boolean fleetArrangement(String shipCommon, int posRow, int posColumn, String orientation, Player p) {
        boolean isVacant = true ;
        int shipSize ;

        if(shipCommon.equals("carrier")){
            shipSize = 5;
        }
        else if(shipCommon.equals("battleship")){
            shipSize = 4;
        }
        else if(shipCommon.equals("cruiser")){
            shipSize = 3;
        }
        else if(shipCommon.equals("destroyer")){
            shipSize = 2;
        }
        else{
            System.out.println("Not a battleship "+shipCommon);
            return false;
        }

        if(orientation.equals("v")){
            if(posRow < 0 || posRow > (9-shipSize)+1){
                // System.out.println("Enter a value more than 0 & less than or equal to"+((9-shipSize)+1)+" for row");
                isVacant = false;

            }
            else if(posColumn < 0 || posColumn > 9){
                // System.out.println("Enter a value more than 0 & less than or equal to 9 for column");
                isVacant = false;
            }
            else
            {
                if(p.getValueAt(Math.abs(posRow - 1), posColumn) == "S" || p.getValueAt(((posRow + shipSize) > 9 ? 9 : (posRow + shipSize)), posColumn) == "S") {
                    isVacant = false;
                    // System.out.println("There are adjacent ships on its top or bottom");
                }
                else if(true){
                    for(int i = posRow; i < posRow+shipSize; i++){
                        if(p.getValueAt(i, Math.abs(posColumn - 1)) == "S" || p.getValueAt(i, posColumn + 1) == "S") {
                            isVacant = false;
                            // System.out.println("There are adjacent ships on its left or right");
                            continue;

                        }
                        else if(p.getValueAt(i, posColumn) == "S") {
                            isVacant = false;
                            // System.out.println("You are overlapping a ship at position "+ i);
                            continue;

                        }
                    }

                }
                if(isVacant == true){
                    // System.out.println("poscolumn: " + posColumn + " lim: " + (posColumn+shipSize));

                    for(int i = posRow; i < posRow+shipSize; i++){
                        p.setValueTo(i, posColumn, "S") ;
                        // System.out.println("i: " + i + " j: " + posColumn + " " + "S");
                    }

                }

            }
        }
        else if(orientation.equals("h")){

            if(posRow < 0 || posRow > 9){
                // System.out.println("Enter a value more than 0 & less than or equal to 9 for row");
                isVacant = false;

            }
            else if(posColumn < 0 || posColumn > (9-shipSize)+1){
                // System.out.println("Enter a value more than 0 & less than or equal to "+((9-shipSize)+1)+"  for column");
                isVacant = false;
            }
            else
            {
                // if(p.ocean[posRow][Math.abs(posColumn-1)] == "S" || p.ocean[posRow][(posColumn + shipSize)>9?9:(posColumn + shipSize)] == "S"){
                if (p.getValueAt(posRow, (Math.abs(posColumn - 1))) == "S" || p.getValueAt(posRow, (posColumn + shipSize)>9?9:(posColumn + shipSize)) == "S") {
                    isVacant = false;
                    // System.out.println("There are adjacent ships on its left or right");
                }
                else if(true){
                    for(int i = posColumn; i < posColumn+shipSize; i++){
                        // if(p.ocean[Math.abs(posRow-1)][i] == "S" ||  (p.ocean[((posRow+1)>9?posRow:posRow+1)][i] == "S")){
                        if ((p.getValueAt((Math.abs(posRow - 1)), i) == "S") || (p.getValueAt(((posRow + 1) > 9 ? posRow : (posRow + 1)), i) == "S")) {
                            isVacant = false;
                            // System.out.println("There are adjacent ships on its top or bottom");
                            continue;

                        }
                        else if (p.getValueAt(posRow, i) == "S") {
                            // System.out.println("You are overlapping a ship at position "+ i);
                            isVacant = false;
                            continue;

                        }
                    }
                }
                if(isVacant == true){
                    // System.out.println("poscolumn: " + posColumn + " lim: " + (posColumn+shipSize));

                    for(int i = posColumn; i < posColumn+shipSize; i++){
                        p.setValueTo(posRow, i, "S");
                        // System.out.println("i: " + posRow + " j: " + i + " " + "S");
                    }

                }
            }

        }
        return isVacant ;
    }

    public boolean setWinner(String name) {
        if (!isGameOver) {
            this.winner = name ;
            return true ;
        }
        else {
            return false ;
        }
    }

    public String getWinnerName() {
        return this.winner ;
    }

}
