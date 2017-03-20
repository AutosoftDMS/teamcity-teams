package com.autoloop.teamcity;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class TeamsSender
{
    protected final String webhookUrl;

    public TeamsSender(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String send(@NotNull String payload) throws IOException
    {
        URL url = new URL(this.webhookUrl);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setRequestProperty("Content-Type", "application/json");
        httpsURLConnection.setDoOutput(true);

        DataOutputStream dataOutputStream = new DataOutputStream(
            httpsURLConnection.getOutputStream()
        );

        byte[] array = payload.getBytes("UTF-8");
        dataOutputStream.write(array, 0, array.length);
        dataOutputStream.flush();
        dataOutputStream.close();

        InputStream inputStream;
        String responseBody = "";

        try {
            inputStream = httpsURLConnection.getInputStream();
        }
        catch (IOException e) {
            responseBody = e.getMessage();
            inputStream = httpsURLConnection.getErrorStream();
            if (inputStream != null) {
                responseBody += ": ";
                responseBody = getResponseBody(inputStream, responseBody);
            }
            throw new IOException(responseBody);
        }

        return getResponseBody(inputStream, responseBody);
    }

    private String getResponseBody(InputStream inputStream, String responseBody) throws IOException {
        String line;

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream)
        );

        while ((line = bufferedReader.readLine()) != null) {
            responseBody += line + "\n";
        }

        bufferedReader.close();
        return responseBody;
    }
}
