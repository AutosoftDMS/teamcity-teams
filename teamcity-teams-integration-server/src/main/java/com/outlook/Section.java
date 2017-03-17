package com.outlook;

import com.google.gson.annotations.Expose;

import java.util.Collection;

public class Section {
    @Expose
    public String title;

    @Expose
    public String activityTitle;

    @Expose
    public String activitySubtitle;

    @Expose
    public String activityImage;

    @Expose
    public String activityText;

    @Expose
    public Collection<Fact> facts;

    @Expose
    public Collection<Image> images;

    @Expose
    public String text;

    @Expose
    public boolean markdown = true;

    @Expose
    public Collection<ViewAction> potentialAction;
}
