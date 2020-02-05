package com.pp.nocca.document;

import com.pp.nocca.minidocuments.MiniVendorDetails;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Repository;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/
@Repository
@Document("TOKENDetails")
public class TokenDetails {

  @Id
  private String id;

  @Field("TokenId")
  private String tokenId;

  @Field("Vendor Details")
  private List<MiniVendorDetails> miniVendorDetailsList;

  public TokenDetails() {
  }

  public TokenDetails(String tokenId, List<MiniVendorDetails> miniVendorDetailsList) {
    this.tokenId = tokenId;
    this.miniVendorDetailsList = miniVendorDetailsList;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public List<MiniVendorDetails> getMiniVendorDetailsList() {
    return miniVendorDetailsList;
  }

  public void setMiniVendorDetailsList(List<MiniVendorDetails> miniVendorDetailsList) {
    this.miniVendorDetailsList = miniVendorDetailsList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "TokenDetails{" +
            "tokenId='" + tokenId + '\'' +
            ", miniVendorDetailsList=" + miniVendorDetailsList +
            '}';
  }
}
