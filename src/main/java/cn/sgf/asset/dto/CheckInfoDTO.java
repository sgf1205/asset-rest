package cn.sgf.asset.dto;

import lombok.Data;

@Data
public class CheckInfoDTO {

	private Long id;
	
	private String user;
	
	private String needCheckAssets;
	
	private String alreadyCheckAssets;
	
	private Long organId;
	
}
