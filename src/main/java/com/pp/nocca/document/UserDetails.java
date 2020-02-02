package com.pp.nocca.document;

import com.pp.nocca.minidocuments.MiniUserPANDetails;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/

@Document("USERDetails")
public class UserDetails {

  @Field("userId")
  private String userId;

  @Field("userName")
  private String userName;

  @Field("userPANDetails")
  private List<MiniUserPANDetails> miniUserPANDetailsList;

  public UserDetails() {
  }

  public UserDetails(String userId, String userName, List<MiniUserPANDetails> miniUserPANDetailsList) {
    this.userId = userId;
    this.userName = userName;
    this.miniUserPANDetailsList = miniUserPANDetailsList;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public List<MiniUserPANDetails> getMiniUserPANDetailsList() {
    return miniUserPANDetailsList;
  }

  public void setMiniUserPANDetailsList(List<MiniUserPANDetails> miniUserPANDetailsList) {
    this.miniUserPANDetailsList = miniUserPANDetailsList;
  }

  @Override
  public String toString() {
    return "UserDetails{" +
            "userId='" + userId + '\'' +
            ", userName='" + userName + '\'' +
            ", miniUserPANDetailsList=" + miniUserPANDetailsList +
            '}';
  }
}
