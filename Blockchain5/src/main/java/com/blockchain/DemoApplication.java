package com.blockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blockchain.blockchain.Main;
import com.blockchain.blockchain.Mineradora;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
		Main NChain = new Main();
		System.out.println(NChain.run());
		
		Mineradora mineradora = new Mineradora();
		System.out.println(mineradora.getBloco());
		
	}

}
