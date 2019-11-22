package cn.sgf.asset.core.enu;

public enum SysOpsTypeEnum {
	LOGIN(1,"登陆"), 
	LOGOUT(2,"登出"),
	REGISTER(3,"资产登记"),
	DELETE(4,"资产删除"),
	MAINTAIN(5,"资产维修"),
	SCRAPPED(6,"资产报废"),
	USED_BORROW(7,"资产借用"),
	USED_RECEIVE(8,"资产领用"),
	USED_BORROW_RETURN(9,"资产归还"),
	SCRAPPED_RECOVERY(10,"资产报废恢复"),
	MAINTAIN_FINISH(11,"资产完成维修"),
	USED_RECEIVE_REVERT(12,"资产退库")

	;
	
	private int code;
	
	private String desc;
	
	private SysOpsTypeEnum(int code,String desc) {
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
