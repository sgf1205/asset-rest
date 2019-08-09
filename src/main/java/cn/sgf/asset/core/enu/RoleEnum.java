package cn.sgf.asset.core.enu;

public enum RoleEnum {
	SYS_ROLE(1,"系统管理员"),
	NORMAL_ROLE(2,"普通管理员");
	
	private int code;
	
	private String name;
	
	private RoleEnum(int code,String name) {
		this.code=code;
		this.name=name;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static RoleEnum getByCode(int code) {
		for(RoleEnum enu:RoleEnum.values()) {
			if(enu.getCode()==code)
				return enu;
		}
		return null;
	}
}
