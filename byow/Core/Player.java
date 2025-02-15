package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw; // 画图库
import java.io.*; // 用于存档加载

public class Player {
  private final static int START_X = 5;
  private final static int START_Y = 5;
  /**
   * Control the player.
   *
   * @param world the world.
   */
  public void move(TETile[][] world) {
    int x = START_X, y = START_Y; // 初始位置
    world[x][y] = Tileset.PLAYER;
    StdDraw.show();

    while (true) {
      if (!StdDraw.hasNextKeyTyped()) continue;
      char key = Character.toUpperCase(StdDraw.nextKeyTyped());

      int newX = x, newY = y;
      if (key == 'W') newY++;
      if (key == 'S') newY--;
      if (key == 'A') newX--;
      if (key == 'D') newX++;

      if (world[newX][newY] != Tileset.WALL) {
        world[x][y] = Tileset.FLOOR;
        x = newX;
        y = newY;
        world[x][y] = Tileset.PLAYER;
        StdDraw.show();
      }

      // 退出保存
      if (key == ':') {
        while (!StdDraw.hasNextKeyTyped());
        char nextKey = Character.toUpperCase(StdDraw.nextKeyTyped());
        if (nextKey == 'Q') {
          SaveLoad.saveWorld(world); // 调用 SaveLoad 类的方法
          break;
        }
      }
    }
  }
}
