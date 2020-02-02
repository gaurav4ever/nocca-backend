package com.pp.nocca.controller;

import com.pp.nocca.Model.request.PanListingRequest;
import com.pp.nocca.Model.request.PaymentPortalRequest;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

  private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

  @Autowired
  TokenDetailsRepo tokenDetailsRepo;

  @Autowired
  UserDetailsRepo userDetailsRepo;


  @PostMapping(value = "/getPanList", headers = "Accept=application/json")
  public ResponseEntity<PanDetailResponse> getPanList(@RequestBody PanListingRequest panListingRequest) {

    if(panListingRequest == null || panListingRequest.email == null){
      LOG.error("Bad Request Parameter for Get Pan List");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    UserDetails userDetails = userDetailsRepo.findByUserId(panListingRequest.email);

    if(userDetails == null){
      LOG.error(INVALIDEMAIL);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    List<MiniUserPANDetails> miniUserPANDetailsList = userDetails.getMiniUserPANDetailsList();

    if(miniUserPANDetailsList.size() == 0){
      LOG.error(NOPANERROR);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

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

    if(deviceListingRequest == null || deviceListingRequest.getUserId() == null || deviceListingRequest.getPanNo() == null){
      LOG.error("Bad Request Parameter for Get Devices List");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    UserDetails userDetails = userDetailsRepo.findByUserId(deviceListingRequest.getUserId());
    if(userDetails == null){
      LOG.error(TOKENERROR);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    List<MiniUserPANDetails> miniUserPANDetailsList = userDetails.getMiniUserPANDetailsList();

    List<MiniTokenDetails> miniTokenDetailsList = new ArrayList<>();
    for (MiniUserPANDetails miniUserPANDetails : miniUserPANDetailsList) {
      if(miniUserPANDetails == null){
        LOG.error(TOKENERROR);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      if (miniUserPANDetails.getPanNumber().equalsIgnoreCase(deviceListingRequest.getPanNo())) {
        miniTokenDetailsList.addAll(miniUserPANDetails.getMiniTokenDetailsList());
      }
    }

    DeviceDetailsResponse deviceDetailsResponse = new DeviceDetailsResponse();
    List<DeviceDetails> deviceDetailsList = new ArrayList<>();

    for (MiniTokenDetails miniTokenDetails : miniTokenDetailsList) {
      TokenDetails tokenDetails = tokenDetailsRepo.findByTokenId(miniTokenDetails.getTokenId());

      if(tokenDetails == null){
        LOG.error(TOKENERROR);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      List<MiniVendorDetails> vendorDetailsList = tokenDetails.getMiniVendorDetailsList();

      for (MiniVendorDetails miniVendorDetails : vendorDetailsList) {
        if(miniVendorDetails == null){
          LOG.error(TOKENERROR);
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

  @Transactional
  @PostMapping(value = "/updateDeviceStatus", headers = "Accept=application/json")
  public ResponseEntity<String> updateDevice(@RequestBody UpdateDeviceStatusRequest updateDeviceStatusRequest) {

    if(updateDeviceStatusRequest == null || updateDeviceStatusRequest.tokenId == null || updateDeviceStatusRequest.deviceId == null){
      LOG.error("Bad Request Parameter for Update Device Api");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    TokenDetails tokenDetails = tokenDetailsRepo.findByTokenId(updateDeviceStatusRequest.tokenId);
    List<MiniVendorDetails> vendorDetailsList = tokenDetails.getMiniVendorDetailsList();
    for (MiniVendorDetails miniVendorDetail : vendorDetailsList) {
      if (miniVendorDetail.getAccountIdDeviceId().equalsIgnoreCase(updateDeviceStatusRequest.deviceId)) {
        miniVendorDetail.setStatus(updateDeviceStatusRequest.status);
        tokenDetailsRepo.save(tokenDetails);
        break;
      }
    }
    return new ResponseEntity<>("success", HttpStatus.OK);
  }


  @PostMapping(value = "/paymentPortal", headers = "Accept=application/json")
  public ResponseEntity<DeviceDetailsResponse> paymentPortal(@RequestBody PaymentPortalRequest paymentPortalRequest){

    if(paymentPortalRequest == null || paymentPortalRequest.panNo == null){
      LOG.error("Bad Request Parameter for Payment Portal Api");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    List<UserDetails> userDetailsList = userDetailsRepo.findAll();

    List<MiniTokenDetails> miniTokenDetailsList = new ArrayList<>();

    for(UserDetails userDetails: userDetailsList){

      List<MiniUserPANDetails> miniUserPANDetailsList = userDetails.getMiniUserPANDetailsList();

      int breakFlag=0;
      for(MiniUserPANDetails miniUserPANDetails : miniUserPANDetailsList){
        if(miniUserPANDetails.getPanNumber().equals(paymentPortalRequest.panNo)){
          breakFlag = 1;
          miniTokenDetailsList.addAll(miniUserPANDetails.getMiniTokenDetailsList());
          break;
        }
      }
      if(breakFlag == 1){
        break;
      }
    }

    DeviceDetailsResponse deviceDetailsResponse = new DeviceDetailsResponse();
    List<DeviceDetails> deviceDetailsList = new ArrayList<>();

    for (MiniTokenDetails miniTokenDetails : miniTokenDetailsList) {
      TokenDetails tokenDetails = tokenDetailsRepo.findByTokenId(miniTokenDetails.getTokenId());

      if(tokenDetails == null){
        LOG.error(TOKENERROR);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      List<MiniVendorDetails> vendorDetailsList = tokenDetails.getMiniVendorDetailsList();

      for (MiniVendorDetails miniVendorDetails : vendorDetailsList) {
        if(miniVendorDetails == null){
          LOG.error(TOKENERROR);
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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





  private final static String TOKENERROR = "No Token Provisioned for this Pan";
  private final static String INVALIDEMAIL = "Enter a valid Email";
  private final static String NOPANERROR = "No Pan enrolled for this user";

}
