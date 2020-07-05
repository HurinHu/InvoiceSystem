package com.iceloof.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iceloof.model.Error;

@RestController
@SuppressWarnings("deprecation")
public class CustomErrorController implements ErrorController {

  private Error error;

  @Override
  public String getErrorPath() {
    return "/error";
  }

  @RequestMapping(value="/error")
  public Error renderError(HttpServletRequest httpRequest, HttpServletResponse response) {
    int httpErrorCode = getErrorCode(httpRequest);

    switch (httpErrorCode) {
      case 400: {
        this.error = new Error("BAD_REQUEST", "400 Bad Request");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        break;
      }
      case 401: {
        this.error = new Error("AUTH_REQUIRED", "User has not been authenticated");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        break;
      }
      case 403: {
        this.error = new Error("NO_SUBSCR", "You do not have a current subscription to access this content");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        break;
      }
      case 404: {
        this.error = new Error("NOT_FOUND", "The requested URL was not found on this server");
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        break;
      }
      case 409: {
        this.error = new Error("CONFLICT", "The request could not be completed due to a conflict with the current state of the resource");
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        break;
      }
      case 500: {
        this.error = new Error("INTERNAL_SERVER_ERROR", "500 Internal Server Error");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        break;
      }
    }
    return error;
  }

  private int getErrorCode(HttpServletRequest httpRequest) {
    return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
  }

}
