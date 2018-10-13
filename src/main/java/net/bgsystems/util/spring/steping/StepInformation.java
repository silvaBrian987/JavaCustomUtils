package net.bgsystems.util.spring.steping;

public class StepInformation {
	private String name;
	private Class<? extends Step> clazz;
	private StepInformation next;
	private StepInformation previous;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<? extends Step> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends Step> clazz) {
		this.clazz = clazz;
	}

	public StepInformation getNext() {
		return next;
	}

	public void setNext(StepInformation next) {
		this.next = next;
	}

	public StepInformation getPrevious() {
		return previous;
	}

	public void setPrevious(StepInformation previous) {
		this.previous = previous;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
