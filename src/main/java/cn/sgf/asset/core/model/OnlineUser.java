package cn.sgf.asset.core.model;

import cn.sgf.asset.dto.UserDTO;
import lombok.Data;

@Data
public class OnlineUser {
	
	private UserDTO userDto;
	
	 //上次活跃时间戳
	private Long activeTime;
	
	private String account;
	
}
