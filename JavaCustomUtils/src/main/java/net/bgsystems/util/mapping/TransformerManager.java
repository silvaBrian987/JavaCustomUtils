package net.bgsystems.util.mapping;

import java.util.ArrayList;
import java.util.List;

public class TransformerManager {
	private int rowsExpected;

	public TransformerManager() {
		this.rowsExpected = 0;
	}

	public TransformerManager(int rowsExpected) {
		this.rowsExpected = rowsExpected;
	}

	public int getRowsExpected() {
		return rowsExpected;
	}

	public void setRowsExpected(int rowsExpected) {
		this.rowsExpected = rowsExpected;
	}

	public <T, K> List<T> switchData(List<K> list, RowMapper<K, T> rowMapper) throws Exception {
		List<T> results = (this.rowsExpected > 0 ? new ArrayList<T>(this.rowsExpected) : new ArrayList<T>());
		int rowNum = 0;
		for (K k : list)
			results.add(rowMapper.mapRow(k, rowNum++));
		return results;
	}

	public <T, K> T switchData(K k, RowMapper<K, T> rowMapper) throws Exception {
		int rowNum = 0;
		return rowMapper.mapRow(k, rowNum++);
	}
}
