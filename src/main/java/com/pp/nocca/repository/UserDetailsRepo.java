package com.pp.nocca.repository;

import com.pp.nocca.document.UserDetails;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/
public interface UserDetailsRepo extends MongoRepository<UserDetails, String> {

  public UserDetails findByUserId(String userId);
}
