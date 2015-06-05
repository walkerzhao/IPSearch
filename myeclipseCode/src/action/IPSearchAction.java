package action;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class IPSearchAction {

	 String ip;	         
	 
	 Map<String, Object> responseMap;   //查询的结果为json数据，struts2自动做序列化的工作
	 
	public Map<String, Object> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, Object> responseMap) {
		this.responseMap = responseMap;
	}	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		System.out.println("input ip is: "+ip);
		this.ip = ip;
	} 	 
	  
	public String search(){	     
		setOutputValue();
		return "success";	       
	}
	  
	  
   public String index(){
      return "success";
   }
	   	   
   public void setOutputValue() {
	   HttpClient httpclient = HttpClients.createDefault();
	   System.out.println("the input ip is" + ip);
		URI uri = null;
		try {
			uri = new URIBuilder()
				.setScheme("http")
				.setHost("ip.taobao.com")
				.setPath("/service/getIpInfo.php")
				.setParameter("ip", ip)
				.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpGet httpget = new HttpGet(uri);

		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode == HttpStatus.SC_OK) { //状态==200，返回成功
			String result = null;
			try {
				result = EntityUtils.toString(response.getEntity());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(result);
			JSONObject resultJson  = new JSONObject(result);
			int code      = resultJson.getInt("code");
			String country = null;
			String region = null;
			String city = null;
			String county = null;
			String isp = null;
			if(code == 0) {
				country = resultJson.getJSONObject("data").getString("country");  
				region = resultJson.getJSONObject("data").getString("region"); 
				city = resultJson.getJSONObject("data").getString("city"); 
				county = resultJson.getJSONObject("data").getString("county"); 
				isp = resultJson.getJSONObject("data").getString("isp"); 
				System.out.println("code is: "+ code + "country is: " + country + "area is "+region+"county is "+county+
						"isp is "+isp);	
			} 
			
			responseMap = new HashMap<String, Object>();
			responseMap.clear();
			responseMap.put("country", country);
			responseMap.put("region", region);
			responseMap.put("city", city);
			responseMap.put("county", county);
			responseMap.put("isp", isp);
			
		}
   }
}