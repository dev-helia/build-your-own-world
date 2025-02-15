package byow.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final int MIN_ROOM_SIZE = 3;
    private static final int MAX_ROOM_SIZE = 8;
    // Rooms should not be close to the right or top edge of the map.
    private static final int ROOM_EDGE_MARGIN = 2;
    // Make sure the room is not attached to the left border or bottom.
    private static final int ROOM_MIN_OFFSET = 1;
    private List<Room> rooms;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
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

        return generateWorld(seed);
    }

    /**
     * Generates a new world based on a given random seed.
     *  * This method initializes the world as a 2D grid of tiles and ensures all
     *  * tiles are initially set to `Tileset.NOTHING`. It also initializes the
     *  * list of rooms that will later be added to the world.
     *
     *
     * @param seed The random seed used to generate a deterministic world layout.
     * @return A 2D array of TETile representing the generated world.
     */
    private TETile[][] generateWorld(long seed) {
        Random random = new Random(seed);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        rooms = new ArrayList<Room>(); // Initialize the room list.
        // Initialization: all tiles are NOTHING.
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // Generate 8-15 rooms randomly.
        int numRooms = random.nextInt(8) + 8; // 8 + [0,7]，from 8 to 15
        for (int i = 0; i < numRooms; i++) {
            // The ROOM width ranges from MIN ROOM SIZE to MAX ROOM SIZE
            int roomWidth = random.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE) + MIN_ROOM_SIZE;
            // The ROOM height ranges from MIN ROOM SIZE to MAX ROOM SIZE
            int roomHeight = random.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE) + MIN_ROOM_SIZE;
            // Buffer area.
            int roomX = random.nextInt(WIDTH - roomWidth - ROOM_EDGE_MARGIN)
                    + ROOM_MIN_OFFSET;
            // Buffer area.
            int roomY = random.nextInt(HEIGHT - roomHeight - ROOM_EDGE_MARGIN)
                    + ROOM_MIN_OFFSET;

            Room newRoom = new Room(roomX, roomY, roomWidth, roomHeight);
            if (!isOverlapping(newRoom)) {
                rooms.add(newRoom);
                addRoomToWorld(world, newRoom);
            }
        }
        return world;
    }

    /**
     * Determines if the new room is overlapping the other rooms in the list.
     *
     * @param newRoom the target room.
     * @return true if there is an overlapping, false otherwise.
     */
    private boolean isOverlapping(Room newRoom) {
        for (Room r : rooms) {
            if (r.intersects(newRoom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add a room to the World and fill the floor of the room with Tileset.FLOOR.
     *
     * @param world the target world.
     * @param room the target room.
     */
    private void addRoomToWorld(TETile[][] world, Room room) {
        if (room.x + room.width > world.length || room.y + room.height > world[0].length) {
            return;  // Prevention of transgression.
        }
        for (int x = room.x; x < room.x + room.width; x++) {
            for (int y = room.y; y < room.y + room.height; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    /**
     *
     * @param world
     */
    private void connectRooms(TETile[][] world) {
        for (int i = 0; i < rooms.size(); i++) {
            Room roomA = rooms.get(i);
            Room roomB = rooms.get(i + 1);
            // Take the center point of roomA as the starting point of the corridor (startX, startY).
            int startX = roomA.x + roomA.width / 2;
            int startY = roomA.y + roomA.height / 2;
            // Take the center point of roomB as the end point of the corridor (endX, endY).
            int endX = roomB.x + roomB.width / 2;
            int endY = roomB.y + roomB.height / 2;

            // First horizontal, then vertical.
            drawHorizontalHallway(world, startX, endX, startY);
            drawVerticalHallWay(world, startY, endY, endX);
        }
    }

    /**
     * Draws horizontal corridors on the world map.
     * @param world A 2D array representing the entire game world.
     * @param x1 Starting x-coordinate.
     * @param x2 Terminal X-coordinate (center of another room).
     * @param y The y-coordinate of the corridor is fixed.
     */
    private void drawHorizontalHallway(TETile[][] world, int x1, int x2, int y) {
        int start = Math.min(x1, x2);
        int end = Math.max(x1, x2);
        for (int x = start; x < end; x++) {
            world[x][y] = Tileset.FLOOR;
        }
    }

    /**
     * Draws vertical corridors on the world map.
     * @param world A 2D array representing the entire game world.
     * @param y1 Starting Y coordinate.
     * @param y2 Terminal Y coordinate (center of another room).
     * @param x The X-coordinate of the corridor is fixed.
     */
    private void drawVerticalHallWay(TETile[][] world, int y1, int y2, int x) {
        int start = Math.min(y1, y2);
        int end = Math.max(y1, y2);
        for (int y = start; y < end; y++) {
            world[x][y] = Tileset.FLOOR;
        }
    }
}
