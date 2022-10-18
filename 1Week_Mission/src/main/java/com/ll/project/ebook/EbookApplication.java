package com.ll.project.ebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbookApplication.class, args);
	}

}
