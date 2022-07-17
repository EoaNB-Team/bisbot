package commands.database.devs;

import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DevManager {
	private static final String TABLE_NAME = "hiatuses";

	public static boolean clearDevsDB(MessageReceivedEvent event) {
		String sql = "DELETE FROM " + TABLE_NAME;
		return DatabaseManager.publish(sql, event);
	}

	public static boolean addDevToDB(MessageReceivedEvent event, String userid, String username, String zone) {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?)";
		return DatabaseManager.publish(sql, event, userid, username, zone);
	}

	public static boolean deleteDevFromDB(MessageReceivedEvent event, String userid) {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE userid=?";
		return DatabaseManager.publish(sql, event, userid);
	}
}
