package com.iceloof.model;

public class User {

  private int id;
  private String name;
  private String email;

  public User(String name) {
    this.name = name;
  }

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public User(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

}
