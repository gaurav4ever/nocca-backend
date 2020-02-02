package com.pp.nocca.Model.request;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/
public class DeviceListingRequest {
  public String userId;
  public String panNo;

  public DeviceListingRequest() {
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPanNo() {
    return panNo;
  }

  public void setPanNo(String panNo) {
    this.panNo = panNo;
  }
}
