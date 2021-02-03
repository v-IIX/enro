package edu.lxq.enro.security;

public class Result {

	private Boolean isok;
	private Integer code;
	private String msg;
	private Object data;
	
	public Result(Boolean isok,Integer code,String msg,Object data) {
		this.isok=isok;
		this.code=code;
		this.msg=msg;
		this.data=data;
	}

	public Boolean getIsok() {
		return isok;
	}

	public void setIsok(Boolean isok) {
		this.isok = isok;
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
}
