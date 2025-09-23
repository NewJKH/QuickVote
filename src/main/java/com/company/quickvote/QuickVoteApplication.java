package com.company.quickvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QuickVoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickVoteApplication.class, args);
	}

}
