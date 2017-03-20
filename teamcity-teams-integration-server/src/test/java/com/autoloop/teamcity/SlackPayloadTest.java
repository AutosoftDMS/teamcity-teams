package com.autoloop.teamcity;

//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.Test;
//
//import static org.testng.Assert.*;

//public class SlackPayloadTest {
//
//    String project = "project<http://example.com|lol>";
//    String build = "build<http://example.com|lol>";
//    String branch = "<http://example.com|lol>";
//    String statusText = "<status>";
//    String statusTextMultiline = "<status\n>";
//    String statusColor = "color";
//    String btId = "btId<http://example.com|lol>";
//    long buildId = 0;
//    String serverUrl = "localhost";
//    String channel = "#channel";
//    String username = "bot";
//    TeamsPayload teamsPayload;
//
//    private String escape(String s) {
//        return s
//                .replace("<", "&lt;")
//                .replace(">", "&gt;");
//    }
//
//    @AfterMethod
//    public void tearDown() throws Exception {
//        teamsPayload = null;
//    }
//
//    @Test
//    public void testSlackPayloadDoesNotRequiresUserAndChannel() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        assertFalse(teamsPayload == null);
//    }
//
//    @Test
//    public void testSlackPayloadWithoutAttachment() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        teamsPayload.setUseAttachments(false);
//        assertFalse(teamsPayload.hasAttachments());
//    }
//
//    @Test
//    public void testSlackPayloadUsesAttachmentByDefault() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        assertTrue(teamsPayload.hasAttachments());
//    }
//
//    @Test
//    public void testSlackPayloadIsUpdatedWithUsername() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        teamsPayload.setUseAttachments(false);
//        teamsPayload.setUsername(username);
//        assertTrue(teamsPayload.getUsername().equals(username));
//    }
//
//    @org.testng.annotations.Test
//    public void testSlackPayloadIsUpdatedWithChannel() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        teamsPayload.setUseAttachments(false);
//        teamsPayload.setChannel(channel);
//        assertTrue(teamsPayload.getChannel().equals(channel));
//    }
//
//    @Test
//    public void testSlackPayloadProjectEscapeLtGt() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        assertFalse(teamsPayload.getText().contains(project));
//        assertTrue(teamsPayload.getText().contains(escape(project)));
//    }
//
//    @Test
//    public void testSlackPayloadBuildEscapeLtGt() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        assertFalse(teamsPayload.getText().contains(build));
//        assertTrue(teamsPayload.getText().contains(escape(build)));
//    }
//
//    @Test
//    public void testSlackPayloadBranchEscapeLtGt() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        assertFalse(teamsPayload.getText().contains(branch));
//        assertTrue(teamsPayload.getText().contains(escape(branch)));
//    }
//
//    @Test
//    public void testSlackPayloadStatusTextEscapeLtGt() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        assertFalse(teamsPayload.getText().contains(statusText));
//        assertTrue(teamsPayload.getText().contains(escape(statusText)));
//    }
//
//    @Test
//    public void testSlackPayloadBtIdEscapeLtGt() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
//        assertFalse(teamsPayload.getText().contains(btId));
//        assertTrue(teamsPayload.getText().contains(escape(btId)));
//    }
//
//    @Test
//    public void testSlackPayloadStatusEscapeNewline() {
//        teamsPayload = new TeamsPayload(project, build, branch, statusTextMultiline, statusColor, btId, buildId, serverUrl);
//        assertFalse(teamsPayload.getText().contains("\n"));
//        assertTrue(teamsPayload.getText().contains("\\n"));
//    }
//
//}