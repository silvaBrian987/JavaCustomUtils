package net.bgsystems.util.process;

import java.io.File;

import net.bgsystems.util.mapping.RowMapper;

public class FileContext {
	private File file;
	private Action action;
	private Class<RowMapper<?, ?>> mapperClass;
	private boolean ignoreHeader;
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public Class<RowMapper<?, ?>> getMapperClass() {
		return mapperClass;
	}
	
	public void setMapperClass(Class<RowMapper<?, ?>> mapperClass) {
		this.mapperClass = mapperClass;
	}
	
	public boolean isIgnoreHeader() {
		return ignoreHeader;
	}
	
	public void setIgnoreHeader(boolean ignoreHeader) {
		this.ignoreHeader = ignoreHeader;
	}
}
