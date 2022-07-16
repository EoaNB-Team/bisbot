package commands.database.hiatuses;

import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HiatusManager {
	private static final String TABLE_NAME = "hiatuses";

    public static boolean addHiatusToDB(MessageReceivedEvent event, String userid, String username, String reason, String end, String start) {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES ('"
			+ userid + "','"
			+ username + "','"
			+ reason + "','"
			+ end + "','"
			+ start + "')";
        return DatabaseManager.publish(sql, event);
    }

    public static boolean removeHiatusFromDB(MessageReceivedEvent event, String userid) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE userid='" + userid + "'";
		return DatabaseManager.publish(sql, event);
    }

    public static boolean updateHiatusToDB(MessageReceivedEvent event, String userid, String reason, String end, String start) {
        String sql = " UPDATE " + TABLE_NAME + " SET " +
			"reason=" + reason +
			"',end='" + end +
			"',start='" + start +
			"' WHERE userid='" + userid + "'";
		return DatabaseManager.publish(sql, event);
    }
}
