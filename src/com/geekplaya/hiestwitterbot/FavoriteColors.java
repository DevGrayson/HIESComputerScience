package com.geekplaya.hiestwitterbot;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;


public class FavoriteColors {

	Twitter twitter;
	ArrayList<String> objects = new ArrayList<String>();
	boolean stop = false;
	
	
	public FavoriteColors() {
		twitter = TwitterFactory.getSingleton();
		defineColors();
	}
	
	public void defineColors() {
		objects.addAll(Arrays.asList(new String("AliceBlue AntiqueWhite Aqua Aquamarine Azure Beige Bisque Black BlanchedAlmond Blue BlueViolet Brown BurlyWood CadetBlue Chartreuse Chocolate Coral CornflowerBlue Cornsilk Crimson Cyan DarkBlue DarkCyan DarkGoldenRod DarkGray DarkGreen DarkKhaki DarkMagenta DarkOliveGreen DarkOrange DarkOrchid DarkRed DarkSalmon DarkSeaGreen DarkSlateBlue DarkSlateGray DarkTurquoise DarkViolet DeepPink DeepSkyBlue DimGray DodgerBlue FireBrick FloralWhite ForestGreen Fuchsia Gainsboro GhostWhite Gold GoldenRod Gray Green GreenYellow HoneyDew HotPink IndianRed  Indigo  Ivory Khaki Lavender LavenderBlush LawnGreen LemonChiffon LightBlue LightCoral LightCyan LightGoldenRodYellow LightGray LightGreen LightPink LightSalmon LightSeaGreen LightSkyBlue LightSlateGray LightSteelBlue LightYellow Lime LimeGreen Linen Magenta Maroon MediumAquaMarine MediumBlue MediumOrchid MediumPurple MediumSeaGreen MediumSlateBlue MediumSpringGreen MediumTurquoise MediumVioletRed MidnightBlue MintCream MistyRose Moccasin NavajoWhite Navy OldLace Olive OliveDrab Orange OrangeRed Orchid PaleGoldenRod PaleGreen PaleTurquoise PaleVioletRed PapayaWhip PeachPuff Peru Pink Plum PowderBlue Purple Red RosyBrown RoyalBlue SaddleBrown Salmon SandyBrown SeaGreen SeaShell Sienna Silver SkyBlue SlateBlue SlateGray Snow SpringGreen SteelBlue Tan Teal Thistle Tomato Turquoise Violet Wheat White WhiteSmoke Yellow YellowGreen").toLowerCase().split(" ")));
	}
	
	public String getColor(String status) {
		String[] statusArr = status.split(" ");
		for(int i = 0; i < statusArr.length; i++) {
			if(statusArr[i].toLowerCase().equals("favorite") &&
				statusArr[i+1] != null &&
				statusArr[i+1].toLowerCase().equals("color") &&
				statusArr[i+2] != null &&
				statusArr[i+2].toLowerCase().equals("is") &&
				statusArr[i+3] != null &&
				objects.contains(statusArr[i+3].toLowerCase().replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2"))) {
					return statusArr[i+3].toLowerCase().replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
			}
		}
		return null;
	}
	
	public void startTweeting() throws TwitterException {
		Query query = new Query("\"My favorite color is \"");
		query.setCount(15);
	    QueryResult result = twitter.search(query);
	    for(Status s : result.getTweets()) {
	    	String color = getColor(s.getText());
	    	if(color != null && !alreadyFollowing(s.getUser()) && !s.isRetweet()) {
	    		String tweet = "@" + s.getUser().getScreenName() + "'s favorite color is #" + color + "!";
	    		twitter.updateStatus(tweet);
	    		System.out.println("Tweeted: " + tweet);
	    		twitter.createFriendship(s.getUser().getScreenName());
	    		return;
	    	}
	    }	    
		return;
	}
	
	public void stopTweeting() {
		stop = true;
		System.out.println("'HIESTwitterBot' is stopping...");
	}
	
	public boolean alreadyFollowing(User u) throws TwitterException {
		return twitter.showFriendship(twitter.getScreenName(), u.getScreenName()).isSourceFollowingTarget();
	}
	
}
