package com.csabapro.sokoban.gui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.csabapro.sokoban.models.Direction;
import com.csabapro.sokoban.models.GameState;
import com.csabapro.sokoban.models.Map;
import com.csabapro.sokoban.models.TileState;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameController {
  private GameState gameState;

  @FXML
  private GridPane grid;

  private List<Pane> panes = new ArrayList<>();

  @FXML
  public void initialize() {
    System.out.println("GameController initialized");
    Platform.runLater(() -> grid.getScene().setOnKeyPressed(this::onKeyPressed));
    startGame();
  }

  void startGame() {
    initializeGameState();
    initializeGrid();
    drawEntities();
  }

  @FXML
  private void onKeyPressed(KeyEvent event) {
    var undoKey = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    var exitKey = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);

    if (undoKey.match(event)) {
      gameState.undoMove();
    } else if (exitKey.match(event)) {
      Platform.exit();
    } else {
      switch (event.getCode()) {
        case W:
        case UP:
          gameState.movePlayer(Direction.UP);
          break;

        case S:
        case DOWN:
          gameState.movePlayer(Direction.DOWN);
          break;

        case A:
        case LEFT:
          gameState.movePlayer(Direction.LEFT);
          break;

        case D:
        case RIGHT:
          gameState.movePlayer(Direction.RIGHT);
          break;

        default:
          break;
      }
    }
    drawEntities();
    if (gameState.isSolved()) {
      gameEnd();
    }
  }

  private void initializeGameState() {
    var res = getClass().getResourceAsStream("/map.json");
    try (InputStreamReader reader = new InputStreamReader(res, "UTF-8")) {
      Map map = Map.fromJson(reader);
      gameState = new GameState(map);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initializeGrid() {
    TileState[][] tiles = gameState.getTiles();
    for (int row = 0; row < tiles.length; row++) {
      for (int col = 0; col < tiles[row].length; col++) {
        Pane tile = new Pane();
        var style = tile.getStyleClass();
        style.add("tile");
        switch (tiles[row][col]) {
          case Wall:
            style.add("wall");
            break;
          case Empty:
            style.add("empty");
            break;
          case Walkable:
            style.add("walkable");
            break;
          default:
            assert false : "Unknown tile state";
            break;
        }
        grid.add(tile, col, row);
        panes.add(tile);
      }
    }
  }

  private void drawEntities() {
    for (var pane : panes) {
      pane.getChildren().clear();
    }

    var tileRowLength = gameState.getTiles()[0].length;

    var pressurePlates = gameState.getPressurePlates();
    for (var pressurePlatePos : pressurePlates) {
      var pressurePlateTile = panes.get(pressurePlatePos.row() * tileRowLength + pressurePlatePos.col());
      pressurePlateTile.getStyleClass().add("pressure-plate");
      pressurePlateTile.getChildren().add(AssetManager.getPressurePlateImage());
    }

    var playerPos = gameState.getPlayerPos();
    var playerTile = panes.get(playerPos.row() * tileRowLength + playerPos.col());
    playerTile.getChildren().add(AssetManager.getPlayerImage());

    var stones = gameState.getStones();
    for (var stonePos : stones) {
      var stoneTile = panes.get(stonePos.row() * tileRowLength + stonePos.col());
      stoneTile.getChildren().add(AssetManager.getStoneImage());
    }
  }

  private void gameEnd() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Congratulations!");
    alert.setHeaderText("You have completed the level!");
    alert.showAndWait();
    Platform.exit();
  }
}
