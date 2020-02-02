package com.pp.nocca.repository;

import com.pp.nocca.document.UserDetails;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/
public interface UserDetailsRepo extends MongoRepository<UserDetails, String> {

  public UserDetails findByUserId(String userId);

  UserDetails findByEmail(String email);
}
