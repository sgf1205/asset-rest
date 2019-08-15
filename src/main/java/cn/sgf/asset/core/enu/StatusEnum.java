package cn.sgf.asset.core.enu;

public enum StatusEnum {
	FREE(0,"空闲"), 
	USED(1,"使用"),
	MAINTAIN(2,"维修"),
	SCRAPPED(3,"报废")
	;
	
	private int code;
	
	private String desc;
	
	private StatusEnum(int code,String desc) {
		this.code=code;
		this.desc=desc;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDesc() {
		return this.desc;
	}
}
