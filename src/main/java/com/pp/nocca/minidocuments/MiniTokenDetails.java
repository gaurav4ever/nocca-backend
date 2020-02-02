package com.pp.nocca.minidocuments;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/
public class MiniTokenDetails {

  @Field("tokenId")
  private String tokenId;

  @Field("tokenName")
  private String tokenName;

  public MiniTokenDetails() {
  }

  public MiniTokenDetails(String tokenId, String tokenName) {
    this.tokenId = tokenId;
    this.tokenName = tokenName;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getTokenName() {
    return tokenName;
  }

  public void setTokenName(String tokenName) {
    this.tokenName = tokenName;
  }

  @Override
  public String toString() {
    return "MiniTokenDetails{" +
            "tokenId='" + tokenId + '\'' +
            ", tokenName='" + tokenName + '\'' +
            '}';
  }
}
