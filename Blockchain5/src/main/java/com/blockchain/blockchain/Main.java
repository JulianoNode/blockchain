package com.blockchain.blockchain;

public class Main {

	public String run() {

		try {
		
			Node no = new Node();
			no.start(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "------------------";
		
	}
	
}
