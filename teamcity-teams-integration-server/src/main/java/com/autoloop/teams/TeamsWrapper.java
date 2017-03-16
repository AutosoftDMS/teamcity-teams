package com.autoloop.teams;

import com.autoloop.teamcity.TeamsNotificator;
import com.autoloop.teamcity.TeamsPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.web.util.WebUtil;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class TeamsWrapper
{
    public static final GsonBuilder GSON_BUILDER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
    private static final Logger LOG = Logger.getLogger(TeamsNotificator.class);

    protected String webhookUrl;

    protected String serverUrl;

    public TeamsWrapper() {

    }

    public String send(Build build, String statusText) throws IOException
    {
        String formattedPayload = getFormattedPayload(build, statusText);
        LOG.debug(formattedPayload);

        URL url = new URL(this.getWebhookUrl());
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setRequestProperty("Content-Type", "application/json");
        httpsURLConnection.setDoOutput(true);

        DataOutputStream dataOutputStream = new DataOutputStream(
            httpsURLConnection.getOutputStream()
        );

        //dataOutputStream.writeBytes(formattedPayload);
        byte[] array = formattedPayload.getBytes("UTF-8");
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

    @NotNull
    public String getFormattedPayload(Build build, String statusText) {
        Gson gson = GSON_BUILDER.create();

        TeamsPayload teamsPayload = new TeamsPayload(build, statusText, WebUtil.escapeUrlForQuotes(getServerUrl()));

        return gson.toJson(teamsPayload);
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

    public void setWebhookUrl(String webhookUrl)
    {
        this.webhookUrl = webhookUrl;
    }

    public String getWebhookUrl()
    {
        return this.webhookUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
