package com.csabapro.sokoban.models;

public record Position(int col, int row) {
  public Position move(Direction direction) {
    return new Position(col + direction.getCol(), row + direction.getRow());
  }

  public boolean isValid(int width, int height) {
    return col >= 0 && col < width && row >= 0 && row < height;
  }
}
