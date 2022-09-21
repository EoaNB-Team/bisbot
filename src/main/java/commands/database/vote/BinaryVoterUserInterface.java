package commands.database.vote;

public class BinaryVoterUserInterface implements VoterUserInterface {
	private long userID;
	private long channelID;

	public BinaryVoterUserInterface(long userID, long channelID) {
		this.userID = userID;
		this.channelID = channelID;
	}
}
