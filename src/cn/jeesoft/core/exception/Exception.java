package cn.jeesoft.core.exception;

/**
 * 异常基类
 * @author king
 */
public class Exception extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public Exception() {
		super();
	}

	public Exception(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public Exception(String arg0) {
		super(arg0);
	}

	public Exception(Throwable arg0) {
		super(arg0);
	}
	
	
}
