package cn.sgf.asset.core.auth;


import lombok.Data;

@Data
public class AdminUser {
	private String name;
	private Long roleId;
	private String account;
	private String password;
}
