package sample;

import com.github.sarxos.webcam.Webcam;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class Camera {

    static int index;

    public static void startCamera(int imageIndex) throws InterruptedException, IOException {
        index = imageIndex;
        System.out.println("Start camera");
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        System.out.println("Pic!");
        BufferedImage image = webcam.getImage();

        // write it to byte array in-memory (jpg format)
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", b);

        // do whatever with the array...
        byte[] jpgByteArray = b.toByteArray();

        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(jpgByteArray);

        sendImage(byteArrayEntity);


        // save image to PNG file
        try {
            ImageIO.write(image, "PNG", new File("test.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void sendImage(ByteArrayEntity imgData) {
        System.out.println("Start emotion!");
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/emotion/v1.0/recognize");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "YOUR_PRIVATE_KEY");


            // Request body
            //StringEntity reqEntity = new StringEntity("{ \"url\": \"https://s-media-cache-ak0.pinimg.com/236x/51/d6/07/51d6074790d59f72d50a20dcd0e74e3b.jpg\" }");
            request.setEntity(imgData);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {

                String result = EntityUtils.toString(entity);
                double happiness = getHappinessValue(result);
                System.out.println("Happiness for image index: " + index + " - " + happiness);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static double getHappinessValue(String json) {
        int index = json.indexOf("happiness");

        String newString = json.substring(index + 11);
        String newnewString = "";

        for(int i=0;newString.charAt(i) != ',';i++) {
            newnewString = newnewString + newString.charAt(i);
        }

        try {
            double number = Double.parseDouble(newnewString);
            return number;
        } catch (Exception e) {
            return 0.0;
        }
    }

}

