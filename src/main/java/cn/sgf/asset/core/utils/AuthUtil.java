package cn.sgf.asset.core.utils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.sgf.asset.core.auth.AdminUser;
import cn.sgf.asset.core.config.UserConfig;
import cn.sgf.asset.core.model.OnlineUser;
import cn.sgf.asset.dto.UserDTO;

@Component
public class AuthUtil {
	
	private static AdminUser admin;
	
	private static Map<String, OnlineUser> onlineUserMap=new ConcurrentHashMap<String, OnlineUser>();
	
	private static Long onlineTimeout;
	
	@Autowired
	private UserConfig userConfig;
	
	@PostConstruct
	public void init() {
		AuthUtil.admin=userConfig.getAdmin();
		AuthUtil.onlineTimeout=userConfig.getOnlineTimeout();
	}
	
	public static AdminUser getAdminUser() {
		return admin;
	}
	
	public static UserDTO getUserByToken(String token) {
		return onlineUserMap.get(token).getUserDto();
	}
	
	public static boolean validToken(String token) {
		OnlineUser onlineUser=onlineUserMap.get(token);
		if(onlineUser!=null) {
			onlineUser.setActiveTime(System.currentTimeMillis());
			return true;
		}
		return false;
	}
	
	public static HttpSession getSession() {
		HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session=request.getSession();
		return session;
	}
	
	public static void remove(String token) {
		onlineUserMap.remove(token);
	}
	
	public static String getToken() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public static void save(UserDTO userDto) {
		// TODO Auto-generated method stub
		OnlineUser onlineUser=new OnlineUser();
		userDto.setToken(getToken());
		onlineUser.setUserDto(userDto);
		onlineUser.setActiveTime(System.currentTimeMillis());
		onlineUser.setAccount(userDto.getAccount());
		onlineUserMap.put(userDto.getToken(), onlineUser);
	}

	public static boolean alreadyOnline(String account) {
		String timoutToken=null;
		for(OnlineUser onlineUser:onlineUserMap.values()) {
			if(onlineUser.getAccount().equals(account)) {
				long current=System.currentTimeMillis();
				if(current-onlineUser.getActiveTime()>onlineTimeout) {
					timoutToken=onlineUser.getUserDto().getToken();
				}else {
					return true;
				}
			}
		}
		if(timoutToken!=null) {
			remove(timoutToken);
		}
		return false;
	}
	
	
}
