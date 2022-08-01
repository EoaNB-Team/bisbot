package commands.database.hiatuses;

import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HiatusManager {
	private static final String TABLE_NAME = "hiatuses";

	public static boolean addHiatusToDB(MessageReceivedEvent event, String userid, String username, String reason, String end, String start) {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
		return DatabaseManager.publish(sql, event, Long.valueOf(userid), username, reason, end, start);
	}

	public static boolean removeHiatusFromDB(MessageReceivedEvent event, String userid) {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE userid=?";
		return DatabaseManager.publish(sql, event, Long.valueOf(userid));
	}

	public static boolean updateHiatusToDB(MessageReceivedEvent event, String userid, String reason, String end, String start) {
		String sql = "UPDATE " + TABLE_NAME + " SET reason=?, end=?, start=? WHERE userid=?";
		return DatabaseManager.publish(sql, event, reason, end, start, Long.valueOf(userid));
	}

	public static Object[][] getHiatuses(MessageReceivedEvent event) {
		String sql = "SELECT * FROM " + TABLE_NAME;
		return DatabaseManager.getTable(sql, event);
	}
}
