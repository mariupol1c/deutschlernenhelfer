package com.mariupol.deutschlernenhelfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeutschlernenhelferApplication {

	public static String getSuchende() {
		return suchende;
	}

	public static void setSuchende(String suchende) {
		DeutschlernenhelferApplication.suchende = suchende;
	}

	private static String suchende = "";

	public static void main(String[] args) {
		SpringApplication.run(DeutschlernenhelferApplication.class, args);
	}
}
