package cn.jeesoft.core.exception;

/**
 * 权限异常
 * @author huangfei
 */
public class PrivilegeException extends Exception {

	private static final long serialVersionUID = 1L;

	public PrivilegeException() {
		super();
	}

	public PrivilegeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public PrivilegeException(String arg0) {
		super(arg0);
	}

	public PrivilegeException(Throwable arg0) {
		super(arg0);
	}


}
