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

  @RequestMapping(value="/test", method=RequestMethod.GET, produces = "application/json")
  public String getRegion(HttpSession session, HttpServletRequest request, HttpServletResponse response){
    return "test";
  }

}
