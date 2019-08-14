package cn.sgf.asset.core.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "user.admin")
public class AdminUser {
	private String name;
	private Long roleId;
	private String account;
	private String password;
}
