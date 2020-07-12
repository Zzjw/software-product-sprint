// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.sps.data.CommentData;
import com.google.sps.data.CommentValue;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;



/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private CommentData commentData = new CommentData();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // response.setContentType("application/json");
    // String json = new Gson().toJson(commentData);
    // response.getWriter().println(json);
    // System.err.println("Json: " + json);

    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<CommentValue> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
    //   long id = entity.getKey().getId();
    //   String title = (String) entity.getProperty("title");
    //   long timestamp = (long) entity.getProperty("timestamp");
      Date sendTime = (Date) entity.getProperty("date");
      String name = (String) entity.getProperty("name");
      String comment = (String) entity.getProperty("comment");

      CommentValue commentValue = new CommentValue(sendTime, name, comment);
      comments.add(commentValue);
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    Date sendTime = new Date();
    String name = getParameter(request, "name", "");
    String comment = getParameter(request, "comment", "Hello!");

    System.err.println("name: " + name);
    System.err.println("comment" + comment);

    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("name", name);
    commentEntity.setProperty("comment", comment);
    commentEntity.setProperty("date", sendTime);
    commentEntity.setProperty("timestamp", System.currentTimeMillis());

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    response.sendRedirect("/index.html");
  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  private String convertToJsonUsingGson(CommentData commentData) {
    Gson gson = new Gson();
    String json = gson.toJson(commentData);
    return json;
  }
}
