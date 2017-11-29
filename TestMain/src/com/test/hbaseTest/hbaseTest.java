package com.test.hbaseTest;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
/**
 * 
* Title: hbaseTest
* Description: HBase 相关测试
* Version:1.0.0  
* @author pancm
* @date 2017年11月23日
 */
public class hbaseTest {
	/** hadoop 连接 */
   private static Configuration conf=null;
   /**hbase 连接 */
   private static Connection con=null;
   /** 会话 */
   private static Admin admin = null;
   
   // 初始化连接
   static {
	   // 获得配制文件对象
       conf = HBaseConfiguration.create(); 
       // 设置配置参数
		conf.set("hbase.zookeeper.quorum", "192.169.0.25");
//		conf.set("hbase.zookeeper.quorum", "192.169.1.144");
		conf.set("hbase.zookeeper.property.clientPort", "2181");  
       try {
           con = ConnectionFactory.createConnection(conf);// 获得连接对象
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
	
	
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param columnFamily 列族
	 */
	public static void creatTable(String tableName,String [] columnFamily){
		// 创建表名对象
		TableName tn = TableName.valueOf(tableName);
		// a.判断数据库是否存在
		try {
			//获取会话
			admin = con.getAdmin();
			if (admin.tableExists(tn)) {
				System.out.println(tableName+" 表存在，删除表....");
				// 先使表设置为不可编辑
				admin.disableTable(tn);
				// 删除表
				admin.deleteTable(tn);
				System.out.println("表删除成功.....");
			}
			// 创建表结构对象
			HTableDescriptor htd = new HTableDescriptor(tn);
			for(String str:columnFamily){
				// 创建列族结构对象
				HColumnDescriptor hcd = new HColumnDescriptor(str);
				htd.addFamily(hcd);
			}
			// 创建表
			admin.createTable(htd);
			System.out.println(tableName+" 表创建成功！");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	/**
	 * 数据插入或更新
	 * @param tableName 	表名
	 * @param rowKey   		行健  (主键)
	 * @param family  		列族
	 * @param qualifier   	列
	 * @param value    		存入的值
	 * @return
	 */
	public static void insert(String tableName, String rowKey,
            String family, String qualifier, String value){
		 Table t=null;
		try {
	             t = con.getTable(TableName.valueOf(tableName));
	            Put put = new Put(Bytes.toBytes(rowKey));
	            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier),
	 	                    Bytes.toBytes(value));
	           t.put(put);
	          System.out.println(tableName+" 更新成功!");
	        } catch (IOException e) {
	        	System.out.println(tableName+" 更新失败!");
	            e.printStackTrace();
	        } finally {
	            close();
	       }
	}
	
	/**
	 * 查询该表中的所有数据
	 * @param tableName 表名
	 */
	public static void select(String tableName){
		Table t =null;
		 try {
			 t = con.getTable(TableName.valueOf(tableName));
			// 读取操作	
			Scan scan = new Scan();
			// 得到扫描的结果集
			ResultScanner rs = t.getScanner(scan);
			for (Result result : rs) {
				// 得到单元格集合
				List<Cell> cs = result.listCells();
				for (Cell cell : cs) {
					// 取行健
					String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
					// 取到时间戳
					long timestamp = cell.getTimestamp();
					// 取到族列
					String family = Bytes.toString(CellUtil.cloneFamily(cell));
					// 取到修饰名
					String qualifier = Bytes.toString(CellUtil
							.cloneQualifier(cell));
					// 取到值
					String value = Bytes.toString(CellUtil.cloneValue(cell));

					System.out.println("通过表名查询所有:" + " ===> rowKey : " + rowKey
							+ ",  timestamp : " + timestamp + ", family : "
							+ family + ", qualifier : " + qualifier
							+ ", value : " + value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据条件明细查询
	 * @param tableName 	表名
	 * @param rowKey   		行健  (主键)
	 * @param family  		列族
	 * @param qualifier   	列
	 */
	public static void select(String tableName,String rowKey,String family, String qualifier){
		System.out.println();
		Table t =null;
		 try {
			 t = con.getTable(TableName.valueOf(tableName));
			// get
			Get get = new Get(Bytes.toBytes(rowKey));
			if(null!=family&&family.length()>0){
				if(null!=qualifier&&qualifier.length()>0){
					get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
				}else{
					get.addFamily(Bytes.toBytes(family));
				}
			}
			Result r = t.get(get);
			List<Cell> cs = r.listCells();
				for (Cell cell : cs) {
					String rowKey1 = Bytes.toString(CellUtil.cloneRow(cell)); // 取行键
					long timestamp = cell.getTimestamp(); // 取到时间戳
					String family1 = Bytes.toString(CellUtil.cloneFamily(cell)); // 取到族列
					String qualifier1 = Bytes.toString(CellUtil.cloneQualifier(cell)); // 取到修饰名
					String value = Bytes.toString(CellUtil.cloneValue(cell)); // 取到值
					System.out.println("通过条件查询:"+" ===> rowKey : " + rowKey1
							+ ",  timestamp : " + timestamp + ", family : "
							+ family1 + ", qualifier : " + qualifier1 + ", value : "
							+ value);
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 连接关闭
	 */
    public static void close() {
    	try {
    		if(admin!=null){
			   admin.close();
    		}
//    		 if (con != null) {
//    			 con.close();
//    		 }
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    

    
    
		public static void main(String[] args) {
//			test();
			test1();
		}



	/**
	 * 一些测试
	 */
	private static void test() {
		String tableName1="t_student",tableName2="t_class",tableName3="t_grade",tableName4="t_subject";
		String []columnFamily1={"st1","st2"};
		String []columnFamily2={"cl1","cl2"};
		String []columnFamily3={"gr1","gr2"};
		String []columnFamily4={"su1","su2"};
//		creatTable(tableName1,columnFamily1);
//		creatTable(tableName2,columnFamily2);
//		creatTable(tableName3,columnFamily3);
//		creatTable(tableName4,columnFamily4);
		String rowKey1="rowkey1",rowKey2="rowkey2",rowKey3="rowkey3",rowKey4="rowkey4";
		String column1="column1",column2="column2",column3="column3",column4="column4"; 
		String value1="value1",value2="value2",value3="value3",value4="value4";
		
//		insert(tableName1,rowKey1,columnFamily1[0],column1,value1);
//		insert(tableName1,rowKey1,columnFamily1[0],column2,value2);
//		insert(tableName1,rowKey1,columnFamily1[1],column3,value3);
//		insert(tableName1,rowKey1,columnFamily1[1],column4,value4);
		select(tableName1);
		select(tableName1,rowKey1,"","");
		select(tableName1,rowKey1,columnFamily1[1],"");
		select(tableName1,rowKey1,columnFamily1[0],column1);
	}
	
	
	
	/**
	 * 一些测试
	 */
	private static void test1() {
		//定义一个大表
		String tableName="t";
		//将关系型数据的表对应成列族 
		String []columnFamily={"t_student","t_class","t_grade","t_subject"};
// 		creatTable(tableName,columnFamily);
        //设置列键(主键，可以一对多)
 		String rowKey1="1",rowKey2="2",rowKey3="3",rowKey4="4";
		//将关系型数据库表字段对应成列
 		String id="id",name="name",age="age"; 
		//数据
 		String id1="1001",name1="张三",age1="18";
 		String id2="1002",name2="李四",age2="20";
		
		insert(tableName,rowKey1,columnFamily[0],id,id1);
		insert(tableName,rowKey1,columnFamily[0],name,name1);
		insert(tableName,rowKey1,columnFamily[0],age,age1);
		insert(tableName,rowKey2,columnFamily[0],id,id2);
		insert(tableName,rowKey2,columnFamily[0],name,name2);
		insert(tableName,rowKey2,columnFamily[0],age,age2);
	
		select(tableName);
		select(tableName,rowKey1,"","");
		select(tableName,rowKey1,columnFamily[0],"");
		select(tableName,rowKey1,columnFamily[0],id);
	}
}
