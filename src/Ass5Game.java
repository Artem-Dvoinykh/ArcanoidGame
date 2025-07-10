/******************
 Name: Artem Dvoinykh
 ID: 345853543
 Assignment: Ex5
 *******************/

import utils.Game;

/**
 * Class to launch the game. Entering point for the program.
 */
public class Ass5Game {

    /**
     * Entering point for the program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
