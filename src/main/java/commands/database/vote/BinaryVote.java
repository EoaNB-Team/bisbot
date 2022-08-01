package commands.database.vote;

public class BinaryVote {
	public enum VoteOption {
		SUPPORT,
		OPPOSE
	}

	public final VoteOption[] votes;
	public final String[] voters;

	public BinaryVote(VoteOption[] votes, String[] voters) {
		this.votes = votes;
		this.voters = voters;
	}
}
