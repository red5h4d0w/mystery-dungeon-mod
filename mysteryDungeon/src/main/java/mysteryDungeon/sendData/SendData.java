package mysteryDungeon.sendData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendData {
    public static Logger logger = LogManager.getLogger(SendData.class);
    public static void sendData(RunDetails run) {
        Gson gson = new Gson();
        String postUrl = "http://www.scarlet-tales-studio.com:44444/graphs/data-reception";
        com.badlogic.gdx.Net.HttpRequest request2 =  new HttpRequestBuilder().newRequest()
            .url(postUrl)
            .method("POST")
            .header("Content-Type", "application/json")
            .content(gson.toJson(run))
            .build();
        logger.info(request2.getContent());
        Gdx.net.sendHttpRequest(request2, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                logger.info("Metrics: http request response: " + httpResponse.getResultAsString());
            }
            
            public void failed(Throwable t) {
                t.printStackTrace();
                logger.info("Metrics: http request failed: " + t.toString());
            }
            
            public void cancelled() {
                logger.info("Metrics: http request cancelled.");
            }
        });
    }
}
