package commands.mariadb.hiatuses;

import commands.mariadb.DBManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HiatusManager {
    public static boolean addHiatusToDB(MessageReceivedEvent event, String userid, String username, String reason, String end, String start, String comment) {
        String target = "/Pub%20Ed/scripts/postHiatus.php?userid=" + userid + "&username=" + username.replace(" ", "%20") + "&reason=" + reason.replace(" ", "%20") +"&end=" + end +"&start=" + start + "&comment=" + comment.replace(" ", "%20");
        return DBManager.EstablishConnection(event, target);
    }

    public static boolean removeHiatusFromDB(MessageReceivedEvent event, String userid) {
        String target = "/Pub%20Ed/scripts/deleteRecord.php?userid=" + userid;
        return DBManager.EstablishConnection(event, target);
    }

    public static boolean updateHiatusToDB(MessageReceivedEvent event, String userid, String reason, String end, String start, String comment) {
        String target = "/Pub%20Ed/scripts/updateRecord.php?userid=" + userid  + "&reason=" + reason.replace(" ", "%20") +"&end=" + end +"&start=" + start + "&comment=" + comment.replace(" ", "%20");
        return DBManager.EstablishConnection(event, target);
    }
}
