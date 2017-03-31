package com.employeeAPI;
/**
 * 
@author
Radoslaw Choromanski Id nr-14020101
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ServerDB {
	
	static Connection connection = null;

	public static Connection getConnection() {
		if (connection != null)
			return connection;
		else {
			try {
				Class.forName("org.sqlite.JDBC");
				connection=DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connection;
		}
		}

	
	public static String AddEmp(person emp)
	{
		connection = ServerDB.getConnection();

		// the mysql insert statement
	      String query = " insert into employee (name, email, title, salary, gender, dob, address, postcode, nin)"
	        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	      System.out.println(query);
	      try{
	      // create the mysql insert preparedstatement
	      PreparedStatement preparedStmt = connection.prepareStatement(query);
	      preparedStmt.setString (1, emp.getName());
	      preparedStmt.setString (2, emp.getEmail());
	      preparedStmt.setString(3, emp.getTitle());
	      preparedStmt.setString(4, emp.getSalary());
	      preparedStmt.setString(5, emp.getGender());
	      preparedStmt.setString(6, emp.getDob());
	      preparedStmt.setString(7, emp.getAddress());
	      preparedStmt.setString(8, emp.getPostcode());
	      preparedStmt.setString(9, emp.getNin());

	      
	      // execute the preparedstatement
	      if(preparedStmt.execute())
	      {
	    	  System.out.println("this fail");
	    	  //connection.close();
	    	  return "success";
	      }
	      else
	      {
	    	  //connection.close();
	    	  return "fail";
	      }
	      }catch(SQLException e)
	      {
	    	  return "fail";
	      }
	      
	      
	}
	
	//method to get list of all amployeees 
	public static ArrayList<person> getAllCustomer() throws ClassNotFoundException, SQLException {
		connection = ServerDB.getConnection();
		ArrayList<person> tileList = new ArrayList<person>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from employee");
			while(rs.next()) {
				person tiledetail=new person();
				tiledetail.setName(rs.getString("name"));
				tiledetail.setAddress(rs.getString("address"));
				tiledetail.setNin(rs.getString("nin"));
				tiledetail.setDob(rs.getString("dob"));
				tiledetail.setGender(rs.getString("gender"));
				tiledetail.setPostcode(rs.getString("postcode"));
				tiledetail.setSalary(rs.getString("salary"));
				tiledetail.setEmail(rs.getString("email"));
				tiledetail.setTitle(rs.getString("title"));
				tileList.add(tiledetail);
				//System.out.println("Json: "+ rs.getString("address"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return tileList;
	}

}
