package com.google.sps.data;

import com.google.sps.data.CommentValue;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CommentData {

  private final List<CommentValue> comments = new ArrayList<>();

  public CommentData() {
  }

  public void addCommentValue(CommentValue commentValue) {
    comments.add(commentValue);
  }
}
