package cn.sgf.asset.core.enu;

public enum RoleEnum {
	SYS_ROLE(1l,"系统管理员"),
	NORMAL_ROLE(2l,"普通管理员");
	
	private Long code;
	
	private String name;
	
	private RoleEnum(Long code,String name) {
		this.code=code;
		this.name=name;
	}
	
	public Long getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static RoleEnum getByCode(Long code) {
		for(RoleEnum enu:RoleEnum.values()) {
			if(enu.getCode()==code)
				return enu;
		}
		return null;
	}
}
