package com.iceloof.library;

import com.iceloof.Setting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Database {

  private Setting setting;
  private Connection connection;
  private Statement statement;
  private ResultSet resultset;
  private String sql;
  private List<Object> list;
  private boolean flag;

  public Database () {
    this.setting = new Setting();
    this.connection = this.connect();
  }

  private Connection connect() {
    try{
      Class.forName("com.mysql.jdbc.Driver");
      this.connection = DriverManager.getConnection(this.setting.db_url+"/"+this.setting.db, this.setting.db_user, this.setting.db_pwd);
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  private boolean isClosed() {
    try {
      if(this.connection.isClosed()) {
          return true;
      }else {
          return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return true;
    }
  }

  public void close() {
    if(this.connection != null) {
      try { this.resultset.close(); } catch (Exception e) {  }
      try { this.statement.close(); } catch (Exception e) {  }
      try { this.connection.close(); } catch (Exception e) {  }
    }
  }

  public ResultSet query(String sql) {
    if(this.connection == null) {
      this.connect();
    }
    try {
      this.statement = connection.createStatement();
      this.resultset = statement.executeQuery(sql);
      return this.resultset;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public void setSQL(String sql) {
    this.sql = sql;
  }

  public String getSQL() {
    return this.sql;
  }

  public List<Object> getResult(ResultSet resultset) {
    try {
      this.list = new ArrayList<Object>();
      List<String> rowdata = null;
      ResultSetMetaData rsmd = resultset.getMetaData();
      int column = rsmd.getColumnCount();
      while(resultset.next()) {
        rowdata = new ArrayList<String>();
        for(int i = 1; i <= column; i ++) {
          rowdata.add(resultset.getString(i));
        }
        this.list.add(rowdata);
      }
      return this.list;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public void print(ResultSet resultset) {
    try {
      ResultSetMetaData rsmd = resultset.getMetaData();
      int column = rsmd.getColumnCount();
      while(resultset.next()) {
        for(int i = 1; i <= column; i ++) {
          System.out.printf("%-50s ", resultset.getString(i));
        }
        System.out.println();
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @SuppressWarnings({ "unchecked" })
  public void print(List<Object> list) {
    for(int i = 0; i < list.size(); i ++) {
      for(int j = 0; j < ((List<Object>) list.get(i)).size(); j ++) {
        System.out.printf("%-50s ", ((List<Object>) list.get(i)).get(j));
      }
      System.out.println();
    }
  }

  public void print(String str) {
    System.out.println(str);
  }

  public void print(int input) {
    System.out.println(input);
  }

  public void select() {
    this.sql = "SELECT * ";
    this.flag = false;
  }

  public void select(String attr) {
    this.sql = "SELECT " + attr + " ";
    this.flag = false;
  }

  public void distinct(String attr) {
    this.sql = "SELECT DISTINCT(" + attr + ") ";
    this.flag = false;
  }

  public void sum(String attr) {
    this.sql = "SELECT SUM(" + attr + ") ";
    this.flag = false;
  }

  public void count(String attr) {
    this.sql = "SELECT COUNT(" + attr + ") ";
    this.flag = false;
  }

  public void avg(String attr) {
    this.sql = "SELECT AVG(" + attr + ") ";
    this.flag = false;
  }

  public void max(String attr) {
    this.sql = "SELECT MAX(" + attr + ") ";
    this.flag = false;
  }

  public void min(String attr) {
    this.sql = "SELECT MIN(" + attr + ") ";
    this.flag = false;
  }

  public void from(String table) {
    this.sql += "FROM "+table + " ";
  }

  public void where(String attr, String value) {
    if(this.flag) {
      this.sql += "AND " + attr + "='" + value + "' ";
    } else {
      this.sql += "WHERE " + attr + "='" + value + "' ";
      this.flag = true;
    }
  }

  public void where(String attr, int value) {
    if(this.flag) {
      this.sql += "AND " + attr + "=" + value + " ";
    } else {
      this.sql += "WHERE " + attr + "=" + value + " ";
      this.flag = true;
    }
  }

  public void where(String str) {
    if(this.flag) {
      this.sql += "AND " + str + " ";
    } else {
      this.sql += "WHERE " + str + " ";
      this.flag = true;
    }
  }

  public void where_or(String str1, String str2) {
    if(this.flag) {
      this.sql += "AND (" + str1 + " OR " +str2 + ") ";
    } else {
      this.sql += "WHERE (" + str1 + " OR " +str2 + ") ";
      this.flag = true;
    }
  }

  public void where_between(String attr, String str1, String str2) {
    if(this.flag) {
      this.sql += "AND (" + attr + " BETWEEN " + str1 + " AND " +str2 + ") ";
    } else {
      this.sql += "WHERE (" + attr + " BETWEEN " + str1 + " AND " +str2 + ") ";
      this.flag = true;
    }
  }

  public void like(String attr, String value) {
    if(this.flag) {
      this.sql += "AND " + attr + " LIKE '%" + value + "%' ";
    } else {
      this.sql += "WHERE " + attr + " LIKE '%" + value + "%' ";
      this.flag = true;
    }
  }

  public void groupby(String attr) {
    this.sql += "GROUP BY " + attr + " ";
  }

  public void orderby(String orderby, String seq) {
    this.sql += "ORDER BY " + orderby + " " + seq +" ";
  }

  public void limit(int start, int length) {
    this.sql += "";
  }

  public List<Object> get() {
    if(this.connection == null || this.isClosed()) {
      this.connect();
    }
    try {
      this.statement = connection.createStatement();
      this.resultset = statement.executeQuery(this.sql);
      this.list = new ArrayList<Object>();
      List<Object> rowdata = null;
      ResultSetMetaData rsmd = resultset.getMetaData();
      int column = rsmd.getColumnCount();
      int type;
      while(resultset.next()) {
        if(column == 1) {
          type = rsmd.getColumnType(1);
          if(type == Types.FLOAT || type == Types.DOUBLE || type == Types.INTEGER || type == Types.NUMERIC) {
            this.list.add(resultset.getDouble(1));
          }else{
            this.list.add(resultset.getString(1));
          }
        }else {
          rowdata = new ArrayList<Object>();
          for(int i = 1; i <= column; i ++) {
            type = rsmd.getColumnType(i);
            if(type == Types.FLOAT || type == Types.DOUBLE || type == Types.INTEGER || type == Types.NUMERIC) {
              rowdata.add(resultset.getDouble(i));
            }else{
              rowdata.add(resultset.getString(i));
            }
          }
          this.list.add(rowdata);
        }
      }
      return this.list;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public List<Object> getResult() {
    return this.list;
  }

  public void printSql() {
    this.print(this.sql);
  }

}