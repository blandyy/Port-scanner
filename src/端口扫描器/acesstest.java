/**
*@author created by 穆洪桥
*@date 2017年7月10日---下午4:21:35
*@problem
*@answer
*@action
*/

package 端口扫描器;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class acesstest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection con = DriverManager.getConnection("jdbc:Access:///d:/Database1.accdb");
			Statement stat =con.createStatement();
			String sql = "select * from porttest where id='80'";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
	               System.out.print(rs.getString(1));
	               System.out.print(rs.getString(2));
	               System.out.print(rs.getString(3));
	           }
			rs.close();
			stat.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

