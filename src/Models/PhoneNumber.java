package Models;

import java.io.*;
public class PhoneNumber implements Serializable {
    private static final long serialVersionUID = 1L;
	private int regionCode, number;
	
	public PhoneNumber(int regionCode, int number) {
		this.number = number;
		this.regionCode = regionCode;
	}
	
	public int getRegionCode() {
		return this.regionCode;
	}

	public int getNumber() {
		return this.number;
	}
	
	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String toString() {
		return this.regionCode + " " + this.number;
	}
}
