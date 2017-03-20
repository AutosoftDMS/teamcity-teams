package com.outlook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

// Office365 connector card reference:
// https://dev.outlook.com/Connectors/Reference

public class ViewAction {
    @Expose
    @SerializedName("@context")
    public final String context = "http://schema.org";

    @Expose
    @SerializedName("@type")
    public final String type = "ViewAction";

    @Expose
    public String name;

    @Expose
    public String[] target;
}
