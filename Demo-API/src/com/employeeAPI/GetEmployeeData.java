package com.employeeAPI;
/**
 * 
@author
Radoslaw Choromanski Id nr-14020101
 */
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange; 
import com.sun.net.httpserver.HttpHandler; 
import com.sun.net.httpserver.HttpServer; 

public class GetEmployeeData {
	
	static HttpServer server;
	
	public static void main(String[] args) throws Exception {
        server = HttpServer.create(new InetSocketAddress(8005), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/info", new InfoHandler());    // to get json response of all data
        server.createContext("/get", new GetHandler()); 
        server.createContext("/add", new AddEmpData());      //to add employee from web service
        server.createContext("/addwebdata", new WebData());  //to add employee from web interface
		server.createContext("/success", new SuccessHandler()); //if adding employee returns success
        server.setExecutor(null); // creates a default executor
        server.start();
    }
	
	static class WebData implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
        	{
				//output HTML form 
				t.sendResponseHeaders(200, 0);
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(t.getResponseBody()));
				out.write("<html><head>"
						+ "<title>Add Employee Data</title>"
						+ "</head><body><form method=\"GET\" action=\"/success\">");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >Name</label><input type='text' name=\"name\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >Salary</label><input type='number' name=\"salary\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >Gender</label><input type='text' name=\"gender\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >NiN</label><input type='text' name=\"nin\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >Date Of Birth</label><input type='text' name=\"dob\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >Address</label><input type='text' name=\"address\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >Postcode</label><input type='number' name=\"postcode\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >Email</label><input type='text' name=\"email\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='width: 60%; margin:20px auto 0;'>");
				out.write("<label style='margin-right: 10px; margin-bottom:5px; display: block;' >Title</label><input type='text' name=\"title\" style='width: 100%; display: block; padding: 8px 0 8px 15px;'></div>");
				out.write("<div style='display: table; margin: 0 auto;'><input type='submit' value='submit' style='margin-top: 20px; cursor: pointer; padding: 8px 20px; border: none; background: #222; color: #fff;'></div>");
				out.write("</form></body></html>");
				out.close();
			}
        }
    }
	
	
	static class SuccessHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			StringBuilder response = new StringBuilder();
			Map <String,String>parms = GetEmployeeData.queryToMap(httpExchange.getRequestURI().getQuery());
			Gson reply = new Gson();
			HashMap<String, String> map = new HashMap<String, String>();
			//start
			if(parms.get("name") == null ||
					parms.get("salary") == null ||
					parms.get("gender") == null ||
					parms.get("nin") == null ||
					parms.get("dob") == null ||
					parms.get("address") == null ||
					parms.get("postcode") == null ||
					parms.get("email") == null ||
					parms.get("title") == null)
			{
				map.put("success", "fail");
				map.put("message", "all values are required...");
			}
			else
			{
				map.put("message", "data inserted successfully...");
				String res_str = ServerDB.AddEmp(new person(parms.get("name").toString(), parms.get("gender").toString(), parms.get("nin").toString(), parms.get("dob").toString(), parms.get("address").toString(), parms.get("postcode").toString(), parms.get("email").toString(), parms.get("title").toString(), parms.get("salary").toString()));
				map.put("success", "true");
			}
			String jsonStr = reply.toJson(map);
			GetEmployeeData.writeResponse(httpExchange, jsonStr);    
			}
        
    }
	
	static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "/get";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
	
	static class AddEmpData implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
			StringBuilder response = new StringBuilder();
			Map <String,String>parms = GetEmployeeData.queryToMap(httpExchange.getRequestURI().getQuery());
			Gson reply = new Gson();
			HashMap<String, String> map = new HashMap<String, String>();
			//start
			if(parms.get("name") == null ||
					parms.get("salary") == null ||
					parms.get("gender") == null ||
					parms.get("nin") == null ||
					parms.get("dob") == null ||
					parms.get("address") == null ||
					parms.get("postcode") == null ||
					parms.get("email") == null ||
					parms.get("title") == null)
			{
				map.put("success", "fail");
				map.put("message", "all values are required...");
			}
			else
			{
				
				map.put("message", "data inserted successfully...");
				
				String res_str = ServerDB.AddEmp(new person(parms.get("name").toString(), parms.get("gender").toString(), parms.get("nin").toString(), parms.get("dob").toString(), parms.get("address").toString(), parms.get("postcode").toString(), parms.get("email").toString(), parms.get("title").toString(), parms.get("salary").toString()));
				map.put("success", "true");
			}
			String jsonStr = reply.toJson(map);
			
			
			//end
//			response.append("<html><body>");
//			response.append("Forename : " + parms.get("firstname") + "<br/>");
//			response.append("Surname : " + parms.get("lastname") + "<br/>");
//			response.append("</body></html>");
			GetEmployeeData.writeResponse(httpExchange, jsonStr);    
			}
    }
	
	static class InfoHandler implements HttpHandler {
		public void handle(HttpExchange httpExchange) throws IOException {
			ArrayList<person> ar = new ArrayList<>();
			ArrayList<HashMap<String,String>> outer = new ArrayList<HashMap<String,String>>();
			try {
				ar = ServerDB.getAllCustomer();
				for(int i = 0; i<ar.size(); i++)
				{
					HashMap<String,String> inner = new HashMap<String,String>();
					inner.put("name", ar.get(i).getName());
					inner.put("address", ar.get(i).getAddress());
					inner.put("gender", ar.get(i).getGender());
					inner.put("nin", ar.get(i).getNin());
					inner.put("dob", ar.get(i).getDob());
					inner.put("postal", ar.get(i).getPosotcode());
					inner.put("title", ar.get(i).getTitle());
					inner.put("email", ar.get(i).getEmail());
					inner.put("salary", ar.get(i).getSalary());
					outer.add(inner);
					//System.out.println(ar.get(i).getName());
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String Jsn = new Gson().toJson(outer);
			GetEmployeeData.writeResponse(httpExchange, Jsn.toString());
			}
		}
	
	static class GetHandler implements HttpHandler {
		public void handle(HttpExchange httpExchange) throws IOException {
			StringBuilder response = new StringBuilder();
			Map <String,String>parms = GetEmployeeData.queryToMap(httpExchange.getRequestURI().getQuery());      
			response.append("<html><body>");
			response.append("Forename : " + parms.get("firstname") + "<br/>");
			response.append("Surname : " + parms.get("lastname") + "<br/>");
			response.append("</body></html>");
			GetEmployeeData.writeResponse(httpExchange, response.toString());    
			}
		} 

	 public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
		 httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		 httpExchange.getResponseHeaders().set("Content-Type", "Application/JSON");
		 httpExchange.sendResponseHeaders(200, response.length());
		 OutputStream os = httpExchange.getResponseBody();
		 os.write(response.getBytes());
		 os.close();
		 }
	 
	 public static Map<String, String> queryToMap(String query){
		 Map<String, String> result = new HashMap<String, String>();
		 for (String param : query.split("&"))
		 {
			 String pair[] = param.split("=");
			 if (pair.length>1) 
			 {
				 result.put(pair[0], pair[1]);
				 }else{
					 result.put(pair[0], "");
					 }
			 }
		 return result;
		 }
	 
}
