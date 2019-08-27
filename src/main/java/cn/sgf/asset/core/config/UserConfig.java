package cn.sgf.asset.core.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.sgf.asset.core.auth.AdminUser;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("user")
public class UserConfig {
	
	private String defaultPwd;
	
	private AdminUser admin;
	
	private List<Map<String, String>> defaultUsers;
}
