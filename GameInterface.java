/**
 * GameInterface.java
 * Created by Krishna Tippur Gururaj and Sanket Agarwal on 11/12/16.
 * This file lists out the interface methods for the RMI for Battleship game.
 */
public interface GameInterface extends java.rmi.Remote {
    boolean createPlayer(String pName) throws java.rmi.RemoteException ;
    boolean isHit(int row, int col, int playerID) throws java.rmi.RemoteException ;
    boolean isGameOver() throws java.rmi.RemoteException ;
    String getWinnerName() throws java.rmi.RemoteException ;
    int getPlayerCount() throws java.rmi.RemoteException ;
    boolean setFleet(String shipName, int row, int col, String orientation, int playerID) throws java.rmi.RemoteException ;
    String getErrorMsg(int playerID) throws java.rmi.RemoteException ;
    void printGrid (int playerID) throws java.rmi.RemoteException ;
}
