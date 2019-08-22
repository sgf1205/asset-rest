package cn.sgf.asset.core.enu;

public enum ApplyTypeEnum {
	RECEIVE(1,"领取"), 
	BORROW(2,"借用"),
	RETURN(3,"归还");
	
	private int code;
	
	private String desc;
	
	private ApplyTypeEnum(int code,String desc) {
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
