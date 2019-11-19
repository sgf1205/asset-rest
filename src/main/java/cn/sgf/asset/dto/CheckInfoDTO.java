package cn.sgf.asset.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CheckInfoDTO {

	private Long id;
	
	private String user;
	
	private String needCheckAssets;
	
	private Integer needCheckSize;
	
	private String alreadyCheckAssets;
	
	private Integer alreadyCheckSize;
	
	private Long organId;
	
	private String organName;
	
	private Date checkTime;
	
}
