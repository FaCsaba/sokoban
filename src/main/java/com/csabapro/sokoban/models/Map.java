package com.csabapro.sokoban.models;

import java.io.Reader;
import java.util.Set;

import com.google.gson.Gson;

public record Map(TileState[][] tiles, Position playerPos, Set<Position> stones, Set<Position> pressurePlates) {
  public static Map fromJson(Reader json) {
    Gson gson = new Gson();
    return gson.fromJson(json, Map.class);
  }
}
