package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Buyer;

@Path("/Buyer")
public class BuyerService {
	
	Buyer buyerObj = new Buyer();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBuyer() {
		return buyerObj.readBuyers();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBuyer(@FormParam("bName") String bName,
			
	 @FormParam("bAddress") String bAddress,
	 @FormParam("bEmail") String bEmail,
	 @FormParam("bDate") String bDate,
	 @FormParam("pNo") String pNo)
	{
		String output="";
		if(bName.length()==0 || bAddress.length()==0 || bEmail.length()==0 || bDate.length()==0 || pNo.length()==0) {
			return "You can't Insert Fields Empty..!";
		}else
		output = buyerObj.insertBuyer(bName, bAddress, bEmail, bDate, pNo);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBuyer(String buyerData)
	{
	//Convert the input string to a JSON object
	 JsonObject buyObject = new JsonParser().parse(buyerData).getAsJsonObject();
	//Read the values from the JSON object
	 String bID = buyObject.get("bID").getAsString();
	 String bName = buyObject.get("bName").getAsString();
	 String bAddress = buyObject.get("bAddress").getAsString();
	 String bEmail = buyObject.get("bEmail").getAsString();
	 String bDate = buyObject.get("bDate").getAsString();
	 String pNo = buyObject.get("pNo").getAsString();
	 String output = buyerObj.updateBuyers(bID, bName, bAddress, bEmail, bDate, pNo);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBuyer(String buyerData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(buyerData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String bID = doc.select("bID").text();
	 String output = buyerObj.deleteBuyer(bID);
	return output;
	}
	

}
