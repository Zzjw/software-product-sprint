package com.google.sps.data;

import java.util.Date;

public final class CommentValue {

  private final Date sendTime;
  private final String name;
  private final String comment;
  private final String imageUrl;

  public CommentValue(Date sendTime, String name, String comment, String imageUrl) {
    this.sendTime = sendTime;
    this.name = name;
    this.comment = comment;
    this.imageUrl = imageUrl;
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

  public String getImageUrl() { return imageUrl; }
}
