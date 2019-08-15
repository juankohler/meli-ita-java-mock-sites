import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import com.google.gson.Gson;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;

import java.util.concurrent.TimeUnit;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpRequest.request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Server {

    static MockServerClient mockServer = startClientAndServer(8083);
    public static void consulta(String method, String path, int statusCode, String content, String body, long delay) {

        mockServer.when(
                request().withMethod(method).withPath(path)

        ).respond(
                response()
                        .withStatusCode(statusCode)
                        .withHeader(new Header("Content-Type", content))
                        .withBody(body)
                        .withDelay(new Delay(TimeUnit.MILLISECONDS, delay))
        );
        return;

    }

    public static void main(String[] args) {

        try {

            URL url = new URL("https://api.mercadolibre.com/sites");
            URL url_categories = new URL("https://api.mercadolibre.com/sites/MLA/categories");

            try {

                URLConnection urlConnection = url.openConnection();
                URLConnection urlConnection_categories = url_categories.openConnection();

                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection_categories.setRequestProperty("Accept", "application/json");

                if ( (urlConnection instanceof HttpURLConnection) && (urlConnection_categories instanceof HttpURLConnection)) {

                    HttpURLConnection connection = (HttpURLConnection) urlConnection;
                    HttpURLConnection connection_categories = (HttpURLConnection) urlConnection_categories;

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    BufferedReader in_categories = new BufferedReader(new InputStreamReader(connection_categories.getInputStream()));

                    Gson gson = new Gson();

                    Site[] sites = gson.fromJson(in, Site[].class);
                    Site[] sites_categories = gson.fromJson(in_categories, Site[].class);

                    consulta("GET", "/sites", 200, "application/json", gson.toJson(sites), 1000);
                    consulta("GET", "/sites/.*/categories", 200, "application/json", gson.toJson(sites_categories), 1000);

                } else {
                    System.out.println("URL inválida");
                    return;
                }

        } catch (IOException e) {

            e.printStackTrace();

        }


    } catch (MalformedURLException exception) {

        System.out.println(exception.getMessage());

    }
    }
}
