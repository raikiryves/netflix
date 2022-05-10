package com.teamfour.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class WebserviceApplication {


	public static void main(String[] args)
	{
		SpringApplication.run(WebserviceApplication.class, args);
		sayHello();
	}

	@GetMapping("/")
	public static String sayHello(){
		return "Hello !";
	}

}
