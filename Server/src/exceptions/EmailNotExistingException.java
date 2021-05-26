package exceptions;

public class EmailNotExistingException extends Exception {
	
	private static final long serialVersionUID = 3476787444873922715L;
	
	public EmailNotExistingException(String errorMessage) {
        super(errorMessage);
    }

}
