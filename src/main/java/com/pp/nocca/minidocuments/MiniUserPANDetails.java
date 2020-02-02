package com.pp.nocca.minidocuments;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 */
public class MiniUserPANDetails {

  @Field("PanId")
  private String panId;

  @Field("PanNumber")
  private String panNumber;

  @Field("tokenDetails")
  private List<MiniTokenDetails> miniTokenDetailsList;

  public MiniUserPANDetails() {
  }

  public MiniUserPANDetails(String panId, String panNumber, List<MiniTokenDetails> miniTokenDetailsList) {
    this.panId = panId;
    this.panNumber = panNumber;
    this.miniTokenDetailsList = miniTokenDetailsList;
  }

  public String getPanId() {
    return panId;
  }

  public void setPanId(String panId) {
    this.panId = panId;
  }

  public String getPanNumber() {
    return panNumber;
  }

  public void setPanNumber(String panNumber) {
    this.panNumber = panNumber;
  }

  public List<MiniTokenDetails> getMiniTokenDetailsList() {
    return miniTokenDetailsList;
  }

  public void setMiniTokenDetailsList(List<MiniTokenDetails> miniTokenDetailsList) {
    this.miniTokenDetailsList = miniTokenDetailsList;
  }

  @Override
  public String toString() {
    return "MiniUserPANDetails{" +
            "panId='" + panId + '\'' +
            ", panNumber='" + panNumber + '\'' +
            ", miniTokenDetailsList=" + miniTokenDetailsList +
            '}';
  }
}
