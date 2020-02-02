package com.pp.nocca.repository;

import com.pp.nocca.document.TokenDetails;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Written by Gaurav Sharma on 2020-02-02.
 **/
public interface TokenDetailsRepo extends MongoRepository<TokenDetails, String> {

  public TokenDetails findByTokenId(String tokenId);

}
