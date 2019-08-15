// Para poder usar el mock server tenemos que importar de manera estatica una libreria
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import com.google.gson.Gson;
// import com.google.gson.stream.JsonReader;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;

import java.io.*;
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

    static MockServerClient mockServer = startClientAndServer(8083); // Esta la estoy llamando estatica y tengo que ponerle el puerto
    // Metodo general para crear lo que responde en todas las expectativas
    public static void consulta(String method, String path, int statusCode, String content, String body, long delay) {

        mockServer.when(
                request().withMethod(method).withPath(path) // Vamos a responder un request que venga con un put o un post, etc.

        ).respond(
                response()
                        .withStatusCode(statusCode)
                        .withHeader(new Header("Content-Type", content))
                        .withBody(body)
                        .withDelay(new Delay(TimeUnit.MILLISECONDS, delay))
        );
        return;

    }

    public static void main(String[] args) throws FileNotFoundException {

        try {

            URL url = new URL("https://api.mercadolibre.com/sites"); // Siempre que instancio un objeto java que no es una interfaz lo instancio con new
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

                    //Operador<Site> operator = new Operador<Site>();

                    //Site.criteria = Site.Criteria.NAME;
                    //Operador.ordenar(sites);

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

/*
        try {
        URL url = new URL("https://api.mercadolibre.com/sites"); // Siempre que instancio un objeto java que no es una interfaz lo instancio con new

        try {
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
        // urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0"); // Para q te pusiera bonito al json

        if (urlConnection instanceof HttpURLConnection) {
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        Gson gson = new Gson();

        Site[] sites = gson.fromJson(in, Site[].class);

        Operador<Site> operator = new Operador<Site>();

        Site.criteria = Site.Criteria.NAME;
        Operador.ordenar(sites);


        consulta("GET", "/sites", 200, "application/json", gson.toJson(sites), 1000);

        } else {
        System.out.println("URL inválida");
        return;
        }

        }

        } catch (IOException e) {

        e.printStackTrace();

        }


        } catch (MalformedURLException exception) {

        System.out.println(exception.getMessage());

        }
        */

/*
        Gson gson = new Gson();
        Site site = new Site("MLA", "Argentina");
        JsonReader reader = new JsonReader(new FileReader("/Users/aziemeckibur/IdeaProjects/mock/src/main/java/sites.json"));
        Site[] sites = gson.fromJson(reader, Site[].class);
        consulta("GET", "/sites", 200, "application/json", gson.toJson(sites), 1000);
        JsonReader readerCategories = new JsonReader(new FileReader("/Users/aziemeckibur/IdeaProjects/mock/src/main/java/MLA.json"));
        Site[] categories = gson.fromJson(readerCategories, Site[].class);
        consulta("GET", "/sites/.*categories", 200, "application/json", gson.toJson(categories), 1000);
        consulta("GET", "/sites/MLA/categories/.*", 200, "application/json", gson.toJson(categories[1]), 1000);
        consulta("GET", "/sites/.*", 200, "application/json", gson.toJson(sites[1]), 1000);
*/