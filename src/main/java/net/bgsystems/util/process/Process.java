package net.bgsystems.util.process;

import java.util.Date;
import java.util.List;

public class Process<T> {
	private Date startDate;
	private Date finishDate;
	private List<Registry<T>> registries;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public List<Registry<T>> getRegistries() {
		return registries;
	}

	public void setRegistries(List<Registry<T>> registries) {
		this.registries = registries;
	}
}
