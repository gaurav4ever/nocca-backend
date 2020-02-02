package com.pp.nocca.Model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/
@JsonIgnoreProperties(ignoreUnknown = false)
public class DeviceDetailsResponse {


  @JsonProperty("deviceDetails")
  public List<DeviceDetails> deviceDetailsList;


}
