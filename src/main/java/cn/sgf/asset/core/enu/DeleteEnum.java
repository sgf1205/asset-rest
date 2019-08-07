package cn.sgf.asset.core.enu;

public enum DeleteEnum {
	DELETED(1),
	NO_DELETED(0);
	
	private int code;
	
	private DeleteEnum(int code) {
		this.code=code;
	}
	
	public int getCode() {
		return this.code;
	}
}
