package com.devsuperior.dscommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DsCommerceDevsuperiorApplication {
	
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
