package model;

import java.sql.*;

public class Buyer {
	
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget", "root", "");
			// For testing
			System.out.println("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readBuyers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Buyer Name</th>" + "<th>Buyer Address</th><th>Buyer Email</th>"
					+ "<th>Date</th>" + "<th>Buyer Phone No</th>" + "<th>Update</th><th>Remove</th></tr>";
			String query = "select * from buyer1";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String bID = Integer.toString(rs.getInt("bID"));
				String bName = rs.getString("bName");
				String bAddress = rs.getString("bAddress");
				String bEmail = rs.getString("bEmail");
				String bDate = rs.getString("bDate");
				String pNo = rs.getString("pNo");

				// Add a row into the html table
				output += "<tr><td>" + bName + "</td>";
				output += "<td>" + bAddress + "</td>";
				output += "<td>" + bEmail + "</td>";
				output += "<td>" + bDate + "</td>";
				output += "<td>" + pNo + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' "
						+ " type='button' value='Update' class='btnUpdate btn btn-secondary' data-bid='" + bID
						+ "'></td>" + "<td>" + "<input name='btnRemove' "
						+ " type='button' value='Remove' class='btnRemove btn btn-danger' data-bid='" + bID + "'>"
						+ "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertBuyer(String bname, String baddress, String bemail, String bdate, String pno) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " insert into buyer1(`bID`,`bName`,`bAddress`,`bEmail`,`bDate`,`pNo`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, bname);
			preparedStmt.setString(3, baddress);
			preparedStmt.setString(4, bemail);
			preparedStmt.setString(5, bdate);
			preparedStmt.setString(6, pno);
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newItems = readBuyers();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Update 
	public String updateBuyers(String bid, String bname, String baddress, String bemail, String bdate, String pno) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE buyer1 SET bName=?,bAddress=?,bEmail=?,bDate=?,pNo=?" + "WHERE bID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, bname);
			preparedStmt.setString(2, baddress);
			preparedStmt.setString(3, bemail);
			preparedStmt.setString(4, bdate);
			preparedStmt.setString(5, pno);
			preparedStmt.setInt(6, Integer.parseInt(bid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newItems = readBuyers();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteBuyer(String bID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from buyer1 where bID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(bID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readBuyers();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
