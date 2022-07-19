package commands.database.playtest;

import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PlaytestReportManager {
	private static final String REPORTS_TABLE = "playtest_reports";
	private static final String REQUESTS_TABLE = "playtest_requests";

	public static boolean addPlaytestToDB(MessageReceivedEvent event, String name, String zone, boolean local, Long userid, String username, String comment) {
		String sql = "INSERT INTO " + REPORTS_TABLE + " VALUES (?, ?, ?, ?, ?, ?)";
		return DatabaseManager.publish(sql, event, name,  zone, (local ? "1" : "0"), userid, username, comment);
	}

	public static boolean addPlaytestReqToDB(MessageReceivedEvent event, String name, String zone, Long userid, String username, String comment) {
		String sql = "INSERT INTO " + REQUESTS_TABLE + " VALUES (?, ?, ?, ?, ?)";
		return DatabaseManager.publish(sql, event, name, zone, userid, username, comment);
	}

	public static boolean removePlaytestFromDB(MessageReceivedEvent event, String name, String zone) {
		String sql = "DELETE FROM " + REPORTS_TABLE + " WHERE name=?";
		return DatabaseManager.publish(sql, event, name);
	}

	public static boolean removePlaytestRequestFromDB(MessageReceivedEvent event, String name, String zone) {
		String sql = "DELETE FROM " + REQUESTS_TABLE + " WHERE name=?";
		return DatabaseManager.publish(sql, event, name);
	}

	public static Object[][] getPlaytestsFromDB(MessageReceivedEvent event, String zone) {
		if (zone == null) {
			String sql = "SELECT * FROM " + REPORTS_TABLE;

			return DatabaseManager.getTable(sql, event);
		} else {
			String sql = "SELECT * FROM " + REPORTS_TABLE +  " WHERE zone=?";

			return DatabaseManager.getTable(sql, event, zone);
		}
	}

	public static Object[][] getPlaytestReqsFromDB(MessageReceivedEvent event, String zone) {
		if (zone == null) {
			String sql = "SELECT * FROM " + REQUESTS_TABLE;

			return DatabaseManager.getTable(sql, event);
		} else {
			String sql = "SELECT * FROM " + REQUESTS_TABLE + " WHERE zone=?";

			return DatabaseManager.getTable(sql, event, zone);
		}
	}
}
