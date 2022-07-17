package commands.database.playtest;

import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PlaytestReportManager {
	private static final String REPORTS_TABLE = "playtest_reports";
	private static final String REQUESTS_TABLE = "playtest_requests";

	public static boolean addPlaytestToDB(MessageReceivedEvent event, String name, String zone, boolean local, String userid, String username, String comment) {
		String sql = "INSERT INTO " + REPORTS_TABLE + " VALUES ('"
			+ name + "','"
			+ zone + "','"
			+ (local ? "1" : "0") + "','"
			+ userid + "','"
			+ username + "','"
			+ comment + "')";
		return DatabaseManager.publish(sql, event);
	}

	public static boolean addPlaytestReqToDB(MessageReceivedEvent event, String name, String zone, String userid, String username, String comment) {
		String sql = "INSERT INTO " + REQUESTS_TABLE + " VALUES ('"
			+ name + "','"
			+ zone + "','"
			+ userid + "','"
			+ username + "','"
			+ comment + "')";
		return DatabaseManager.publish(sql, event);
	}

	public static boolean removePlaytestFromDB(MessageReceivedEvent event, String name, String zone) {
		String sql = "DELETE FROM " + REPORTS_TABLE + " WHERE name='"
			+ name + "'";
		return DatabaseManager.publish(sql, event);
	}

	public static boolean removePlaytestRequestFromDB(MessageReceivedEvent event, String name, String zone) {
		String sql = "DELETE FROM " + REQUESTS_TABLE + " WHERE name='"
			+ name + "'";
		return DatabaseManager.publish(sql, event);
	}

	public static String getPlaytestsFromDB(MessageReceivedEvent event, String zone) {
		String sql = "SELECT * FROM " + REPORTS_TABLE
			+ zone != null ? " WHERE zone='" + zone + "'" : "";

		Object[] obj = DatabaseManager.retrieveColumn(sql, event);
		if (obj instanceof String[]) {
			return (String) obj[0];
		}

		return null;
	}

	public static String getPlaytestReqsFromDB(MessageReceivedEvent event, String zone) {
		String sql = "SELECT * FROM " + REQUESTS_TABLE
			+ zone != null ? " WHERE zone='" + zone + "'" : "";

		Object[] obj = DatabaseManager.retrieveColumn(sql, event);
		if (obj instanceof String[]) {
			return (String) obj[0];
		}

		return null;
	}
}
