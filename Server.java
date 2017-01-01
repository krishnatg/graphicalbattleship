import java.rmi.Naming;

/**
 * Server.java
 * Created by Sanket Agarwal and Krishna Tippur Gururaj on 11/12/16.
 * This file contains the server-side logic for playing Battleship over RMI.
 */
public class Server {
    public static void main(String args[]) {
        System.out.println("Battleship game started - Server side") ;
        try {
            Game g = new Game() ;
            Naming.rebind("//localhost:30097/BattleshipObj", g) ;
            System.out.println("Bound in registry") ;
        }
        catch (Exception e) {
            e.printStackTrace() ;
        }

    }
}
