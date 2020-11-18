package entity;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class OperationResponse {
	private String errMsg;
	private boolean isOK ;
	private boolean isValid;
	private String permission;
    private String responseString;
    private int statusCode;
	UUser user;
	DirInfo data[];
	private InputStream in;
	private OutputStream out;
	public InputStream getIn() {
		return in;
	}
	public void setIn(InputStream in) {
		this.in = in;
	}
	public OutputStream getOut() {
		return out;
	}
	public void setOut(OutputStream out) {
		this.out = out;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public boolean getIsOK() {
		return isOK;
	}
	public void setIsOK(boolean isOK) {
		this.isOK = isOK;
	}
	public boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public UUser getUser() {
		return user;
	}
	public void setUser(UUser user) {
		this.user = user;
	}
	public DirInfo[] getData() {
		return data;
	}
	public void setData(DirInfo[] data) {
		this.data = data;
	}

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
