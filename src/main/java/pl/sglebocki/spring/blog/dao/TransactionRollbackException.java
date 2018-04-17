package pl.sglebocki.spring.blog.dao;

public class TransactionRollbackException extends RuntimeException {
	
	private static final long serialVersionUID = 4322027567018735883L;

	public TransactionRollbackException(String message) {
		super(message);
	}
	
	public TransactionRollbackException(Throwable e) {
		super(e);
	}
	
}
