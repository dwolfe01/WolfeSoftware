package com.wolfesoftware.gds.users.sessionmanager;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import com.wolfesoftware.gds.users.User;
import com.wolfesoftware.gds.users.Users;
import com.wolfesoftware.gds.users.UsersAPI;

public class CustomJDBCRealm extends JdbcRealm {
	
	Users users = new UsersAPI();
	
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
	    User user = users.findUser(username);
		return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
	}

}
