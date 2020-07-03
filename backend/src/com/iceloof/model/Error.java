package com.iceloof.model;

public class Error {
  private Msg error;

  public Error() {}

  public Error(String code, String description) {
    this.error = new Msg(code, description);
  }

  public Msg getError() {
    return this.error;
  }
}

class Msg {
  private String code;
  private String description;

  public Msg(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return this.code;
  }

  public String getDescription() {
    return this.description;
  }
}