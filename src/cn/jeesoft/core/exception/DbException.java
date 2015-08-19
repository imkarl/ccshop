package cn.jeesoft.core.exception;

/**
 * 数据库操作异常
 * @author king
 */
public class DbException extends Exception {
	private static final long serialVersionUID = 1L;

	public DbException() {
		super();
	}

	public DbException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DbException(String arg0) {
		super(arg0);
	}

	public DbException(Throwable arg0) {
		super(arg0);
	}
	
	
}
