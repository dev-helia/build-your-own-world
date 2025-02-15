package byow.Core;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.*;
import byow.TileEngine.TETile;

public class SaveLoad {
  private  static final String SAVE_FILE = "savefile.ser";

  /**
   * Store world to file.
   *
   * @param world A world in need of storage.
   */
  public static void saveWorld(TETile[][] world) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
      out.writeObject(world);
    } catch (IOException e) {
      System.out.println("Error saving world");
      e.printStackTrace();
    }
  }

  /**
   * Load the world from the file.
   * @return The world is loaded, or null is returned to indicate no archive.
   */
  public static TETile[][] loadWorld() {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
      return (TETile[][]) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("No previous save found or failed to load.");
      return null;
    }
  }
}
