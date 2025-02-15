package byow.Core;
import edu.princeton.cs.introcs.StdDraw;

/**
 * The player class.
 */
public class Menu {
  /**
   * Get the seed from the player.
   * @return the seed String.
   */
  public long getUserInputForSeed() {
    StringBuilder seedString = new StringBuilder();
    char key;

    while (true) {
      if (!StdDraw.hasNextKeyTyped()) {
        continue;
      }
      key = StdDraw.nextKeyTyped();
      key = Character.toUpperCase(key);
      if (Character.isDigit(key)) {
        seedString.append(key);
        StdDraw.clear();
        StdDraw.text(40, 15, "Seed:" + seedString);
        StdDraw.show();
      } else if (key == 'S' && seedString.length() > 0) {
        break;
      }
    }
    return Long.parseLong(seedString.toString());
  }

  /**
   * Main menu.
   *
   * @return the key.
   */
  public char getMainMenuInput() {
    while (true) {
      if (!StdDraw.hasNextKeyTyped()) {
        continue;
      }
      char key = StdDraw.nextKeyTyped();
      if (key == 'N' || key == 'L' || key == 'Q') {
        return key;
      }
    }
  }
}
