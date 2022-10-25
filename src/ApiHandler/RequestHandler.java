package ApiHandler;

import JCode.FileHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import objects.*;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler {
    private static String port = ":4040/";
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
    public static List jsonListRequestHandler(Response response) throws IOException, JSONException {
        String result = basicRequestHandler(response);
        String jsonResponse;

        if (result.contains("OK")) {
            jsonResponse = response.body().string();
            final ObjectMapper objectMapper = new ObjectMapper();
            List<Email> langList = objectMapper.readValue(jsonResponse, new TypeReference<List<Email>>(){});
            return langList;
        } else {
            return null;
        }
    }
    public static List jsonListUserRequestHandler(Response response) throws IOException, JSONException {
        String result = basicRequestHandler(response);
        String jsonResponse;

        if (result.contains("OK")) {
            jsonResponse = response.body().string();
            final ObjectMapper objectMapper = new ObjectMapper();
            List<Users> langList = objectMapper.readValue(jsonResponse, new TypeReference<List<Users>>(){});
            return langList;
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

    public static Response checkIp(String endPoint,String ip) throws IOException {
        try {
            Request request = new Request.Builder()

                    .url("http://" + ip + port + endPoint)
                    .build();
            return client.newCall(request).execute();
        } catch (ConnectException connectException) {
            connectException.printStackTrace();
            showAlertDialog();

        } catch (SocketTimeoutException exception) {
            exception.getLocalizedMessage();
            showAlertDialog();
        }
        return null;
    }
    public static Response run(String endPoint) throws IOException {
        try {
            Request request = new Request.Builder()

                    .url("http://" + FileHelper.getNetworkDetails().getHost() + port + endPoint)
                    .build();
            return client.newCall(request).execute();
        } catch (ConnectException | SocketTimeoutException connectException) {
            connectException.getLocalizedMessage();
            showAlertDialog();

        }
        return null;
    }

    private static void showAlertDialog() {
        Alert alert2 = new Alert(Alert.AlertType.ERROR, "Cannot Connect to the Server!",
                ButtonType.OK);
        alert2.showAndWait();

        if (alert2.getResult() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public static boolean checkUpdate(String endPoint) throws IOException {
        InputStream inputStream = null;
        ResponseBody body = null;
        try {
            Request request = new Request.Builder()
                    .url("http://" + FileHelper.getNetworkDetails().getHost() + port + endPoint)
                    .build();
            body = client.newCall(request).execute().body();
            inputStream = client.newCall(request).execute().body().byteStream();

        } catch (ConnectException e) {
            e.getLocalizedMessage();
            showAlertDialog();
            return false;
        } finally {
            if (body != null) {
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

    public static String writeJSONIntegerList(List<Integer> list) {

        return new Gson().toJson(list);
    }

    public static String writeJSON(Object object) throws IOException, IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
       return gson.toJson(object);
    }

    public static String downloadZipFile(String endPoint, String f, String s, String destFile) throws Exception {

        OkHttpClient client = new OkHttpClient();
        String result = "http://" + FileHelper.getNetworkDetails().getHost() + port + endPoint + java.net.URLEncoder.encode(f, StandardCharsets.UTF_8.name()).replace("+", "%20") + s;

        Request request = new Request.Builder().url(result).build();
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
                .url("http://" + FileHelper.getNetworkDetails().getHost() + port + endPoint)
                .post(requestBody)
                .build();
        String resp = "";
        try (Response response = client.newCall(request).execute()) {
            resp = response.body().string();
        } catch (ConnectException connectException) {
            connectException.printStackTrace();
            showAlertDialog();
        }

        return resp;
    }


    public static ResponseBody post(String endpoint, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://" + FileHelper.getNetworkDetails().getHost() + port + endpoint)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body();
        } catch (ConnectException connectException) {
            connectException.printStackTrace();
            showAlertDialog();
        }
        return null;
    }
    public static Response postOfReturnResponse(String endpoint, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://" + FileHelper.getNetworkDetails().getHost() + port + endpoint)
                .post(body)
                .build();

        try {
            return client.newCall(request).execute();
        } catch (ConnectException connectException) {
            connectException.printStackTrace();
            showAlertDialog();
        }
        return null;
    }

    public static String writeJSONRightListList(List<RightsList> rightsLists) {
        return new Gson().toJson(rightsLists);
    }

    public static String writeJSONEmailList(List<EmailList> emailLists) {
        return new Gson().toJson(emailLists);
    }
    public static String writeJSONPhoneList(List<PhoneList> phoneLists) {
        return new Gson().toJson(phoneLists);
    }
    public static String writeJSONDomainList(List<Domain> domains) {
        return new Gson().toJson(domains);
    }

    public static String writeJSONKeywordList(List<Keyword> keywords) {
        return new Gson().toJson(keywords);
    }
}
