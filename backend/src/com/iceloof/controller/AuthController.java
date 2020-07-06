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
public class AuthController {

  private Data data = new Data();

  @RequestMapping(value="/auth", method=RequestMethod.POST, produces = "application/json")
  public Object auth(@RequestParam(value="name", required=false) String name, @RequestParam(value="email", required=false) String email, @RequestParam(value="password", required=true) String password, HttpServletResponse response){
    if(name != null) {
      return data.loginByName(name, password, response);
    } else if(email != null) {
      return data.loginByEmail(email, password, response);
    } else {
      return data.bad_request(response);
    }
  }

}
