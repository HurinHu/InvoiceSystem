package com.iceloof.library;

import java.util.ArrayList;
import java.util.List;

public class Result {

  private String status = "404 Not Found";
  private String msg = "";
  private List<DataStructure> axes = new ArrayList<DataStructure>();
  private Object data = new ArrayList<Object>();

  public Result() {

  }

  public Result(String status) {
      this.status = status;
  }

  public Result(String status, Object data) {
      this.status = status;
      this.data = data;
  }

  public Result(String status, List<DataStructure> axes, Object data) {
      this.status = status;
      this.axes = axes;
      this.data = data;
  }

  public Result(String status, String msg) {
      this.status = status;
      this.msg = msg;
  }

  public String getStatus() {
      return this.status;
  }

  public List<DataStructure> getAxes() {
      return this.axes;
  }

  public Object getData() {
      return this.data;
  }

  public String getMsg() {
    return this.msg;
  }
}
