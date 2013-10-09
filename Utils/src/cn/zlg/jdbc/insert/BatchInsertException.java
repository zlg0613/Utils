package cn.zlg.jdbc.insert;

public class BatchInsertException extends Exception {

	public BatchInsertException() {
	}

	public BatchInsertException(String message) {
		super(message);
	}

	public BatchInsertException(Throwable cause) {
		super(cause);
	}

	public BatchInsertException(String message, Throwable cause) {
		super(message, cause);
	}

}
