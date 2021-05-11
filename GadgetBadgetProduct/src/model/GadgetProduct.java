package model;

import java.sql.*;

public class GadgetProduct {
	
	public Connection connect()
	{
	Connection con = null;
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget","root", "");
	//For testing
	System.out.println("Successfully connected");
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return con;
	}
	
	
	public String insertProduct(String pname, String pdate, String price, String pdes)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{
	return "Error while connecting to the database";
	}
	// create a prepared statement
	String query = " insert into product1(`pId`, `pName`, `pDate`, `pPrice`, `pDes`)" + " values ( ?, ?, ?, ?, ?)";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, 0);
	preparedStmt.setString(2, pname);
	preparedStmt.setString(3, pdate);
	preparedStmt.setString(4, price);
	preparedStmt.setString(5, pdes);
	//execute the statement
	preparedStmt.execute();
	con.close();
	
	String newItems = readProducts(); 
	 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
	 
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
	System.err.println(e.getMessage());
	}
	return output;
	}
	
	
	
	public String readProducts()
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{
	return "Error while connecting to the database for reading.";
	}
	// Prepare the html table to be displayed
	output = "<table border='1'><tr><th>Product Name</th>"
	+"<th>Date</th><th>Price</th>"
	+ "<th>Description</th>"
	+ "<th>Update</th><th>Remove</th></tr>";
	String query = "select * from product1";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	
	// iterate through the rows in the result set
	while (rs.next())
	{
		String pId = Integer.toString(rs.getInt("pId"));
		String pName = rs.getString("pName");
		String pDate = rs.getString("pDate");
		String pPrice = rs.getString("pPrice");
		String pDes = rs.getString("pDes");

	
	// Add a row into the html table
		output += "<tr><td>" + pName + "</td>";
		output += "<td>" + pDate + "</td>";
		output += "<td>" + pPrice + "</td>";
		output += "<td>" + pDes + "</td>";
		
	
	// buttons
	output += "<td><input name='btnUpdate' "
	+ " type='button' value='Update' class='btnUpdate btn btn-secondary' data-pid='" + pId + "'></td>"
	+ "<td>"
	+ "<input name='btnRemove' "
	+ " type='button' value='Remove' class='btnRemove btn btn-danger' data-pid='" + pId + "'>"
	+ "</td></tr>";
	}
	con.close();
	// Complete the html table
	output += "</table>";
	}
	catch (Exception e)
	{
	output = "Error while reading the items.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	
	
	public String deleteProduct(String pId)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{
	return "Error while connecting to the database for deleting.";
	}
	// create a prepared statement
	String query = "delete from product1 where pId=?";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, Integer.parseInt(pId));
	// execute the statement
	preparedStmt.execute();
	con.close();
	String newItems = readProducts(); 
	 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
	}
	catch (Exception e)
	{
	output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
	System.err.println(e.getMessage());
	}
	return output;
	}
	
	//Update.....
	public String updateProduct(String pid, String pname, String pdate, String price, String pdes) {
		String output = "";

		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			
			// create a prepared statement
			String query = "UPDATE product1 SET pName=?,pDate=?,pPrice=?,pDes=? WHERE pId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, pname);
			preparedStmt.setString(2, pdate);
			preparedStmt.setString(3, price);
			preparedStmt.setString(4, pdes);
			preparedStmt.setInt(5, Integer.parseInt(pid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newItems = readProducts(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
			 
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}


}
