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
    Gson gson = new Gson();
    response.setContentType("application/json;");
    if (userService.isUserLoggedIn()) {
      LoginStatusJson loginStatusJson = new LoginStatusJson(1, "Has logged in!");
      String json = convertToJson(loginStatusJson);
      response.getWriter().println(json);
    } else {
      LoginStatusJson loginStatusJson = new LoginStatusJson(0, "Please log in first!");
      String json = convertToJson(loginStatusJson);
      response.getWriter().println(json);
    }
  }

  private String convertToJson(LoginStatusJson loginStatusJson) {
    String json = "{";
    json += "\"ifLoggedIn\": ";
    json += loginStatusJson.getIfLoggedIn();
    json += ", ";
    json += "\"hint\": ";
    json += "\""+ loginStatusJson.getHint() + "\"";
    json += "}";
    return json;
  }
}


class LoginStatusJson {
  private final long ifLoggedIn;
  private final String hint;

  public LoginStatusJson(long ifLoggedIn, String hint) {
    this.ifLoggedIn = ifLoggedIn;
    this.hint = hint;
  }

  public long getIfLoggedIn() {
    return ifLoggedIn;
  }

  public String getHint() {
    return hint;
  }
}


