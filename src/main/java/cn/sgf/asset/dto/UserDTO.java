package cn.sgf.asset.dto;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
    private String name;
    private String account;
    private String pwd;
    private Long organId;
    private Integer roleId;
    private String organName;
    private String roleName;
    private int deleteFlag;
}
