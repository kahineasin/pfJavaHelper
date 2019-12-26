package pf.java.pfHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PFSqlConn {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    //String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student;";
    //String sql = "select * from GLType";
    public PFSqlConn(String url,String user,String password
    		//,Boolean isMySql
    		) throws SQLException {
        // 连接数据库
//    	if(isMySql) {
//    		try {
//				Class.forName("com.mysql.cj.jdbc.Driver");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	}
        conn = DriverManager.getConnection(url, user, password);
    }
    public <T> List<T> QueryList(Class<T> tClass,String sql) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 建立Statement对象
        stmt = conn.createStatement();
        /**
         * Statement createStatement() 创建一个 Statement 对象来将 SQL 语句发送到数据库。
         */
        // 执行数据库查询语句
        rs = stmt.executeQuery(sql);

        List<T> result=new ArrayList<T>();
        ResultSetMetaData md=rs.getMetaData();

        int count=md.getColumnCount();
        //1.获取字段
        //  1.1 获取所有字段 -- 字段数组
        //     可以获取公用和私有的所有字段，但不能获取父类字段
        Field[] fields = tClass.getDeclaredFields();
        List<Field> orderFields = new ArrayList<Field>();
        for(int i=1;i<=count;i++){
            for(Field field: fields){
                if(field.getName().equals(md.getColumnName(i))){
                    orderFields.add(field);
                }
            }
        }
        //result.add(String.valueOf(orderFields.size()));
        while (rs.next()) {
            //Object item=clazz.newInstance();
            T item=tClass.getDeclaredConstructor().newInstance();
            for(Field field: orderFields){
                String columnName=field.getName();
                field.set(item, rs.getObject(columnName));

                //Type typeName=field.getGenericType();
                //if(typeName.getTypeName()=="int"){
                //    field.set(item, rs.getInt(columnName));
                //}else{
                //    field.set(item, rs.getString(columnName));
                //}
            }
            result.add(item);

        }
        if (rs != null) {
            rs.close();
            rs = null;
        }
        if (stmt != null) {
            stmt.close();
            stmt = null;
        }
        if (conn != null) {
            conn.close();
            conn = null;
        }
        return result;
    }
    /**
     * @功能描述 执行一条select语句返回一张数据表，支持多表查询
     * @可能的错误
     * @作者 叶小钗
     * @修改说明
     * @修改人
     * @使用方法:
    String sql = "select * from kpxz where fbh=? order by kpxzsx asc";
    SqlParameter[] p = new SqlParameter[1];
    p[0] = new SqlParameter("int", pId);
    return db.getDataTable(sql, p);
     */
    public  PFDataTable GetDataTable(String sql, PFSqlParameter[] p) {
        //Connection conn = DB.createConn();

    	PreparedStatement ps = null;
        PFDataTable t = null;
        try {
        	ResultSet rs=null;
        	if(p!=null) {
        		ps = conn.prepareStatement(sql);
                AddSqlParameter(ps, p);
                rs = ps.executeQuery();
        	}else {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
        	}
            ResultSetMetaData rsmd = rs.getMetaData();
 
            List<PFDataRow> row = new ArrayList<PFDataRow>();// 表所有行集合
            List<PFDataColumn> col = null;// 行所有列集合
            PFDataRow r = null; // 单独一行
            PFDataColumn c = null;// 单独一列
            // 此处开始循环读数据，每次往表格中插入一行记录
            while (rs.next()) {
                // 初始化行集合，
 
                // 初始化列集合
                col = new ArrayList<PFDataColumn>();
                // 此处开始列循环，每次向一行对象插入一列
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(columnName);
                    // 初始化单元列
                    c = new PFDataColumn(columnName, value);
                    // 将列信息加入列集合
                    col.add(c);
                }
                // 初始化单元行
                r = new PFDataRow(col);
                // 将行信息降入行结合
                row.add(r);
            }
            // 得到数据表
            t = new PFDataTable(row);
            rs.close();
            rs = null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
        		if(p!=null) {
            		ps.close();
        		}
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //DB.close(ps);
            //DB.close(conn);
        }
        return t;
    }
/**
     * @功能描述 增加参数方法
     * @可能的错误 需要测试全局数据共享问题，以及可能会扩展参数类型
     * @作者 叶小钗
     * @修改说明
     * @修改人
     */
    private  void AddSqlParameter(PreparedStatement ps, PFSqlParameter[] p)
            throws SQLException {
        for (int j = 0; j < p.length; j++) {
            // wl(p[j].getValue() + "--" + p[j].getType() + "--" + j);
            if (p[j].getType().equals("int")) {
                ps.setInt(j + 1, p[j].getIntValue());
            }
            if (p[j].type.equals("String")) {
                ps.setString(j + 1, p[j].getValue());
            }
            if (p[j].type.equals("boolean")) {
                ps.setBoolean(j + 1, p[j].getBoolValue());
            }
            if (p[j].type.equals("Date")) {
                ps.setDate(j + 1, p[j].getDateValue());
            }
            if (p[j].type.equals("Blob")) {
                ps.setBlob(j + 1, p[j].getBlobValue());
            }
        }
    }
}
