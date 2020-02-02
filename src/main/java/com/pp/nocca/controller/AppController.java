package com.pp.nocca.controller;

import com.pp.nocca.Model.UserAndPan;
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
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping(value = "/getPanList", headers = "Accept=application/json")
  public List<String> getPanList(@RequestBody String userId) {

    UserDetails userDetails = userDetailsRepo.findByUserId(userId);
    List<MiniUserPANDetails> miniUserPANDetailsList = userDetails.getMiniUserPANDetailsList();

    List<String> panList = new ArrayList<>();
    for (MiniUserPANDetails miniUserPANDetails : miniUserPANDetailsList) {
      panList.add(miniUserPANDetails.getPanNumber());
    }

    return panList;
  }

  @GetMapping(value = "/getDevicesList", headers = "Accept=application/json")
  public Map<String,List<MiniVendorDetails>> getPanList(@RequestBody UserAndPan userAndPan) {

    UserDetails userDetails = userDetailsRepo.findByUserId(userAndPan.getUserId());
    List<MiniUserPANDetails> miniUserPANDetailsList = userDetails.getMiniUserPANDetailsList();

    List<MiniTokenDetails> miniTokenDetailsList = new ArrayList<>();
    for (MiniUserPANDetails miniUserPANDetails : miniUserPANDetailsList) {
      if (miniUserPANDetails.getPanNumber().equalsIgnoreCase(userAndPan.getPanNo())) {
        miniTokenDetailsList.addAll(miniUserPANDetails.getMiniTokenDetailsList());
      }
    }

    Map<String,List<MiniVendorDetails>> miniVendorDetailsMap = new HashMap<>();

    for (MiniTokenDetails miniTokenDetails : miniTokenDetailsList) {
      TokenDetails tokenDetails = tokenDetailsRepo.findByTokenId(miniTokenDetails.getTokenId());

      List<MiniVendorDetails> vendorDetailsList = tokenDetails.getMiniVendorDetailsList();

      miniVendorDetailsMap.put(tokenDetails.getTokenId(),vendorDetailsList);
    }

    return miniVendorDetailsMap;
  }



}
