package com.iceloof.library;

import com.iceloof.Setting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Database {

  private Setting setting;
  private Connection connection;
  private PreparedStatement statement;
  private ResultSet resultset;
  private String sql;
  private List<Object> list;
  private boolean flag_select;
  private boolean flag_attr;
  private boolean flag_where;
  private boolean flag_set;
  private boolean flag_val;
  private boolean flag_on;
  private boolean flag_having;
  private boolean flag_update;
  private ArrayList<Type> setData;

  public Database () {
    this.setting = new Setting();
    this.connection = this.connect();
    this.setData = new ArrayList<Type>();
    this.sql = "";
    this.flag_select = false;
    this.flag_attr = false;
    this.flag_where = false;
    this.flag_set = false;
    this.flag_val = false;
    this.flag_on = false;
    this.flag_having = false;
    this.flag_update = false;
  }

  public void reset() {
    this.setting = new Setting();
    this.connection = this.connect();
    this.setData = new ArrayList<Type>();
    this.sql = "";
    this.flag_select = false;
    this.flag_attr = false;
    this.flag_where = false;
    this.flag_set = false;
    this.flag_val = false;
    this.flag_on = false;
    this.flag_having = false;
    this.flag_update = false;
  }

  private Connection connect() {
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      this.connection = DriverManager.getConnection(this.setting.db_url+"/"+this.setting.db, this.setting.db_user, this.setting.db_pwd);
    } catch (ClassNotFoundException e) {
      this.print("Class not Found: " + e.getMessage());
    } catch (SQLException e) {
      this.print(e.getMessage());
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
      Statement statement = connection.createStatement();
      this.resultset = statement.executeQuery(sql);
      return this.resultset;
    } catch (SQLException e) {
      this.print(e.getMessage());
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
      this.print(e.getMessage());
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
        this.print("");
      }
    } catch (SQLException e) {
      this.print(e.getMessage());
    }
  }

  @SuppressWarnings({ "unchecked" })
  public void print(List<Object> list) {
    for(int i = 0; i < list.size(); i ++) {
      for(int j = 0; j < ((List<Object>) list.get(i)).size(); j ++) {
        System.out.printf("%-50s ", ((List<Object>) list.get(i)).get(j));
      }
      this.print("");
    }
  }

  public void print(String str) {
    System.out.println(str);
  }

  public void print(int input) {
    System.out.println(input);
  }

  public void print(float input) {
    System.out.println(input);
  }

  public void print(double input) {
    System.out.println(input);
  }

  private String[] getArray(String attr){
    return attr.replaceAll("\\s","").split(",");
  }

  private String[] getSplit(String attr){
    int index = attr.indexOf("!=")!=-1?attr.indexOf("!="):attr.indexOf(">=")!=-1?attr.indexOf(">="):attr.indexOf("<=")!=-1?attr.indexOf("<="):attr.indexOf("=")!=-1?attr.indexOf("="):attr.indexOf("<>")!=-1?attr.indexOf("<>"):attr.indexOf(">")!=-1?attr.indexOf(">"):attr.indexOf("<");
    String str1 = "";
    String str2 = "";
    if(attr.substring(index+1, index+2).contains("=") || attr.substring(index+1, index+2).contains(">")){
      str1 = attr.substring(0, index+2);
      str2 = attr.substring(index+2);
    } else {
      str1 = attr.substring(0, index+1);
      str2 = attr.substring(index+1);
    }
    return new String[]{str1.trim(), str2.trim()};
  }

  private String checkType(String val) {
    if(val.equalsIgnoreCase("null")){
      return "Null";
    }
    try{
      Integer.parseInt(val);
      return "Int";
    }catch(Exception e1){
      try{
        Double.parseDouble(val);
        return "Double";
      }catch(Exception e3){
        return "String";
      }
    }
  }

  public void insert(String table) {
    this.sql += " INSERT INTO " + table;
    this.flag_update = true;
  }

  public void attr(String attr) {
    if(this.flag_attr) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", `" + attr + "`)";
    } else {
      this.sql += " (`" + attr + "`)";
      this.flag_attr = true;
    }
  }

  public void attr(String func, String attr) {
    if(this.flag_attr) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", " + func + "(`" + attr + "`))";
    } else {
      this.sql += " (" + func + "(`" + attr + "`))";
      this.flag_attr = true;
    }
  }

  public void attr(String func, String arg, String attr) {
    if(this.flag_attr) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", " + func + "(`" + attr + "`," + arg + "))";
    } else {
      this.sql += " (" + func + "(`" + attr + "`," + arg + "))";
      this.flag_attr = true;
    }
  }

  public void attr(String func, int arg, String attr) {
    if(this.flag_attr) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", " + func + "(`" + attr + "`," + arg + "))";
    } else {
      this.sql += " (" + func + "(`" + attr + "`," + arg + "))";
      this.flag_attr = true;
    }
  }

  public void val() {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", ?)";
    } else {
      this.sql += " VALUES (?)";
      this.flag_val = true;
    }
    this.setData.add(new Type("Null", ""));
  }

  public void val(String value) {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", ?)";
    } else {
      this.sql += " VALUES (?)";
      this.flag_val = true;
    }
    this.setData.add(new Type("String", value));
  }

  public void val(String func, String value) {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", " + func + "(?))";
    } else {
      this.sql += " VALUES (" + func + "(?))";
      this.flag_val = true;
    }
    this.setData.add(new Type("String", value));
  }

  public void val(String func, String arg, String value) {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", " + func + "(?," + arg + "))";
    } else {
      this.sql += " VALUES (" + func + "(?," + arg + "))";
      this.flag_val = true;
    }
    this.setData.add(new Type("String", value));
  }

  public void val(String func, int arg, String value) {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", " + func + "(?," + arg + "))";
    } else {
      this.sql += " VALUES (" + func + "(?," + arg + "))";
      this.flag_val = true;
    }
    this.setData.add(new Type("String", value));
  }

  public void val(int value) {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", ?)";
    } else {
      this.sql += " VALUES (?)";
      this.flag_val = true;
    }
    this.setData.add(new Type("Int", Integer.toString(value)));
  }

  public void val(long value) {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", ?)";
    } else {
      this.sql += " VALUES (?)";
      this.flag_val = true;
    }
    this.setData.add(new Type("Long", Long.toString(value)));
  }

  public void val(float value) {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", ?)";
    } else {
      this.sql += " VALUES (?)";
      this.flag_val = true;
    }
    this.setData.add(new Type("Float", Float.toString(value)));
  }

  public void val(double value) {
    if(this.flag_val) {
      this.sql = this.sql.replaceFirst(".$","");
      this.sql += ", ?)";
    } else {
      this.sql += " VALUES (?)";
      this.flag_val = true;
    }
    this.setData.add(new Type("Double", Double.toString(value)));
  }

  public void update(String table) {
    this.sql += " UPDATE " + table;
    this.flag_update = true;
  }

  public void set(String attr, String value) {
    if(this.flag_set) {
      this.sql += ", `" + attr + "`=?";
    } else {
      this.sql += " SET `" + attr + "`=?";
      this.flag_set = true;
    }
    this.setData.add(new Type("String", value));
  }

  public void set(String attr, long value) {
    if(this.flag_set) {
      this.sql += ", `" + attr + "`=?";
    } else {
      this.sql += " SET `" + attr + "`=?";
      this.flag_set = true;
    }
    this.setData.add(new Type("Long", Long.toString(value)));
  }

  public void set(String attr, int value) {
    if(this.flag_set) {
      this.sql += ", `" + attr + "`=?";
    } else {
      this.sql += " SET `" + attr + "`=?";
      this.flag_set = true;
    }
    this.setData.add(new Type("Int", Integer.toString(value)));
  }

  public void set(String attr, float value) {
    if(this.flag_set) {
      this.sql += ", `" + attr + "`=?";
    } else {
      this.sql += " SET `" + attr + "`=?";
      this.flag_set = true;
    }
    this.setData.add(new Type("Float", Float.toString(value)));
  }

  public void set(String attr, double value) {
    if(this.flag_set) {
      this.sql += ", `" + attr + "`=?";
    } else {
      this.sql += " SET `" + attr + "`=?";
      this.flag_set = true;
    }
    this.setData.add(new Type("Double", Double.toString(value)));
  }

  public void delete(String table) {
    this.sql += " DELETE FROM " + table;
    this.flag_update = true;
  }

  public void left_join(String table) {
    this.sql += " LEFT JOIN " + table;
  }

  public void right_join(String table) {
    this.sql += " RIGHT JOIN " + table;
  }

  public void inner_join(String table) {
    this.sql += " INNER JOIN " + table;
  }

  public void full_outer_join(String table) {
    this.sql += " FULL OUTER JOIN " + table;
  }

  public void union(){
    this.sql += " UNION";
  }

  public void union_all(){
    this.sql += " UNION ALL";
  }

  public void select() {
    this.sql += " SELECT * ";
  }

  public void select(String value) {
    if(this.flag_select) {
      this.sql += ", " + value;
    } else {
      this.sql += " SELECT " + value;
      this.flag_select = true;
    }
  }

  public void select(String value, String alias) {
    if(this.flag_select) {
      this.sql += ",  " + value + " AS `" + alias + "`";
    } else {
      this.sql += " SELECT " + value + " AS `" + alias + "`";
      this.flag_select = true;
    }
  }

  public void distinct(String attr) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", DISTINCT(" + select + ")";
    } else {
      this.sql += " SELECT DISTINCT(" + select + ")";
      this.flag_select = true;
    }
  }

  public void distinct(String attr, String alias) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", DISTINCT(" + select + ") AS `" + alias + "`";
    } else {
      this.sql += " SELECT DISTINCT(" + select + ") AS `" + alias + "`";
      this.flag_select = true;
    }
  }

  public void sum(String attr) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", SUM(" + select + ")";
    } else {
      this.sql += " SELECT SUM(" + select + ")";
      this.flag_select = true;
    }
  }

  public void sum(String attr, String alias) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", SUM(" + select + ") AS `" + alias + "`";
    } else {
      this.sql += " SELECT SUM(" + select + ") AS `" + alias + "`";
      this.flag_select = true;
    }
  }

  public void count(String attr) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", COUNT(" + select + ")";
    } else {
      this.sql += " SELECT COUNT(" + select + ")";
      this.flag_select = true;
    }
  }

  public void count(String attr, String alias) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", COUNT(" + select + ") AS `" + alias + "`";
    } else {
      this.sql += " SELECT COUNT(" + select + ") AS `" + alias + "`";
      this.flag_select = true;
    }
  }

  public void avg(String attr) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", AVG(" + select + ")";
    } else {
      this.sql += " SELECT AVG(" + select + ")";
      this.flag_select = true;
    }
  }

  public void avg(String attr, String alias) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", AVG(" + select + ") AS `" + alias + "`";
    } else {
      this.sql += " SELECT AVG(" + select + ") AS `" + alias + "`";
      this.flag_select = true;
    }
  }

  public void max(String attr) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";;
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", MAX(" + select + ")";
    } else {
      this.sql += " SELECT MAX(" + select + ")";
      this.flag_select = true;
    }
  }

  public void max(String attr, String alias) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", MAX(" + select + ") AS `" + alias + "`";
    } else {
      this.sql += " SELECT MAX(" + select + ") AS `" + alias + "`";
      this.flag_select = true;
    }
  }

  public void min(String attr) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", MIN(" + select + ")";
    } else {
      this.sql += " SELECT MIN(" + select + ")";
      this.flag_select = true;
    }
  }

  public void min(String attr, String alias) {
    String select = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      select += item + ",";
    }
    if(select.contains(",")){
      select = select.replaceFirst(".$","");
    }
    if(this.flag_select) {
      this.sql += ", MIN(" + select + ") AS `" + alias + "`";
    } else {
      this.sql += " SELECT MIN(" + select + ") AS `" + alias + "`";
      this.flag_select = true;
    }
  }

  public void from(String table) {
    this.sql += " FROM "+table;
  }

  public void on(String attr, String value) {
    if(this.flag_on) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " ON " + attr + "=?";
      this.flag_on = true;
    }
    this.setData.add(new Type("String", value));
  }

  public void on(String attr, long value) {
    if(this.flag_on) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " ON " + attr + "=?";
      this.flag_on = true;
    }
    this.setData.add(new Type("Long", Long.toString(value)));
  }

  public void on(String attr, int value) {
    if(this.flag_on) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " ON " + attr + "=?";
      this.flag_on = true;
    }
    this.setData.add(new Type("Int", Integer.toString(value)));
  }

  public void on(String attr, float value) {
    if(this.flag_on) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " ON " + attr + "=?";
      this.flag_on = true;
    }
    this.setData.add(new Type("Float", Float.toString(value)));
  }

  public void on(String attr, double value) {
    if(this.flag_on) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " ON " + attr + "=?";
      this.flag_on = true;
    }
    this.setData.add(new Type("Double", Double.toString(value)));
  }

  public void on(String str) {
    if(this.flag_on) {
      this.sql += " AND " + str;
    } else {
      this.sql += " ON " + str;
      this.flag_on = true;
    }
  }

  public void where(String attr, String value) {
    if(this.flag_where) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " WHERE " + attr + "=?";
      this.flag_where = true;
    }
    this.setData.add(new Type("String", value));
  }

  public void where(String attr, long value) {
    if(this.flag_where) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " WHERE " + attr + "=?";
      this.flag_where = true;
    }
    this.setData.add(new Type("Long", Long.toString(value)));
  }

  public void where(String attr, int value) {
    if(this.flag_where) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " WHERE " + attr + "=?";
      this.flag_where = true;
    }
    this.setData.add(new Type("Int", Integer.toString(value)));
  }

  public void where(String attr, float value) {
    if(this.flag_where) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " WHERE " + attr + "=?";
      this.flag_where = true;
    }
    this.setData.add(new Type("Float", Float.toString(value)));
  }

  public void where(String attr, double value) {
    if(this.flag_where) {
      this.sql += " AND " + attr + "=?";
    } else {
      this.sql += " WHERE " + attr + "=?";
      this.flag_where = true;
    }
    this.setData.add(new Type("Double", Double.toString(value)));
  }

  public void where(String str) {
    String[] split = this.getSplit(str);
    if(this.flag_where) {
      this.sql += " AND " + split[0] + "?";
    } else {
      this.sql += " WHERE " + split[0] + "?";
      this.flag_where = true;
    }
    this.setData.add(new Type(this.checkType(split[1]), split[1]));
  }

  public void where_or(String str1, String str2) {
    String[] split1 = this.getSplit(str1);
    String[] split2 = this.getSplit(str2);
    if(this.flag_where) {
      this.sql += " AND (" + split1[0] + "? OR " + split2[0] + "?)";
    } else {
      this.sql += " WHERE (" + split1[0] + "? OR " + split2[0] + "?)";
      this.flag_where = true;
    }
    this.setData.add(new Type(this.checkType(split1[1]), split1[1]));
    this.setData.add(new Type(this.checkType(split2[1]), split2[1]));
  }

  public void where_between(String attr, String str1, String str2) {
    if(this.flag_where) {
      this.sql += " AND (" + attr + " BETWEEN ? AND ?)";
    } else {
      this.sql += " WHERE (" + attr + " BETWEEN ? AND ?)";
      this.flag_where = true;
    }
    this.setData.add(new Type("String", str1));
    this.setData.add(new Type("String", str2));
  }

  public void like(String attr, String value) {
    if(this.flag_where) {
      this.sql += " AND " + attr + " LIKE '%?%'";
    } else {
      this.sql += " WHERE " + attr + " LIKE '%?%'";
      this.flag_where = true;
    }
    this.setData.add(new Type("String", value));
  }

  public void groupby(String attr) {
    String group = "";
    String[] attrArr = this.getArray(attr);
    for(String item: attrArr) {
      group += "?,";
      this.setData.add(new Type("String", item));
    }
    if(group.contains(",")){
      group = group.replaceFirst(".$","");
    }
    this.sql += " GROUP BY " + group + " ";
  }

  public void orderby(String orderby, String seq) {
    String order = "";
    String[] attrArr = this.getArray(orderby);
    for(String item: attrArr) {
      order += "?,";
      this.setData.add(new Type("String", item));
    }
    if(order.contains(",")){
      order = order.replaceFirst(".$","");
    }
    String direct = "";
    if(seq.equalsIgnoreCase("asc") || seq.equalsIgnoreCase("desc")){
      direct = seq;
    }
    this.sql += " ORDER BY " + order + " " + direct +" ";
  }

  public void limit(int start, int length) {
    this.sql += " LIMIT " + start + ", " + length;
  }

  public void having(String str) {
    String[] split = this.getSplit(str);
    if(this.flag_having) {
      this.sql += " AND " + split[0] + "?";
    } else {
      this.sql += " HAVING " + split[0] + "?";
      this.flag_having = true;
    }
    this.setData.add(new Type(this.checkType(split[1]), split[1]));
  }

  public Object get() {
    if(this.connection == null || this.isClosed()) {
      this.connect();
    }
    try {
      this.statement = connection.prepareStatement(this.sql);
      int i = 1;
      for(Type item: this.setData){
        switch(item.getType()){
          case "Null":
            this.statement.setNull(i++, Types.INTEGER);
            break;
          case "String":
            this.statement.setString(i++, item.getValue());
            break;
          case "Long":
            this.statement.setLong(i++, Long.parseLong(item.getValue()));
            break;
          case "Int":
            this.statement.setInt(i++, Integer.parseInt(item.getValue()));
            break;
          case "Float":
            this.statement.setFloat(i++, Float.parseFloat(item.getValue()));
            break;
          case "Double":
            this.statement.setDouble(i++, Double.parseDouble(item.getValue()));
            break;
          default:
            this.statement.setString(i++, item.getValue());
            break;
        }
      }
      this.list = new ArrayList<Object>();
      this.resultset = this.statement.executeQuery();
      List<Object> rowdata = null;
      ResultSetMetaData rsmd = this.resultset.getMetaData();
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
          for(i = 1; i <= column; i ++) {
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
      return e.getMessage();
    }
  }

  public Object update() {
    if(this.connection == null || this.isClosed()) {
      this.connect();
    }
    try {
      this.statement = connection.prepareStatement(this.sql);
      int i = 1;
      for(Type item: this.setData){
        switch(item.getType()){
          case "Null":
            this.statement.setNull(i++, Types.INTEGER);
            break;
          case "String":
            this.statement.setString(i++, item.getValue());
            break;
          case "Long":
            this.statement.setLong(i++, Long.parseLong(item.getValue()));
            break;
          case "Int":
            this.statement.setInt(i++, Integer.parseInt(item.getValue()));
            break;
          case "Float":
            this.statement.setFloat(i++, Float.parseFloat(item.getValue()));
            break;
          case "Double":
            this.statement.setDouble(i++, Double.parseDouble(item.getValue()));
            break;
          default:
            this.statement.setString(i++, item.getValue());
            break;
        }
      }
      this.list = new ArrayList<Object>();
      this.statement.executeUpdate();
      return this.list;
    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public List<Object> getResult() {
    return this.list;
  }

  public void printSql() {
    this.print(this.sql);
  }

  public void printParams() {
    String params = "";
    for(Type item: this.setData){
      params += item.getValue() + ",";
    }
    this.print(params.replaceFirst(".$",""));
  }

}

class Type {
  private String type;
  private String value;

  public Type(String type, String value) {
    this.type = type;
    this.value = value;
  }

  public String getType() {
    return this.type;
  }

  public String getValue() {
    return this.value;
  }
}