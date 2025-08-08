package com.devsuperior.dscommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DsCommerceDevsuperiorApplication implements CommandLineRunner {
	
	/*
	@Autowired
	private PasswordEncoder passwordEndoder;
	*/

	public static void main(String[] args) {
		SpringApplication.run(DsCommerceDevsuperiorApplication.class, args);
	}

	/*
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("ENDODE = " +  passwordEndoder.encode("123456"));
		
	}
	*/

}
