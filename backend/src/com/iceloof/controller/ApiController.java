package com.iceloof.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.iceloof.model.*;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ApiController {

  private Data data = new Data();

  @RequestMapping(value="/userlist", method=RequestMethod.GET, produces = "application/json")
  public Object getUser(HttpServletResponse response){
    return data.getUser(response);
  }

  @RequestMapping(value="/user", method=RequestMethod.POST, produces = "application/json")
  public Object createUser(@RequestParam(value="name", required=true) String name, @RequestParam(value="email", required=true) String email, @RequestParam(value="password", required=true) String password, HttpServletResponse response){
    return data.createUser(name, email, password, response);
  }

  @RequestMapping(value="/user", method=RequestMethod.PUT, produces = "application/json")
  public Object updateUser(@RequestParam(value="id", required=false) Integer id, @RequestParam(value="name", required=false) String name, @RequestParam(value="email", required=false) String email,  @RequestParam(value="password", required=false) String password, HttpServletResponse response){
    try{
      if(name != null) {
        if(id != null) {
          return data.updateUserNameById(name, id, response);
        } else if(email != null) {
          return data.updateUserNameByEmail(name, email, response);
        }
      } else if(password != null) {
        if(id != null) {
          return data.updateUserPasswordById(name, id, response);
        } else if(email != null) {
          return data.updateUserPasswordByEmail(name, email, response);
        }
      }
    } catch(Exception e) {
      return data.bad_request(response);
    }
    return data.bad_request(response);
  }

  @RequestMapping(value="/user", method=RequestMethod.DELETE, produces = "application/json")
  public Object deleteUser(@RequestParam(value="id", required=false) Integer id, @RequestParam(value="name", required=false) String name, @RequestParam(value="email", required=false) String email, HttpServletResponse response){
    try{
      if(id != null) {
        return data.deleteUserById(id, response);
      } else if(name != null) {
        return data.deleteUserByName(name, response);
      } else if(email != null) {
        return data.deleteUserByEmail(email, response);
      }
    } catch(Exception e) {
      return data.bad_request(response);
    }
    return data.bad_request(response);
  }

}
