package com.csabapro.sokoban.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

record Move(Direction playerMove, Position stoneMoved) {}

public class GameState {
  private TileState[][] tiles;
  private Position playerPos;
  private Set<Position> stones;
  private Set<Position> pressurePlates;
  private List<Move> moves = new ArrayList<>();

  public GameState(Map map) {
    this.tiles = map.tiles();
    this.playerPos = map.playerPos();
    this.stones = map.stones();
    this.pressurePlates = map.pressurePlates();
  }

  public TileState[][] getTiles() {
    return tiles;
  }

  public Position getPlayerPos() {
    return playerPos;
  }

  public Set<Position> getStones() {
    return stones;
  }

  public Set<Position> getPressurePlates() {
    return pressurePlates;
  }

  public boolean isMoveValid(Direction direction) {
    Position newPosition = playerPos.move(direction);
    if (!newPosition.isValid(tiles[0].length, tiles.length) || tiles[newPosition.row()][newPosition.col()] != TileState.Walkable) {
      return false;
    }
    if (stones.contains(newPosition)) {
      Position newStonePosition = newPosition.move(direction);
      if (!newStonePosition.isValid(tiles[0].length, tiles.length) || tiles[newStonePosition.row()][newStonePosition.col()] != TileState.Walkable || stones.contains(newStonePosition)) {
        return false;
      }
    }
    return true;
  }

  public void movePlayer(Direction direction) {
    Position newPlayerPos = playerPos.move(direction);
    if (!newPlayerPos.isValid(tiles[0].length, tiles.length) || tiles[newPlayerPos.row()][newPlayerPos.col()] != TileState.Walkable) {
      return;
    }
    Position stoneMoved = null;
    if (stones.contains(newPlayerPos)) {
      Position newStonePos = newPlayerPos.move(direction);
      if (!newStonePos.isValid(tiles[0].length, tiles.length) || tiles[newStonePos.row()][newStonePos.col()] != TileState.Walkable || stones.contains(newStonePos)) {
        return;
      }
      stones.remove(newPlayerPos);
      stones.add(newStonePos);
      stoneMoved = newPlayerPos;
    }
    playerPos = newPlayerPos;
    moves.add(new Move(direction, stoneMoved));
  }

  public void undoMove() {
    if (moves.isEmpty()) {
      return;
    }
    Move lastMove = moves.remove(moves.size() - 1);
    Position newPlayerPos = playerPos.move(lastMove.playerMove().opposite());
    if (lastMove.stoneMoved() != null) {
      Position oldStonePos = lastMove.stoneMoved();
      stones.remove(oldStonePos.move(lastMove.playerMove()));
      stones.add(oldStonePos);
    }
    playerPos = newPlayerPos;
  }

  public boolean isSolved() {
    return pressurePlates.containsAll(stones);
  }
}