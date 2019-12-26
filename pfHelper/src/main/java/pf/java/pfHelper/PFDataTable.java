package pf.java.pfHelper;

import java.util.ArrayList;
import java.util.List;

public class PFDataTable {

	public List<PFDataRow> Rows=new ArrayList<PFDataRow>();
    public PFDataColumnCollection Columns=new PFDataColumnCollection();//NewRow需要用到--benjamin20191114
 
    public PFDataTable() {
 
    }
 
    public PFDataTable(List<PFDataRow> r) {
        Rows = r;
    }
 
    public List<PFDataRow> getRow() {
        return Rows;
    }
 
    public void setRow(List<PFDataRow> row) {
        this.Rows = row;
    }
    public Boolean IsEmpty() {
        return Rows==null||Rows.isEmpty();
    }
    public PFDataRow NewRow() {
    	PFDataRow newRow=new PFDataRow(Columns);
    	Rows.add(newRow);
        return newRow;
    }
 
    /**
     * @功能描述 双表根据两表关联字段连接，要求两表必须包含公告字段，并且每一行数据公共字段相等 。dt1对应colname1 ,dt2
     *       对应colName2
     * @可能的错误 未完成
     * @作者 叶小钗
     * @修改说明
     * @修改人
     */
    public static PFDataTable joinTable(PFDataTable dt1, PFDataTable dt2, String colName1,
            String colName2) {
        List<PFDataRow> newRows = new ArrayList<PFDataRow>();
 
        List<PFDataRow> rows1 = dt1.getRow();
        List<PFDataRow> rows2 = dt2.getRow();
 
        int i1 = rows1.size();
        int i2 = rows2.size();
 
        List<PFDataRow> temp = new ArrayList<PFDataRow>();
        String tempC = "";
        if (i1 > i2) {
            temp = rows1;
            rows1 = rows2;
            rows2 = temp;
            tempC = colName1;
            colName1 = colName2;
            colName2 = tempC;
        }
        for (PFDataRow r1 : rows1) {
            String value1 = r1.eval(colName1);
            for (PFDataRow r2 : rows2) {
                String value2 = r2.eval(colName2);
                if (value1.equals(value2)) {
                    List<PFDataColumn> cols = new ArrayList<PFDataColumn>();
                    for (PFDataColumn c : r1.getCol()) {
                        cols.add(c);
                    }
                    for (PFDataColumn c : r2.getCol()) {
                        cols.add(c);
                    }
                    PFDataRow rr = new PFDataRow(cols);
                    newRows.add(rr);
                }
            }
        }
        PFDataTable dt = new PFDataTable(newRows);
        return dt;
    }
 
    public static void outTable(PFDataTable dt) {
        for (PFDataRow r : dt.getRow()) {
            for (PFDataColumn c : r.getCol()) {
                System.out.print(c.getKey() + ":" + c.getValue() + "  ");
            }
            wl("");
        }
    }
 
    public static void wl(String s) {
        System.out.println(s);
    }
}
