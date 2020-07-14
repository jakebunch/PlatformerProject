package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Utils {
	
	private static Random rand = new Random();
	
	public static String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				builder.append(line + "\n");
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static int parseInt(String number) {
		try { 
			return Integer.parseInt(number);
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int randomInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
}
