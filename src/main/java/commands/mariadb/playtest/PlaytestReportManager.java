package commands.mariadb.playtest;

import commands.mariadb.DBManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PlaytestReportManager {
    public static boolean addPlaytestToDB(MessageReceivedEvent event, String name, String zone, boolean local, String userid, String username, String comment) {
        String target = "/Pub%20Ed/scripts/postPlayReport.php?repname=" + name.replace(" ", "%20") + "&zone=" + zone.replace(" ", "%20") + "&local=" + (local ? "1" : "0") + "&userid=" + userid + "&username=" + username.replace(" ", "%20") + "&comment=" + comment.replace(" ", "%20");
        return DBManager.EstablishConnection(event, target);
    }

    public static boolean addPlaytestReqToDB(MessageReceivedEvent event, String name, String zone, String userid, String username, String comment) {
        String target = "/Pub%20Ed/scripts/postPlayRequest.php?repname=" + name.replace(" ", "%20") + "&zone=" + zone.replace(" ", "%20") + "&userid=" + userid + "&username=" + username.replace(" ", "%20") + "&comment=" + comment.replace(" ", "%20");
        return DBManager.EstablishConnection(event, target);
    }

    public static boolean removePlaytestFromDB(MessageReceivedEvent event, String name, String zone) {
        String target = "/Pub%20Ed/scripts/deletePlayReport.php?repname=" + name.replace(" ", "%20") + "&zone=" + zone.replace(" ", "%20");
        return DBManager.EstablishConnection(event, target);
    }

    public static boolean removePlaytestRequestFromDB(MessageReceivedEvent event, String name, String zone) {
        String target = "/Pub%20Ed/scripts/deletePlayRequest.php?repname=" + name.replace(" ", "%20") + "&zone=" + zone.replace(" ", "%20");
        return DBManager.EstablishConnection(event, target);
    }

    public static String getPlaytestsFromDB(MessageReceivedEvent event, String zone) {
        String target = "/Pub%20Ed/Playtest%20Reports/getPlayReportsJson.php" + ((zone == null) ? "" : "?zone=" + zone.replace(" ", "%20"));
        return DBManager.EstablishConnectionReturn(event, target);
    }

    public static String getPlaytestReqsFromDB(MessageReceivedEvent event, String zone) {
        String target = "/Pub%20Ed/Playtest%20Requests/getPlayRequestsJson.php" + ((zone == null) ? "" : "?zone=" + zone.replace(" ", "%20"));
        return DBManager.EstablishConnectionReturn(event, target);
    }
}
