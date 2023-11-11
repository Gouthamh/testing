package assure.RestClient;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class restClient {

	public CloseableHttpResponse getMethod(String url) throws IOException {
		CloseableHttpClient  httpclient = HttpClients.createDefault();
		HttpGet getmethod = new HttpGet(url);
		
			CloseableHttpResponse closerresposne = httpclient.execute(getmethod);
			closerresposne.getCode();
			return closerresposne;
			
		
			// TODO Auto-generated catch block
			
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
