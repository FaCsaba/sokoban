package com.csabapro.sokoban.models;

public enum Direction {
  UP(0, -1),
  DOWN(0, 1),
  LEFT(-1, 0),
  RIGHT(1, 0);

  private final int col;
  private final int row;

  Direction(int col, int row) {
    this.col = col;
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public int getRow() {
    return row;
  }

  public Direction opposite() {
    switch (this) {
      case UP:
        return DOWN;
      case DOWN:
        return UP;
      case LEFT:
        return RIGHT;
      case RIGHT:
        return LEFT;
      default:
        assert false : "Invalid direction";
        return null;
    }
  }
}
