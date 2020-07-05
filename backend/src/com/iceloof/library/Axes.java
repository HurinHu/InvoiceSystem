package com.iceloof.library;

import java.util.ArrayList;
import java.util.List;

public class Axes {

  private List<DataStructure> axes;

  public Axes() {
    this.axes = new ArrayList<DataStructure>();
  }

  public void add(DataStructure ds) {
    this.axes.add(ds);
  }

  public List<DataStructure> getAxes(){
    return this.axes;
  }
}
