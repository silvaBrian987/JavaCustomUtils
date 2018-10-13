package net.bgsystems.util.mapping;

public interface RowMapper<K, T> {

	/**
	 * 
	 * @param obj
	 * @param rowNumber
	 * @return
	 */
	public T mapRow(K obj, int rowNumber) throws Exception;

}
