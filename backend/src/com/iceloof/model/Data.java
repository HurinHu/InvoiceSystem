package com.iceloof.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

  public Data(Database db) {
    this.db = db;
    this.axes = new Axes();
    this.setting = new Setting();
    this.tools = new Tools();
  }

  public Object getUser(HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.select(this.setting.user_id);
    this.db.select(this.setting.user_name);
    this.db.select(this.setting.user_email);
    this.db.from(this.setting.table_user);
    this.axes.add(new DataStructure("Integer", "id"));
    this.axes.add(new DataStructure("String", "name"));
    this.axes.add(new DataStructure("String", "email"));
    this.result = this.db.get();
    this.db.close();
    if(this.result instanceof String) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return new Error("BAD_REQUEST", "400 Bad Request");
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", this.axes.getAxes(), this.result);
    }
  }

  public Object createUser(String name, String email, String password, String role, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.insert(this.setting.table_user);
    this.db.attr(this.setting.user_id);
    this.db.attr(this.setting.user_name);
    this.db.attr(this.setting.user_email);
    this.db.attr(this.setting.user_password);
    this.db.attr(this.setting.user_role);
    this.db.attr(this.setting.created_time);
    this.db.val();
    this.db.val(name);
    this.db.val(email);
    this.db.val(this.tools.hash(password, "iceloof"));
    if(role == null) {
      this.db.val("USER");
    } else {
      this.db.val(role);
    }
    this.db.val(this.tools.currentTimestamp());
    this.result = this.db.update();
    this.db.close();
    if(this.result instanceof String) {
      if(this.result.toString().contains("Duplicate")) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist");
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request");
      }
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", "User created");
    }
  }

  public Object updateUserNameByEmail(String name, String email, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.update(this.setting.table_user);
    this.db.set(this.setting.user_name, name);
    this.db.where(this.setting.user_email, email);
    this.result = this.db.update();
    this.db.close();
    if(this.result instanceof String) {
      if(this.result.toString().contains("Duplicate")) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist");
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request");
      }
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", "User updated");
    }
  }

  public Object updateUserNameById(String name, int id, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.update(this.setting.table_user);
    this.db.set(this.setting.user_name, name);
    this.db.where(this.setting.user_id, id);
    this.result = this.db.update();
    this.db.close();
    if(this.result instanceof String) {
      if(this.result.toString().contains("Duplicate")) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist");
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request");
      }
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", "User updated");
    }
  }

  public Object updateUserPasswordByEmail(String password, String email, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.update(this.setting.table_user);
    this.db.set(this.setting.user_password, this.tools.hash(password, "iceloof"));
    this.db.where(this.setting.user_email, email);
    this.result = this.db.update();
    this.db.close();
    if(this.result instanceof String) {
      if(this.result.toString().contains("Duplicate")) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist");
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request");
      }
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", "User password updated");
    }
  }

  public Object updateUserPasswordById(String password, int id, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.update(this.setting.table_user);
    this.db.set(this.setting.user_password, this.tools.hash(password, "iceloof"));
    this.db.where(this.setting.user_id, id);
    this.result = this.db.update();
    this.db.close();
    if(this.result instanceof String) {
      if(this.result.toString().contains("Duplicate")) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist");
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request");
      }
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", "User password updated");
    }
  }

  public Object deleteUserByEmail(String email, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.delete(this.setting.table_user);
    this.db.where(this.setting.user_email, email);
    this.result = this.db.update();
    this.db.close();
    if(this.result instanceof String) {
      if(this.result.toString().contains("Duplicate")) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist");
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request"+this.result);
      }
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", "User deleted");
    }
  }

  public Object deleteUserByName(String name, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.delete(this.setting.table_user);
    this.db.where(this.setting.user_name, name);
    this.result = this.db.update();
    this.db.close();
    if(this.result instanceof String) {
      if(this.result.toString().contains("Duplicate")) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist");
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request");
      }
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", "User deleted");
    }
  }

  public Object deleteUserById(int id, HttpServletResponse res) {
    this.db.reset();
    this.axes = new Axes();
    this.db.delete(this.setting.table_user);
    this.db.where(this.setting.user_id, id);
    this.result = this.db.update();
    this.db.close();
    if(this.result instanceof String) {
      if(this.result.toString().contains("Duplicate")) {
        res.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Error("CONFLICT", "User name or email is exist"+this.result);
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new Error("BAD_REQUEST", "400 Bad Request");
      }
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      return new Result("200 OK", "User deleted");
    }
  }

  @SuppressWarnings({ "unchecked" })
  public Object loginByName(String name, String password, HttpServletResponse res, HttpSession session) {
    this.db.reset();
    this.axes = new Axes();
    this.db.select(this.setting.user_id);
    this.db.select(this.setting.user_name);
    this.db.select(this.setting.user_email);
    this.db.select(this.setting.user_role);
    this.db.from(this.setting.table_user);
    this.db.where(this.setting.user_name, name);
    this.db.where(this.setting.user_password, this.tools.hash(password, "iceloof"));
    this.axes.add(new DataStructure("Integer", "id"));
    this.axes.add(new DataStructure("String", "name"));
    this.axes.add(new DataStructure("String", "email"));
    this.axes.add(new DataStructure("String", "role"));
    this.result = this.db.get();
    this.db.close();
    if(this.result instanceof String) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return new Error("BAD_REQUEST", "400 Bad Request");
    } else {
      if(((List<Object>)this.result).size() == 1){
        List<Object> list = ((List<List<Object>>)this.result).get(0);
        res.setStatus(HttpServletResponse.SC_OK);
        int id = (new Double(list.get(0).toString())).intValue();
        String email = list.get(2).toString();
        String role = list.get(3).toString();
        session.setAttribute("UserId", id);
        session.setAttribute("UserName", name);
        session.setAttribute("UserEmail", email);
        session.setAttribute("UserRole", role);
        return new User(id, name, email, role);
      } else {
        session.invalidate();
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return new Error("AUTH_FAILED", "Either username or password was incorrect");
      }
    }
  }

  @SuppressWarnings({ "unchecked" })
  public Object loginByEmail(String email, String password, HttpServletResponse res, HttpSession session) {
    this.db.reset();
    this.axes = new Axes();
    this.db.select(this.setting.user_id);
    this.db.select(this.setting.user_name);
    this.db.select(this.setting.user_email);
    this.db.select(this.setting.user_role);
    this.db.from(this.setting.table_user);
    this.db.where(this.setting.user_email, email);
    this.db.where(this.setting.user_password, this.tools.hash(password, "iceloof"));
    this.axes.add(new DataStructure("Integer", "id"));
    this.axes.add(new DataStructure("String", "name"));
    this.axes.add(new DataStructure("String", "email"));
    this.axes.add(new DataStructure("String", "role"));
    this.result = this.db.get();
    this.db.close();
    if(this.result instanceof String) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return new Error("BAD_REQUEST", "400 Bad Request");
    } else {
      if(((List<Object>)this.result).size() == 1){
        List<Object> list = ((List<List<Object>>)this.result).get(0);
        res.setStatus(HttpServletResponse.SC_OK);
        int id = (new Double(list.get(0).toString())).intValue();
        String user = list.get(1).toString();
        String role = list.get(3).toString();
        session.setAttribute("UserId", id);
        session.setAttribute("UserName", user);
        session.setAttribute("UserEmail", email);
        session.setAttribute("UserRole", role);
        return new User(id, user, email, role);
      } else {
        session.invalidate();
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return new Error("AUTH_FAILED", "Either email or password was incorrect");
      }
    }
  }

  public Object bad_request(HttpServletResponse res) {
    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    return new Error("BAD_REQUEST", "400 Bad Request");
  }

  public Object logout(HttpServletResponse res, HttpSession session) {
    session.invalidate();
    res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    res.setHeader("Pragma", "no-cache");
    res.setDateHeader("Expires", 0);
    res.setStatus(HttpServletResponse.SC_OK);
    return new Result("200 OK", "User logout");
  }

}