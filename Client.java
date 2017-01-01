import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.rmi.Naming;

/**
 * Client.java
 * Created by Krishna Tippur Gururaj and Sanket Agarwal on 11/12/16.
 * This file handles the client-side working for the Battleship game.
 */
public class Client extends Application {

    static GameInterface gi = null ;
    static int playerID = 0 ;
    static String playerName = null ;
    private TextField playerUsername ;
    private int howManyHit = 0 ;

    public static void printGridOnServer() {
        try {
            gi.printGrid(playerID);
        }
        catch (Exception e) {
            e.printStackTrace() ;
        }
    }

    public void start(Stage stage) {
        stage.setTitle("Battleship Game");
        BorderPane bp = new BorderPane() ;
        bp.maxHeight(1000) ;
        bp.maxWidth(1000) ;
        TextField errField = new TextField() ;
        errField.setEditable(false);
        bp.setTop(errField);
        FlowPane namePane = new FlowPane() ;
        namePane.getChildren().add(new Label("Name")) ;
        playerUsername = new TextField() ;
        playerUsername.setPromptText("Enter player name here");
        playerUsername.setPrefColumnCount(20);
        namePane.getChildren().add(playerUsername) ;
        Button nameEntered = new Button("Create player") ;
        Button detachPlayer = new Button("Detach player") ;

        namePane.getChildren().add(nameEntered) ;
        namePane.getChildren().add(detachPlayer) ;
        bp.setLeft(namePane);
        VBox arrangement = new VBox() ;

        FlowPane caArrangement = new FlowPane() ;
        TextField caPosRow = new TextField() ;
        caPosRow.setPromptText("Row");
        caPosRow.setPrefColumnCount(2);
        TextField caPosCol = new TextField() ;
        caPosCol.setPromptText("Column");
        caPosCol.setPrefColumnCount(2);
        TextField caOrientation = new TextField() ;
        caOrientation.setPromptText("Orientation");
        caOrientation.setPrefColumnCount(1);
        Button caEntered = new Button("Carrier") ;
        caEntered.setDisable(true);
        caArrangement.getChildren().add(caPosRow) ;
        caArrangement.getChildren().add(caPosCol) ;
        caArrangement.getChildren().add(caOrientation) ;
        caArrangement.getChildren().add(caEntered) ;
        arrangement.getChildren().add(caArrangement) ;

        FlowPane bsArrangement = new FlowPane() ;
        TextField bsPosRow = new TextField() ;
        bsPosRow.setPromptText("Row");
        bsPosRow.setPrefColumnCount(2);
        TextField bsPosCol = new TextField() ;
        bsPosCol.setPromptText("Column");
        bsPosCol.setPrefColumnCount(2);
        TextField bsOrientation = new TextField() ;
        bsOrientation.setPromptText("Orientation");
        bsOrientation.setPrefColumnCount(1);
        Button bsEntered = new Button("Battleship") ;
        bsEntered.setDisable(true);
        bsArrangement.getChildren().add(bsPosRow) ;
        bsArrangement.getChildren().add(bsPosCol) ;
        bsArrangement.getChildren().add(bsOrientation) ;
        bsArrangement.getChildren().add(bsEntered) ;
        arrangement.getChildren().add(bsArrangement) ;

        FlowPane crArrangement = new FlowPane() ;
        TextField crPosRow = new TextField() ;
        crPosRow.setPromptText("Row");
        crPosRow.setPrefColumnCount(2);
        TextField crPosCol = new TextField() ;
        crPosCol.setPromptText("Column");
        crPosCol.setPrefColumnCount(2);
        TextField crOrientation = new TextField() ;
        crOrientation.setPromptText("Orientation");
        crOrientation.setPrefColumnCount(1);
        Button crEntered = new Button("Cruiser") ;
        crEntered.setDisable(true);
        crArrangement.getChildren().add(crPosRow) ;
        crArrangement.getChildren().add(crPosCol) ;
        crArrangement.getChildren().add(crOrientation) ;
        crArrangement.getChildren().add(crEntered) ;
        arrangement.getChildren().add(crArrangement) ;

        FlowPane deArrangement = new FlowPane() ;
        TextField dePosRow = new TextField() ;
        dePosRow.setPromptText("Row");
        dePosRow.setPrefColumnCount(2);
        TextField dePosCol = new TextField() ;
        dePosCol.setPromptText("Column");
        dePosCol.setPrefColumnCount(2);
        TextField deOrientation = new TextField() ;
        deOrientation.setPrefColumnCount(1);
        deOrientation.setPromptText("Orientation");
        Button deEntered = new Button("Destroyer") ;
        deEntered.setDisable(true);
        deArrangement.getChildren().add(dePosRow) ;
        deArrangement.getChildren().add(dePosCol) ;
        deArrangement.getChildren().add(deOrientation) ;
        deArrangement.getChildren().add(deEntered) ;
        arrangement.getChildren().add(deArrangement) ;

        Button playGame = new Button("Start Game") ;
        playGame.setDisable(true);

        arrangement.getChildren().add(playGame) ;

        FlowPane guess = new FlowPane() ;
        bp.setBottom(guess);
        TextField guessRow = new TextField() ;
        guessRow.setPrefColumnCount(2);
        TextField guessCol = new TextField() ;
        guessCol.setPrefColumnCount(2);
        Button guessBtn = new Button("Guess") ;
        guessBtn.setDisable(true);
        TextField guessResult = new TextField() ;
        guessResult.setEditable(false) ;

        guess.getChildren().add(guessRow) ;
        guess.getChildren().add(guessCol) ;
        guess.getChildren().add(guessBtn) ;
        guess.getChildren().add(guessResult) ;
        guess.setAlignment(Pos.BOTTOM_CENTER);

        detachPlayer.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                errField.setText("Detached from game. Close the window to quit screen.") ;
                nameEntered.setDisable(true);
                detachPlayer.setDisable(true);
                caEntered.setDisable(true);
                bsEntered.setDisable(true);
                crEntered.setDisable(true);
                deEntered.setDisable(true);
                playGame.setDisable(true);
                guessBtn.setDisable(true);
                guessResult.setText("Last hit counter for player was " + howManyHit);
            }
        });

        nameEntered.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerName = playerUsername.getText() ;
                if (playerName != null) {
                    try {
                        gi = (GameInterface) Naming.lookup("//localhost:30097/BattleshipObj") ;
                        boolean ret = gi.createPlayer(playerName) ;
                        if (!ret) {
                            errField.setText("Player count reached!") ;
                        }
                        else {
                            playerID = gi.getPlayerCount() ;
                            errField.setText("Player " + playerID + " successfully created");
                            nameEntered.setDisable(true);
                            caEntered.setDisable(false);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        caEntered.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int posRow = Integer.parseInt(caPosRow.getText()) ;
                if (posRow > 9 || posRow < 0) {
                    errField.setText("Invalid row for carrier");
                    return ;
                }
                int posCol = Integer.parseInt(caPosCol.getText()) ;
                if (posCol > 9 || posCol < 0) {
                    errField.setText("Invalid col for carrier");
                    return ;
                }
                String orientation = caOrientation.getText() ;
                if (!(orientation.equals("h") || orientation.equals("v"))) {
                    errField.setText("Orientation of carrier can only be h or v");
                    return ;
                }
                try {
                    boolean retval = gi.setFleet("carrier", posRow, posCol, orientation, playerID) ;
                    if (retval) {
                        caEntered.setDisable(true);
                        errField.setText("Carrier successfully set");
                        bsEntered.setDisable(false);
                    }
                    else {
                        errField.setText("Unable to set Carrier. Try again.");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

       bsEntered.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int posRow = Integer.parseInt(bsPosRow.getText()) ;
                if (posRow > 9 || posRow < 0) {
                    errField.setText("Invalid row for battleship");
                    return ;
                }
                int posCol = Integer.parseInt(bsPosCol.getText()) ;
                if (posCol > 9 || posCol < 0) {
                    errField.setText("Invalid col for battleship");
                    return ;
                }
                String orientation = bsOrientation.getText() ;
                if (!(orientation.equals("h") || orientation.equals("v"))) {
                    errField.setText("Orientation of battleship can only be h or v");
                    return ;
                }
                try {
                    boolean retval = gi.setFleet("battleship", posRow, posCol, orientation, playerID) ;
                    if (retval) {
                        bsEntered.setDisable(true);
                        errField.setText("Battleship successfully set");
                        crEntered.setDisable(false);
                    }
                    else {
                        errField.setText("Unable to set battleship. Try again.");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

       crEntered.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int posRow = Integer.parseInt(crPosRow.getText()) ;
                if (posRow > 9 || posRow < 0) {
                    errField.setText("Invalid row for cruiser");
                    return ;
                }
                int posCol = Integer.parseInt(crPosCol.getText()) ;
                if (posCol > 9 || posCol < 0) {
                    errField.setText("Invalid col for cruiser");
                    return ;
                }
                String orientation = crOrientation.getText() ;
                if (!(orientation.equals("h") || orientation.equals("v"))) {
                    errField.setText("Orientation of cruiser can only be h or v");
                    return ;
                }
                try {
                    boolean retval = gi.setFleet("cruiser", posRow, posCol, orientation, playerID) ;
                    if (retval) {
                        crEntered.setDisable(true);
                        errField.setText("Cruiser successfully set");
                        deEntered.setDisable(false);
                    }
                    else {
                        errField.setText("Unable to set Cruiser. Try again.");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

       deEntered.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int posRow = Integer.parseInt(dePosRow.getText()) ;
                if (posRow > 9 || posRow < 0) {
                    errField.setText("Invalid row for destroyer");
                    return ;
                }
                int posCol = Integer.parseInt(dePosCol.getText()) ;
                if (posCol > 9 || posCol < 0) {
                    errField.setText("Invalid col for destroyer");
                    return ;
                }
                String orientation = deOrientation.getText() ;
                if (!(orientation.equals("h") || orientation.equals("v"))) {
                    errField.setText("Orientation of destroyer can only be h or v");
                    return ;
                }
                try {
                    boolean retval = gi.setFleet("destroyer", posRow, posCol, orientation, playerID) ;
                    if (retval) {
                        deEntered.setDisable(true);
                        errField.setText("Destroyer successfully set");
                        printGridOnServer();
                        playGame.setDisable(false);
                    }
                    else {
                        errField.setText("Unable to set Destroyer. Try again.");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

       playGame.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                guessBtn.setDisable(false);
                playGame.setDisable(true);
            }
        });

        guessBtn.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (gi.isGameOver()) {
                        guessResult.setText("Winner is " + gi.getWinnerName());
                        guessBtn.setDisable(true);
                        return ;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                int row = Integer.parseInt(guessRow.getText()) ;
                if (row > 9 || row < 0) {
                    guessResult.setText("Invalid row guess");
                    return ;
                }
                int col = Integer.parseInt(guessCol.getText()) ;
                if (col > 9 || col < 0) {
                    guessResult.setText("Invalid col guess");
                    return ;
                }
                try {
                    boolean ret = gi.isHit(row, col, playerID) ;
                    if (ret) {
                        guessResult.setText("Hit at (" + row + ", " + col + ")");
                        howManyHit++ ;
                        if (howManyHit == 14) {
                            guessResult.setText("Congrats! You won!");
                            guessBtn.setDisable(true);
                        }
                    }
                    else {
                        String resStr = gi.getErrorMsg(playerID) ;
                        if (resStr.equals("Game over")) {
                            guessBtn.setDisable(true);
                            String winner = gi.getWinnerName() ;
                            if (winner.equals(playerName)) {
                                guessResult.setText("Congrats! You won!");
                            }
                            else {
                                guessResult.setText("You lost. Better luck next time.");
                            }
                        }
                        else {
                            guessResult.setText(gi.getErrorMsg(playerID));
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bp.setRight(arrangement);
        Scene scene = new Scene(bp) ;
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinHeight(300);
        stage.setMinWidth(600);
        stage.show() ;
    }

    public static void main(String args[]) {
        Application.launch(args);
    }
}
