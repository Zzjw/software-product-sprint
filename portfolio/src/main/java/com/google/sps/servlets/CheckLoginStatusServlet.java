package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/check")
public class CheckLoginStatusServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    response.setContentType("application/json;");
    if (userService.isUserLoggedIn()) {
    //   User user = userService.getCurrentUser();
      LoginStatusJson loginStatusJson = new LoginStatusJson(1, userService.createLogoutURL("/"));
      String json = convertToJson(loginStatusJson);
      response.getWriter().println(json);
    } else {
      LoginStatusJson loginStatusJson = new LoginStatusJson(0, userService.createLoginURL("/"));
      String json = convertToJson(loginStatusJson);
      response.getWriter().println(json);
    }
  }

  private String convertToJson(LoginStatusJson loginStatusJson) {
    String json = "{";
    json += "\"ifLoggedIn\": ";
    json += loginStatusJson.getIfLoggedIn();
    json += ", ";
    json += "\"url\": ";
    json += "\""+ loginStatusJson.getHint() + "\"";
    json += "}";
    return json;
  }
}


class LoginStatusJson {
  private final long ifLoggedIn;
  private final String url;

  public LoginStatusJson(long ifLoggedIn, String url) {
    this.ifLoggedIn = ifLoggedIn;
    this.url = url;
  }

  public long getIfLoggedIn() {
    return ifLoggedIn;
  }

  public String getHint() {
    return url;
  }
}


