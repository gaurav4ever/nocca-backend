package com.pp.nocca.minidocuments;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/
public class MiniVendorDetails {

  @Field("MerchantFlag")
  private int merchantFlag;

  @Field("MerchantName")
  private String merchantName;

  @Field("DeviceName")
  private String deviceName;

  @Field("AccountId_DeviceId")
  private String accountIdDeviceId;

  @Field("status")
  private int status;

  public MiniVendorDetails() {
  }

  public MiniVendorDetails(int merchantFlag, String merchantName, String deviceName, String accountIdDeviceId, int status) {
    this.merchantFlag = merchantFlag;
    this.merchantName = merchantName;
    this.deviceName = deviceName;
    this.accountIdDeviceId = accountIdDeviceId;
    this.status = status;
  }

  public int getMerchantFlag() {
    return merchantFlag;
  }

  public void setMerchantFlag(int merchantFlag) {
    this.merchantFlag = merchantFlag;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getAccountIdDeviceId() {
    return accountIdDeviceId;
  }

  public void setAccountIdDeviceId(String accountIdDeviceId) {
    this.accountIdDeviceId = accountIdDeviceId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "MiniVendorDetails{" +
            "merchantFlag=" + merchantFlag +
            ", merchantName='" + merchantName + '\'' +
            ", deviceName='" + deviceName + '\'' +
            ", accountIdDeviceId='" + accountIdDeviceId + '\'' +
            ", status=" + status +
            '}';
  }
}
