package commands.database.playtest;

import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class PlaytestReportManager {
	private static final String REPORTS_TABLE = "playtest_reports";
	private static final String REQUESTS_TABLE = "playtest_requests";

	public static boolean addPlaytestToDB(MessageReceivedEvent event, String name, String zone, boolean local, String userid, String username, String comment) {
		String sql = "INSERT INTO " + REPORTS_TABLE + " VALUES (?, ?, ?, ?, ?, ?)";
		return DatabaseManager.publish(sql, event, name,  zone, (local ? "1" : "0"), userid, username, comment);
	}

	public static boolean addPlaytestReqToDB(MessageReceivedEvent event, String name, String zone, String userid, String username, String comment) {
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

	public static String getPlaytestsFromDB(MessageReceivedEvent event, String zone) {
		String sql = "SELECT * FROM " + REPORTS_TABLE + zone != null ? " WHERE zone=?" : "";

		return Arrays.toString(DatabaseManager.getStrings(sql, event, zone));
	}

	public static String getPlaytestReqsFromDB(MessageReceivedEvent event, String zone) {
		String sql = "SELECT * FROM " + REQUESTS_TABLE
			+ zone != null ? " WHERE zone=?" : "";

		return Arrays.toString(DatabaseManager.getStrings(sql, event, zone));
	}
}
