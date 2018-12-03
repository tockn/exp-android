package com.example.tockn.githubclient;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GitHubAPIStore extends AsyncTask<String, String, String> {

    final String API_ENDPOINT = "https://api.github.com";

    private TextView title, contents;
    private Object gists;

    public GitHubAPIStore(TextView t, TextView c) {
        title = t;
        contents = c;
    }

    public Object getGists() {
        return gists;
    }

    @Override
    protected String doInBackground(String... params) {
        switch (params[0]) {
            case "GetPublicGists":
                return GetPublicGists();
        }
        return null;
    }

    private String GetPublicGists() {
        String urlStr = API_ENDPOINT + "/gists/public";
        HttpURLConnection con = null;
        InputStream is = null;
        String result = "";
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            is = con.getInputStream();
            result = inputStreamToString(is);
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        }
        return result;
    }

    public void onPostExecute(String result) {
        String id = "";
        String description = "";
        try {
            JSONArray jsons = new JSONArray(result);
            JSONObject json = jsons.getJSONObject(0);
            id = json.getString("id");
            description = json.getString("description");
        }
        catch(JSONException ex) {
        }
        title.setText(id);
        contents.setText(description);
    }

    static String inputStreamToString(InputStream is) throws IOException {
        InputStreamReader reader = new InputStreamReader(is);
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[512];
        int read;
        while (0 <= (read = reader.read(buffer))) {
            builder.append(buffer, 0, read);
        }
        return builder.toString();
    }
}

