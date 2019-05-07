package main.java.lotf.util;

import main.java.lotf.client.KeyHandler;

public class KeyInfo {

	private final KeyHandler.KeyType keyType;
	private final int key1, key2;
	
	public KeyInfo(KeyHandler.KeyType keyType, int key1, int key2) {
		this.keyType = keyType;
		this.key1 = key1;
		this.key2 = key2;
	}
	
	public boolean contains(int key) {
		if (key1 == key || key2 == key) {
			return true;
		}
		
		return false;
	}
	
	public int getKey1() {
		return key1;
	}
	
	public int getKey2() {
		return key2;
	}
	
	public KeyHandler.KeyType getKeyType() {
		return keyType;
	}
}
