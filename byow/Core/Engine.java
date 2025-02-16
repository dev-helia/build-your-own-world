package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        // Player class.
        Menu menu = new Menu();
        Player player = new Player();

        char choice = menu.getMainMenuInput();
        if (choice == 'N') {
            long seed = menu.getUserInputForSeed();
            WorldGenerator worldGen = new WorldGenerator(WIDTH, HEIGHT, seed);
            TETile[][] world = worldGen.generateWorld();
            ter.renderFrame(world);
            player.move(world);
            SaveLoad.saveWorld(world);
        } else if (choice == 'L') {
            TETile[][] world = SaveLoad.loadWorld();
            if (world == null) {
                System.out.println("No save file found. Generating new world...");
                return;
            }
        } else if (choice == 'Q') {
            System.exit(0);
        }
    }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        input = input.toUpperCase();
        if (!input.startsWith("N") || !input.endsWith("S")) {
            throw new IllegalArgumentException();
        }

        String seedString = input.substring(1, input.length() - 1);
        long seed = Long.parseLong(seedString);

        WorldGenerator worldGen = new WorldGenerator(WIDTH, HEIGHT, seed);
        return worldGen.generateWorld();
    }
}
