import twitter4j.TwitterException;


public class HIESTwitterBot {

	public static void main (String[] args) {
		FavoriteColors favColors = new FavoriteColors();
		try {
			favColors.startTweeting();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
