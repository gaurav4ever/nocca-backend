package com.pp.nocca.controller;

import com.pp.nocca.Model.request.PanListingRequest;
import com.pp.nocca.Model.request.UpdateDeviceStatusRequest;
import com.pp.nocca.Model.request.DeviceListingRequest;
import com.pp.nocca.Model.response.DeviceDetails;
import com.pp.nocca.Model.response.DeviceDetailsResponse;
import com.pp.nocca.Model.response.PanDetailResponse;
import com.pp.nocca.Model.response.PanDetails;
import com.pp.nocca.document.TokenDetails;
import com.pp.nocca.document.UserDetails;
import com.pp.nocca.minidocuments.MiniTokenDetails;
import com.pp.nocca.minidocuments.MiniUserPANDetails;
import com.pp.nocca.minidocuments.MiniVendorDetails;
import com.pp.nocca.repository.TokenDetailsRepo;
import com.pp.nocca.repository.UserDetailsRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/

@RestController
@RequestMapping("/nocca")
public class AppController {

  @Autowired
  TokenDetailsRepo tokenDetailsRepo;

  @Autowired
  UserDetailsRepo userDetailsRepo;

  @PostMapping(value = "/getPanList", headers = "Accept=application/json")
  public ResponseEntity<PanDetailResponse> getPanList(@RequestBody PanListingRequest panListingRequest) {

    UserDetails userDetails = userDetailsRepo.findByUserId(panListingRequest.email);
    List<MiniUserPANDetails> miniUserPANDetailsList = userDetails.getMiniUserPANDetailsList();

    PanDetailResponse panDetailResponse = new PanDetailResponse();
    List<PanDetails> panDetailList = new ArrayList<>();
    for (MiniUserPANDetails miniUserPANDetails : miniUserPANDetailsList) {
      PanDetails panDetails = new PanDetails();
      panDetails.panId = miniUserPANDetails.getPanId();
      panDetails.panNumber = miniUserPANDetails.getPanNumber();
      panDetailList.add(panDetails);
    }
    panDetailResponse.panDetails = panDetailList;
    return new ResponseEntity<>(panDetailResponse, HttpStatus.OK);
  }

  @PostMapping(value = "/getDevicesList", headers = "Accept=application/json")
  public ResponseEntity<DeviceDetailsResponse> getPanList(@RequestBody DeviceListingRequest deviceListingRequest) {


    UserDetails userDetails = userDetailsRepo.findByUserId(deviceListingRequest.getUserId());
    List<MiniUserPANDetails> miniUserPANDetailsList = userDetails.getMiniUserPANDetailsList();

    List<MiniTokenDetails> miniTokenDetailsList = new ArrayList<>();
    for (MiniUserPANDetails miniUserPANDetails : miniUserPANDetailsList) {
      if (miniUserPANDetails.getPanNumber().equalsIgnoreCase(deviceListingRequest.getPanNo())) {
        miniTokenDetailsList.addAll(miniUserPANDetails.getMiniTokenDetailsList());
      }
    }

    Map<String, List<MiniVendorDetails>> miniVendorDetailsMap = new HashMap<>();

    DeviceDetailsResponse deviceDetailsResponse = new DeviceDetailsResponse();
    List<DeviceDetails> deviceDetailsList = new ArrayList<>();

    for (MiniTokenDetails miniTokenDetails : miniTokenDetailsList) {
      TokenDetails tokenDetails = tokenDetailsRepo.findByTokenId(miniTokenDetails.getTokenId());

      List<MiniVendorDetails> vendorDetailsList = tokenDetails.getMiniVendorDetailsList();


      for (MiniVendorDetails miniVendorDetails : vendorDetailsList) {
        DeviceDetails deviceDetails = new DeviceDetails();
        deviceDetails.deviceId = miniVendorDetails.getAccountIdDeviceId();
        deviceDetails.deviceName = miniVendorDetails.getDeviceName();
        deviceDetails.status = miniVendorDetails.getStatus();
        deviceDetails.tokenId = tokenDetails.getTokenId();
        deviceDetailsList.add(deviceDetails);
      }

    }
    deviceDetailsResponse.deviceDetailsList = deviceDetailsList;
    return new ResponseEntity<>(deviceDetailsResponse, HttpStatus.OK);
  }


  @PostMapping(value = "/updateDevice", headers = "Accept=application/json")
  public ResponseEntity<String> updateDevice(@RequestBody UpdateDeviceStatusRequest updateDeviceStatusRequest) {
    TokenDetails tokenDetails = tokenDetailsRepo.findByTokenId(updateDeviceStatusRequest.tokenId);
    List<MiniVendorDetails> vendorDetailsList = tokenDetails.getMiniVendorDetailsList();
    for (MiniVendorDetails miniVendorDetail : vendorDetailsList) {
      if (miniVendorDetail.getAccountIdDeviceId().equalsIgnoreCase(updateDeviceStatusRequest.deviceId)) {
        miniVendorDetail.setStatus(0);
        tokenDetailsRepo.save(tokenDetails);
      }
    }
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

}
