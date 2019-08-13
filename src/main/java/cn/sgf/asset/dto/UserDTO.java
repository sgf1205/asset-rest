package cn.sgf.asset.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
    private String name;
    private String account;
    private String pwd;
    private Long organId;
    private String token;
    private Long roleId;
    private String organName;
    private String roleName;
    private int deleteFlag;
    private List<String> menus;
}
