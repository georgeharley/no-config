package io.pivotal.noconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class NoconfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoconfigApplication.class, args);
	}
}
