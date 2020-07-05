package com.iceloof.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import com.iceloof.library.*;
import com.iceloof.Setting;

public class Data {
  private Database db;
  private Axes axes;
  private Object result;
  private Setting setting;
  private Tools tools;

  public Data() {
    this.db = new Database();
    this.axes = new Axes();
    this.setting = new Setting();
    this.tools = new Tools();
  }

  public Object getUser(HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.select("id");
    this.db.select("name");
    this.db.select("email");
    this.db.from(this.setting.table_user);
    this.axes.add(new DataStructure("Integer", "id"));
    this.axes.add(new DataStructure("String", "name"));
    this.axes.add(new DataStructure("String", "email"));
    this.result = this.db.get();
    this.db.close();
    if(this.result instanceof String) {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request");
    }else {
        res.setStatus(HttpServletResponse.SC_OK);
        return new Result("200 OK", this.axes.getAxes(), this.result);
    }
  }

  public Object createUser(String name, String email, String password, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.insert(this.setting.table_user);
    this.db.attr("id");
    this.db.attr("name");
    this.db.attr("email");
    this.db.attr("password");
    this.db.attr("created_time");
    this.db.val();
    this.db.val(name);
    this.db.val(email);
    this.db.val(this.tools.hash(password, "iceloof"));
    this.db.val(this.tools.currentTimestamp());
    this.result = this.db.get();
    this.db.close();
    if(this.result instanceof String) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist");
    }else {
        res.setStatus(HttpServletResponse.SC_OK);
        return new Result("200 OK");
    }
  }
}