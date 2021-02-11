package com.shivu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.shivu.storageService.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class})
public class SpringBootAdminEnduserJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAdminEnduserJwtApplication.class, args);
	}

}
