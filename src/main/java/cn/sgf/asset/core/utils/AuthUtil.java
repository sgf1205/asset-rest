package cn.sgf.asset.core.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.sgf.asset.dto.UserDTO;

public class AuthUtil {
	
	public static UserDTO getUserByToken(String token) {
		
		return (UserDTO)getSession().getAttribute(token);
	}
	
	public static boolean validToken(String token) {
		return getSession().getAttribute(token)!=null;
	}
	
	public static HttpSession getSession() {
		HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session=request.getSession();
		return session;
	}
	
	public static void remove(String token) {
		getSession().removeAttribute(token);
	}
	
	public static String getToken() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public static void save(UserDTO userDto) {
		// TODO Auto-generated method stub
		userDto.setToken(getToken());
		getSession().setAttribute(userDto.getToken(), userDto);
	}
}
