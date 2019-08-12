package cn.sgf.asset.dto;

import lombok.Data;

@Data
public class RoleConfigDTO {
	private Long roleId;
	
	private String roleName;
	
	private String[] menus;

}
