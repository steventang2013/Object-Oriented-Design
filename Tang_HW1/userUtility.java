public class userUtility{
	
	public static void createUserName(String first, String last){

		String username = "";
		for (int i = 0; i < 3; i++){
			username += first.charAt(i);
		}
		for (int i = 0; i < 3; i++){
			username += last.charAt((last.length()-1)- i);
		}
		System.out.println("This is your new username: " + username);
	}
}

