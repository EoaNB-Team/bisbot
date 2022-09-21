package commands.database.vote;

import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.xml.crypto.Data;

public class VoteDatabaseInterface {
	// TODO: Implement this on the database-side.
	public enum VoteType {
		RANKED,
		BINARY
	}

	public static final String ACTIVE_VOTES_TABLE = "active_votes";

	public static void addNewVote(MessageReceivedEvent event, int id, String summary, String type) {
		String sql = "INSERT INTO " + ACTIVE_VOTES_TABLE + " (ID, summary, type) VALUES (?,?,?)";
		DatabaseManager.publish(sql, event, id, summary, type);
	}

	public static Integer[] getActiveVotesIDs() {
		String sql = "SELECT ID FROM " + ACTIVE_VOTES_TABLE;
		return (Integer[]) DatabaseManager.getTable(sql)[0];
	}

	public static Object[][] getVotes() {
		String sql = "SELECT ID, summary FROM " + ACTIVE_VOTES_TABLE;
		return DatabaseManager.getTable(sql);
	}

	public static String getActiveVoteType(int id) {
		String sql = "SELECT type FROM " + ACTIVE_VOTES_TABLE + " WHERE ID=?";
		return (String) DatabaseManager.getTable(sql, id)[0][0];
	}

	public static boolean hasAnyActiveVotes() {
		String sql = "SELECT * FROM " + ACTIVE_VOTES_TABLE;
		return DatabaseManager.hasResult(sql);
	}

	public static boolean containsActiveVote(int ID) {
		String sql = "SELECT summary FROM " + ACTIVE_VOTES_TABLE + " WHERE ID=?";
		return DatabaseManager.hasResult(sql, ID);
	}
}
