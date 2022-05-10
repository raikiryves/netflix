package com.teamfour.frontwebservice;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import java.util.List;



@RestController
@SpringBootApplication
@EnableCircuitBreaker
public class FrontwebserviceApplication {

	@Autowired
	 DiscoveryClient discoveryClient;

	@Autowired
	private LoadBalancerClient loadBalancer;

	public static void main(String[] args) {


		SpringApplication.run(FrontwebserviceApplication.class, args);

	}

	@HystrixCommand(fallbackMethod = "defaultMessage")
	@GetMapping("/")
	public String hello() {
		List<ServiceInstance> instances = discoveryClient.getInstances("Netflix");
		ServiceInstance test = instances.get(0);
		String hostname = test.getHost();
		int port = test.getPort();
		RestTemplate restTemplate = new RestTemplate();
		String microservice1Address = "http://" + hostname + ":" + port;
		ResponseEntity<String> response =
				restTemplate.getForEntity(microservice1Address, String.class);
		String s = response.getBody();
		return s;
	}


	public String defaultMessage() {
		return "Salut !";
	}

	@GetMapping()
	public void method() {

		ServiceInstance serviceInstance = loadBalancer.choose("Netflix");
		System.out.println(serviceInstance.getUri());
	}

}
