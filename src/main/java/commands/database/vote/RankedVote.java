package commands.database.vote;

import java.util.HashSet;

public class RankedVote {
	public HashSet<RankedVoter> voters;

	public RankedVote() {

	}

	/**
	 * All the data in this class is stored in memory, and therefore should be relatively safe from database attacks.
	 * In the future we might some cryptographic hashing here to decrease the risk of memory tools, like Cheat Engine, from accessing the data.
	 * TODO: Figure out a way to make this safe from both intrusions and unforeseen shutdowns.
	 */
	protected class RankedVoter {
		public HashSet<String> votes;
		public long voterID;

		public RankedVoter(long voterID) {
			this.voterID = voterID;
		}

		public void addVote(String vote) {
			votes.add(vote);
		}
	}
}
