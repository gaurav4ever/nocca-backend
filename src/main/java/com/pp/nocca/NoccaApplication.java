package com.pp.nocca;

import com.pp.nocca.repository.TokenDetailsRepo;
import com.pp.nocca.repository.UserDetailsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoccaApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(NoccaApplication.class, args);
  }

  @Autowired
  UserDetailsRepo userDetailsRepo;

  @Autowired
  TokenDetailsRepo tokenDetailsRepo;

  @Override
  public void run(String[] args) {
	/*	System.out.println("Printing all of the User Details");
		System.out.println(userDetailsRepo.findAll().toString());

		System.out.println("Printing all of the Token Details");
		System.out.println(tokenDetailsRepo.findAll().toString());

		System.out.println(userDetailsRepo.findByUserId("shikhar@gmail.com"));
*/
  }

}
