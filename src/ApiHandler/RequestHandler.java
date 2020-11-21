package ApiHandler;

import JCode.trayHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.ProgressBar;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler {
    final static String url = "http://localhost:8080/";
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private static final String FIRST_KEY = "_embedded";
    private static Gson gson = new Gson();
    static OkHttpClient client = new OkHttpClient();

    //Checks status codes and displays appropriate errors
    public static String basicRequestHandler(Response response) throws IOException {
        int statusCode = response.code();

        switch (statusCode) {
            case 200:       //CREATED
            case 201: {     //OK
                return "OK";
            }
            case 403: {     //ACCESS DENIED
                String message = "ACCESS_DENIED";
                return message;
            }
            case 404: {     //NOT FOUND
                String message = "Not Found";
                return message;
            }
            case 500: {     //INTERNAL SERVER ERROR
                String message = "An Internal Error Occured";
                return message;
            }
            default: {
                String message = "Something went wrong :(";
                return message;
            }
        }
    }

    //Checks status codes then converts into list
    public static List listRequestHandler(Response response, Class className) throws IOException, JSONException {
        String result = basicRequestHandler(response);
        String jsonResponse;

        if (result.contains("OK")) {
            jsonResponse = response.body().string();
            return convertJSONtoArray(jsonResponse, className);
        } else {
            return null;
        }
    }

    //~Very proud of this function
    //~What a beautiful peace of code!
    //~Maashaallah
    public static List convertJSONtoArray(String jsonString, Class className) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        JSONObject obj;

        if (json.has(FIRST_KEY))
            obj = json.getJSONObject(FIRST_KEY);
        else
            return new ArrayList();

        JSONArray array = obj.getJSONArray(obj.keys().next());  //Gets the immediate next() key which identifies the array

        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            Object object = gson.fromJson(array.get(i).toString(), className);
            list.add(object);
        }
        return list;
    }

    public static Object objectRequestHandler(Response response, Class className) throws IOException {
        String result = basicRequestHandler(response);
        String jsonResponse;
        if (result.contains("OK")) {
            jsonResponse = response.body().string();
            return gson.fromJson(jsonResponse, className);
        } else {
            return null;
        }
    }

    public static void handleRequestFailure(final Exception e, final ProgressBar progressBar) {
        e.printStackTrace();

    }


    public static Response run(String endPoint) throws IOException {
        Request request = new Request.Builder()
                .url(url + endPoint)
                .build();
        Response response = client.newCall(request).execute();
        ResponseBody body = response.body();
        try {

        } finally {
            body.close();
        }
        return client.newCall(request).execute();
    }

    public static boolean checkUpdate(String endPoint) throws IOException {
        InputStream inputStream = null;
        ResponseBody body = null;
        try {
            Request request = new Request.Builder()
                    .url(url + endPoint)
                    .build();
            body = client.newCall(request).execute().body();
            inputStream = client.newCall(request).execute().body().byteStream();

        } catch (ConnectException e) {
            trayHelper tray = new trayHelper();
            tray.displayNotification("Error", "Server isn't Running");
            return false;
        }finally {
            if (body!=null){
                body.close();
            }

        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();

        boolean value = Boolean.parseBoolean(response.toString());
        return value;
    }

    public static String writeJSON(Object object) throws IOException, IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(object);
    }

    public static String downloadZipFile(String endPoint, String destFile) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + endPoint).build();
        Response response = client.newCall(request).execute();

        FileOutputStream fos = new FileOutputStream(destFile);
        fos.write(response.body().bytes());
        fos.close();
        return destFile;
    }

    public static String postWithFile(String endPoint, String json, List<File> fileList) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (fileList == null) {
            builder.addPart(Headers.of("Content-Disposition",
                    "form-data; name=\"email\""), body);
        } else {
            builder.addPart(Headers.of("Content-Disposition",
                    "form-data; name=\"email\""), body);
            for (File file : fileList) {
                builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            }
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url + endPoint)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    public static String post(String endpoint, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url + endpoint)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
