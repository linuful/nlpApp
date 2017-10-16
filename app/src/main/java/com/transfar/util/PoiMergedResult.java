package com.transfar.util;

public class PoiMergedResult {
	public boolean merged;
	public int startRow;
	public int endRow;
	public int startCol;
	public int endCol;
	public PoiMergedResult(boolean merged,int startRow,int endRow
	,int startCol,int endCol){
		this.merged = merged;
		this.startRow = startRow;
		this.endRow = endRow;
		this.startCol = startCol;
		this.endCol = endCol;
	}
}
