package com.csabapro.sokoban.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AssetManager {
  public static final String PLAYER_IMG_PATH = "img/player.png";
  public static final String STONE_IMG_PATH = "img/stone.png";
  public static final String PRESSURE_PLATE_IMG_PATH = "img/pressure_plate.png";

  public static final Image PLAYER_IMG = new Image(PLAYER_IMG_PATH, 50, 50, false, false);
  public static final Image STONE_IMG = new Image(STONE_IMG_PATH, 50, 50, false, false);
  public static final Image PRESSURE_PLATE_IMG = new Image(PRESSURE_PLATE_IMG_PATH, 50, 50, false, false);

  public static final ImageView getPlayerImage() {
    return new ImageView(PLAYER_IMG);
  } 

  public static final ImageView getStoneImage() {
    return new ImageView(STONE_IMG);
  }

  public static final ImageView getPressurePlateImage() {
    return new ImageView(PRESSURE_PLATE_IMG);
  }
}
