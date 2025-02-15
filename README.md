### **2D Tile-Based World Exploration Engine**

#### **Phase 1: World Generation**
- Implement a **random world generator** that creates **different but reproducible** maps based on a given seed value.
- The generated world should be stored as a `TETile[][]` array and returned as the output.

#### **Phase 2: Interactivity**
- Introduce a **player avatar** that can move using `WASD` controls.
- Implement **game saving and loading** functionality.

#### **Additional Features:**
- A **main menu** allowing players to select **New Game (N), Load Game (L), and Quit (Q)**.
- **No external libraries** are allowed, except for the provided `byow.TileEngine` & `byow.Core` code.
- **Final grade depends on fulfilling core requirements + additional features** (e.g., fog of war, AI enemies, advanced save systems).

---

## **📌 Project Breakdown**

### **🌍 Phase 1: World Generation**
The goal of this phase is to **randomly generate a 2D world** that includes:
- **Multiple random rooms and hallways**, ensuring all rooms are connected.
- **Walls and floors must be visually distinct**, with unused areas clearly marked.
- **Seed-based deterministic world generation**, ensuring the same seed generates an identical world.
- **No mutable static variables** (to prevent debugging issues).
- **The generated world must be returned as a `TETile[][]`** via `interactWithInputString()`.

---

### **🎮 Phase 2: Interactivity**
At this stage, you should already have a **stable world generator**. Now, add:
1. **A player avatar (`@`)**.
2. **`WASD` movement controls**.
3. **Save (`:Q`) and load (`L`) functionality**.

---

## **📂 Project Directory Structure**

### **🟢 `byow/Core/` (Core Logic)**
> Handles **world generation, interaction, and input processing**.

- **`Engine.java`**
    - 🌎 **The main engine for BYOW**, managing world creation and interaction.
    - Implements:
        - `interactWithInputString(String input)`: Handles input strings (e.g., `"N12345S"`), generates a world, and returns `TETile[][]` without rendering.
        - `interactWithKeyboard()`: Handles live keyboard input and renders the game in real-time.

| Method | Description | Notes |
| -------- | ----------- | ----- |
| `public TETile[][] interactWithInputString(String input)` | **Parses input strings**, simulates user keystrokes, and returns the final world state as a `TETile[][]` array. **Does not render anything!** | - Processes commands like `"N12345S"` to generate a **deterministic** world.<br>- Does not render, only returns `TETile[][]`.<br>- Supports **saving (`:Q`) and loading (`L`)**. |
| `public void interactWithKeyboard()` | **Handles real-time keyboard inputs**, rendering the world and allowing player control. | - **Displays the main menu first:**<br>    - `N`: Prompts for seed input and generates a world.<br>    - `L`: Loads the last saved game.<br>- **Game loop:**<br>    - Listens for `WASD` input.<br>    - Updates `TETile[][]` accordingly.<br>- **Handles saving:**<br>    - `:Q` saves and exits. |

---

- **`Main.java`**
    - 🚀 **The entry point of the game**.
    - Calls `Engine.java` methods based on command-line arguments.
- **`RandomUtils.java`**
    - 🎲 **Utility class for generating random numbers**.

---

### **🟡 `byow/TileEngine/` (Rendering Engine)**
> Manages **tile-based rendering and world visualization**.

- **`TERenderer.java`**
    - 📺 **Handles screen rendering**.
    - Call `initialize(width, height)` to initialize the screen and `renderFrame(TETile[][] tiles)` to render the map.
- **`TETile.java`**
    - 🧱 **Tile representation** (e.g., walls, floors, avatars).
- **`Tileset.java`**
    - 📜 **Predefined tiles** (e.g., `WALL`, `FLOOR`, `NOTHING`).

---

### **🟣 `byow/InputDemo/` (Keyboard Input)**
> Demonstrates how to read user inputs.

---

### **🟠 `byow/lab12` & `byow/lab13` (Labs)**
> Labs to help understand **StdDraw (graphics) and user interaction**.

- **Lab 12**: Demonstrates **StdDraw rendering**.
- **Lab 13**: Demonstrates **keyboard input handling**.

---

### **🔵 `byow/Networking/` (Networking - Not Used)**
> This package includes `BYOWClient` and `BYOWServer`, likely for **multiplayer networking**, but is **not required** for this project.

---

## **📌 Code Overview**

| **Module**      | **File**                  | **Description**                                        | **Est. Lines of Code** |
| -------------- | ------------------------ | --------------------------------------------------- | ----------------- |
| **Core Engine** | `Engine.java`             | Implements `interactWithInputString()` and `interactWithKeyboard()` | 150 ~ 250 LOC |
| **World Gen**   | `WorldGenerator.java`<br>`Room.java` | Generates random rooms and hallways | 200 ~ 300 LOC |
| **Rendering**   | `TERenderer.java`         | Renders `TETile[][]` | **Provided** |
| **Tile Handling** | `TETile.java`, `Tileset.java` | Defines map tiles (walls, floors, player) | **Provided** |
| **Player Control** | `Player.java`           | Handles `WASD` movement, updates world state | 100 ~ 150 LOC |
| **Saving & Loading** | `SaveLoad.java`       | Implements `:Q` for saving and `L` for loading | 80 ~ 150 LOC |
| **Menu UI**     | `Menu.java`               | Handles `N`, `L`, `Q` selections | 50 ~ 100 LOC |
| **Random Utils** | `RandomUtils.java`       | Generates random values for world generation | **Provided** |

---

## **🔍 BYOW vs. Gitlet: Code Complexity Comparison**

| Project | Lines of Code | Difficulty | Key Challenges |
| ------- | ------------ | ---------- | -------------- |
| **Gitlet** | **1500+ LOC** | **Data structures & version control** | Implementing `commit`, `branch`, `checkout`, `merge`, requiring **file operations, hash storage, and serialization**. |
| **BYOW** | **800 ~ 1200 LOC** | **Game development & interactivity** | Requires **random world generation**, managing **player movement, saving/loading, and rendering**. However, data structures are simpler than Gitlet. |

---

## **🎯 Key Takeaways**
- **World generation** is driven by **random but reproducible seed-based algorithms**.
- **Player movement** follows `WASD` logic and prevents **wall collisions**.
- **Saving and loading** allows for persistent world states (`:Q` and `L`).
- **Rendering engine** (`TERenderer`) manages **tile-based visualization**.

---

## **✅ What’s Next?**

If you want to add **extra features**, consider:
- **🧩 Items & power-ups**: Add collectible objects.
- **🗺️ Mini-map or fog of war**: Improve exploration.
- **🤖 AI enemies**: Implement simple NPC movement.


>[!warning]
>Your project should only use standard java libraries (imported from java.*).

