/**
 * Player.java
 * Created by Sanket Agarwal and Krishna Tippur Gururaj on 11/12/16.
 * This file contains the Player class for playing the Battleship game.
 */
public class Player {
    String pName ;
    String[][] ocean ;

    public Player(String name) {
        this.pName = name ;
        this.ocean = new String[10][10] ;

        for (int i = 0 ; i < 10 ; i++) {
            for (int j = 0 ; j < 10 ; j++) {
                this.ocean[i][j] = "0" ;
            }
        }
    }

    public String getValueAt(int row, int col) {
        return ocean[row][col] ;
    }

    public void setValueTo(int row, int col, String str) {
        ocean[row][col] = str ;
        return ;
    }

    public String getName() {
        return pName ;
    }

}
