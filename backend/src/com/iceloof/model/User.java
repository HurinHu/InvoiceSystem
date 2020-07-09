package com.iceloof.model;

import javax.servlet.http.HttpSession;

public class User {

  private int id;
  private String name;
  private String email;
  private String role;

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

  public User(int id, String name, String email, String role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.role = role;
  }

  public User(HttpSession session) {
    this.id = (int) session.getAttribute("UserId");
    this.name = session.getAttribute("UserName").toString();
    this.email = session.getAttribute("UserEmail").toString();
    this.role = session.getAttribute("UserRole").toString();
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

  public String getRole() {
    return this.role;
  }

  public String toSring() {
    return "{\"id\":"+this.id+", \"name\":"+this.name+", \"email\":"+this.email+", \"role\":"+this.role+"}";
  }

}
