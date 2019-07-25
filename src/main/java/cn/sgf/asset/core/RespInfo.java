package cn.sgf.asset.core;


public class RespInfo {

	private Integer code;
	
	private String msg;
	
	private Object data;
	
	public RespInfo() {
		
	}
	
	public Integer getCode() {
		return code;
	}



	public void setCode(Integer code) {
		this.code = code;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	public Object getData() {
		return data;
	}



	public void setData(Object data) {
		this.data = data;
	}



	public RespInfo(Integer code) {
		this.code=code;
	}
	
	public RespInfo(Integer code,String msg) {
		this.code=code;
		this.msg=msg;
	}
	
	public static RespInfo success() {
		return new RespInfo(RespCodeEnum.SUCCESS.code);
	}
	
	public static RespInfo success(Object data) {
		RespInfo respInfo=success();
		respInfo.setData(data);
		return respInfo;
	}
	
	public enum RespCodeEnum{
		SUCCESS(0),
		FAIL(1);
		
		private int code;
		
		RespCodeEnum(int code){
			this.code=code;
		}
		
		public int getCode() {
			return this.code;
		}
	}
	
	
}
