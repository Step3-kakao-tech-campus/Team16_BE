package com.daggle.animory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class AnimoryApplication {

	@PostConstruct
	void setKoreanTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(final String[] args) {
		SpringApplication.run(AnimoryApplication.class, args);
	}

}
