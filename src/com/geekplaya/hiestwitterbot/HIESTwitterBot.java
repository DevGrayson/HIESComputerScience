package com.geekplaya.hiestwitterbot;
import twitter4j.TwitterException;


public class HIESTwitterBot {

	public static void main (String[] args) {
		FavoriteColors favColors = new FavoriteColors();
		try {
			System.out.println("'HIESTwitterBot' has started.");
			favColors.startTweeting();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
