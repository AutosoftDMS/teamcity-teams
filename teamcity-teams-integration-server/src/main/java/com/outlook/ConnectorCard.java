package com.outlook;

import com.google.gson.annotations.Expose;

import java.util.Collection;

// Office365 connector card reference:
// https://dev.outlook.com/Connectors/Reference

public class ConnectorCard {
    @Expose
    public String summary;

    @Expose
    public String text;

    @Expose
    public String title;

    @Expose
    public String themeColor;

    @Expose
    public Collection<Section> sections;

    @Expose
    public Collection<ViewAction> potentialAction;
}
