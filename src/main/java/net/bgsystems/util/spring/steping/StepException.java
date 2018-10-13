package net.bgsystems.util.spring.steping;

public class StepException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6959078816707895088L;
	private String stepName;

	public StepException(String stepName, String message, Throwable cause) {
		super(message, cause);
		this.stepName = stepName;
	}

	public String getStepName() {
		return stepName;
	}

	@Override
	public String toString() {
		return "Ocurrio un error en el step " + this.getStepName() + ": " + this.getMessage();
	}
}
