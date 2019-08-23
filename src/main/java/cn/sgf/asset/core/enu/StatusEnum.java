package cn.sgf.asset.core.enu;

public enum StatusEnum {
	FREE(0,"空闲"), 
	USED_BORROW(2,"借用"),
	USED_RECEIVE(3,"领用"),
	MAINTAIN(4,"维修"),
	SCRAPPED(5,"报废"),
	USED_BORROW_RETURN(6,"借用归还"),
	SCRAPPED_RECOVERY(7,"恢复"),
	MAINTAIN_FINISH(8,"维修完成")
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
