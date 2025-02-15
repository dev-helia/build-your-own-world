package byow.Core;

/**
 * The room class.
 */
public class Room {
  // size of the room
  int x, y, width, height;

  /**
   * Constructor.
   *
   * @param x of Bottom-Left Corner
   * @param y of Bottom-Left Corner
   * @param width of the room
   * @param height of the room
   */
  public Room(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Determines if this room intersects with another room.
   *
   * @param other the target room.
   * @return true if there is intersection, false otherwise.
   */
  public boolean intersects(Room other) {
    return this.x < other.x + other.width
            && this.x + this.width > other.x
            && this.y < other.y + other.height
            && this.y + this.height > other.y;
  }
}
