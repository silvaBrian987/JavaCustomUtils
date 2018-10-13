package net.bgsystems.util.spring.steping;

public abstract class Step {
	public abstract void ejecutar() throws StepException;

	public String getStepName() {
		return this.getClass().getSimpleName();
	}
}
