package com.autoloop.teamcity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.outlook.*;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.web.util.WebUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TeamsPayloadBuilder {

    private static final Logger LOG = Logger.getInstance(TeamsPayloadBuilder.class.getName());

    private static final GsonBuilder GSON_BUILDER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

    private final String serverUrl;

    public TeamsPayloadBuilder(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @NotNull
    public String getFormattedPayload(Build build, String statusText) {
        String serverUrl = WebUtil.escapeUrlForQuotes(this.serverUrl);
        String project = build.getFullName();
        String buildNumber = build.getBuildNumber();
        String branchName = getBranchName(build);
        long buildId = build.getBuildId();
        String btId = build.getBuildTypeExternalId();
        Status status = build.getStatusDescriptor().getStatus();

        // https://teamcity.jetbrains.com/viewLog.html?buildId=885548&buildTypeId=TeamCityThirdPartyPlugins_TeamcitySlackNotifications_Build
        String buildUrl = serverUrl + "/viewLog.html?buildId=" + buildId + "&buildTypeId=" + btId;

        // Build connector card to send to Office365/Teams
        // Reference: https://dev.outlook.com/Connectors/Reference
        ConnectorCard card = new ConnectorCard();
        card.summary = project + " #" + buildNumber + " " + statusText;
        card.title = project;
        card.text = "Build #" + buildNumber + " " + statusText;

        if (status.isFailed()) {
            card.text += ": " + build.getStatusDescriptor().getText();
        }

        // Looks like themeColor is ignored by Teams?
        if (status == Status.WARNING) {
            card.themeColor = "FDB913";
        }
        else if (status == Status.ERROR || status == Status.FAILURE) {
            card.themeColor = "E71E27";
        }
        else if (status == Status.NORMAL) {
            card.themeColor = "BAD80A"; // Success
        }

//        card.sections = new ArrayList<>();

//        List<? extends VcsModification> changes = build.getContainingChanges();
//        int numChanges = changes.size();
//
//        if (numChanges > 0) {
//            Section commits = new Section();
//            commits.title = "Commits";
//
//            if (numChanges > 3) {
//                commits.text = MessageFormat.format("{0} commits", numChanges);
//            }
//            else {
//                commits.facts = new ArrayList<>();
//
//                for (VcsModification change : changes) {
//                    Fact f = new Fact();
//                    f.name = change.getUserName();
//                    f.value = change.getDescription();
//
//                    commits.facts.add(f);
//                }
//            }
//
//            card.sections.add(commits);
//        }

        ViewAction viewAction = new ViewAction();
        viewAction.name = "View in TeamCity";
        viewAction.target = new String[] { buildUrl };

        card.potentialAction = new ArrayList<>();
        card.potentialAction.add(viewAction);

        Gson gson = GSON_BUILDER.create();
        return gson.toJson(card);
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
