package auth;

import models.User;
import play.mvc.Controller;

public class Auth {
	public static boolean login(String username, String password) {
		User user = User.find.where().eq("username", username).findUnique();
		
		if (user == null) {
			return false;
		}
		
		if (!user.passwordHash.equals(User.hash(password))) {
			return false;
		}
		
		Controller.session("user_id", Long.toString(user.id));
		return true;
	}
	
	public static void logout() {
		Controller.session("user_id", null);
	}
	
	public static Long getUserId() {
		String userId = Controller.session("user_id");
		
		if (userId == null) {
			return null;
		}
		
		return Long.parseLong(userId);
	}
	
	public static User getUser() {
		Long userId = getUserId();
		
		if (userId == null) {
			return null;
		}
		
		return User.find.byId(userId);
	}
	
	public static boolean isLoggedIn() {
		return getUserId() != null;
	}
}
