package commands.database.vote;

public class RankedVoterUserInterface implements VoterUserInterface {
	private long userID;
	private long channelID;

	public RankedVoterUserInterface(long userID, long channelID) {
		this.userID = userID;
		this.channelID = channelID;
	}
}
