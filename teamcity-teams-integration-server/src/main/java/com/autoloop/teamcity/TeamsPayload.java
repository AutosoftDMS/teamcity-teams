package com.autoloop.teamcity;
import com.google.gson.annotations.Expose;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.SBuild;

public class TeamsPayload {
    @Expose
    protected String summary;
    @Expose
    protected String title;
    @Expose
    protected String text;
    @Expose
    protected String themeColor;

    //public String getSummary() { return summary; }
    public String getText() {
        return text;
    }
    //public String getTitle() { return title; }

    private String escape(String s) {
        return s
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private String escapeNewline(String s) {
        return s.replace("\n", "\\n");
    }

    public TeamsPayload(Build build, String statusText, String serverUrl) {
        statusText = escape(escapeNewline(statusText));
        String project = escape(build.getFullName());
        String buildNumber = escape(build.getBuildNumber());
        String branchName = getBranchName(build);
        long buildId = build.getBuildId();
        String btId = build.getBuildTypeExternalId();

        // https://teamcity.jetbrains.com/viewLog.html?buildId=885548&buildTypeId=TeamCityThirdPartyPlugins_TeamcitySlackNotifications_Build
        String buildUrl = serverUrl + "/viewLog.html?buildId=" + buildId + "&buildTypeId=" + btId;

        this.summary = project + " #" + buildNumber + " " + statusText;
        this.title = project;
        this.text = "[Build #" + buildNumber + "]" + "(" + buildUrl + ") " + statusText;
        this.themeColor = build.getStatusDescriptor().getStatus().getHtmlColor();
    }

    private static String getBranchName(Build build) {
        Branch branch = null;

        if (build instanceof SBuild) {
            branch = ((SBuild)build).getBranch();
        }

        if (branch != null && branch.getName() != "<default>") {
            return branch.getDisplayName();
        } else {
            return "";
        }
    }
}
