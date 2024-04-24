package com.csabapro.sokoban.models;

import com.google.gson.annotations.SerializedName;

public enum TileState {
  @SerializedName("0")
  Empty,
  @SerializedName("1")
  Wall,
  @SerializedName("2")
  Walkable,
}
