package com.google.sps.data;

import java.util.Date;

public final class CommentData {

  private final Date sendTime;
  private final String name;
  private final String comment;

  public CommentData(Date sendTime, String name, String comment) {
    this.sendTime = sendTime;
    this.name = name;
    this.comment = comment;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public String getName() {
    return name;
  }

  public String getComment() {
    return comment;
  }
}
