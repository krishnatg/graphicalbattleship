import java.rmi.Naming;

/**
 * Server.java
 * Created by Sanket Agarwal and Krishna Tippur Gururaj on 11/12/16.
 * This file contains the server-side logic for playing Battleship over RMI.
 */
public class Server {
    public static void main(String args[]) {
        int port = 30097 ; // can be changed as needed
        try {
            LocateRegistry.createRegistry(port) ;
        }
        catch (RemoteException e) {
            e.printStackTrace() ;
            System.err.println("unable to start RMI registry on " + port) ;
            System.exit(1) ;
        }
        System.out.println("Battleship game started - Server side") ;
        try {
            String str = "//localhost:" + port + "/BattleshipObj" ;
            Game g = new Game() ;
            Naming.rebind(str, g) ;
            System.out.println("Bound in registry") ;
        }
        catch (Exception e) {
            e.printStackTrace() ;
        }

    }
}
