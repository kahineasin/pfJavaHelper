package pf.java.pfHelper;

import pf.java.pfHelper.config.PFDataHelper;

//后端post的返回结果
public class PFRequestResult {
	public String content;
	public int statusCode;
	public String error;
	public Boolean success=true;
	public Boolean refuse=false;
	public String toString() {
		String r=String.valueOf(statusCode)+"\r\n";
		if(!PFDataHelper.StringIsNullOrWhiteSpace(content)) {
			r+=content+"\r\n";
		}
		if(!PFDataHelper.StringIsNullOrWhiteSpace(error)) {
			r+=error+"\r\n";
		}
		return r;
	}
	public void setError(String error) {
		this.error=error;
		success=false;
	}
}
