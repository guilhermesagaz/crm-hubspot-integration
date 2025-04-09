package com.integracao.crmhubspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CrmHubspotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmHubspotApplication.class, args);
	}

}
