package pf.java.pfHelper;

import java.awt.List;
import java.util.ArrayList;

public class PFDataColumnCollection extends ArrayList<PFDataColumn>{
	public PFDataColumn add(String columnName, Class type) {
		PFDataColumn column=new PFDataColumn(columnName,type);		
		super.add(column );
		return column;
	}
}
