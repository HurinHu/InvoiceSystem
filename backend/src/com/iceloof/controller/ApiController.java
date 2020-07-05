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
  public Object getUser(@RequestParam(value="name", required=true) String name, @RequestParam(value="email", required=true) String email, @RequestParam(value="password", required=true) String password, HttpServletResponse response){
    return data.createUser(name, email, password, response);
  }

}
