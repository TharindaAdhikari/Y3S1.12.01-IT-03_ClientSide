package com;

import model.GadgetProduct;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Product")

public class ServiceProduct {
	
	GadgetProduct ProductObj = new GadgetProduct();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProduct() {
		return ProductObj.readProducts();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProduct(@FormParam("pName") String pName, 
			@FormParam("pDate") String pDate,
			@FormParam("pPrice") String pPrice,
			@FormParam("pDes") String pDes) {
		String output = ProductObj.insertProduct(pName, pDate, pPrice, pDes);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateProduct(String productData) {
		// Convert the input string to a JSON object
		JsonObject ProObject = new JsonParser().parse(productData).getAsJsonObject();

		// Read the values from the JSON object
		String pId = ProObject.get("pId").getAsString();
		String pName = ProObject.get("pName").getAsString();
		String pDate = ProObject.get("pDate").getAsString();
		String pPrice = ProObject.get("pPrice").getAsString();
		String pDes = ProObject.get("pDes").getAsString();

		String output = ProductObj.updateProduct(pId, pName, pDate, pPrice, pDes);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProduct(String productData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(productData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String pId = doc.select("pId").text();
		String output = ProductObj.deleteProduct(pId);
		return output;
	}
	

}
