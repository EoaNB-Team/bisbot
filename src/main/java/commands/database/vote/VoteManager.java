package commands.database.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class VoteManager {
	public static final Logger logger = LoggerFactory.getLogger(VoteManager.class);

	public static int generateUniqueID() {
		Random random = new Random();
		int id;

		// Check if id is available.
		int tries = 0;

		// Generate id
		boolean tryAgain = false;
		do {
			tries++;
			if (tries > 999) {
				logger.error("Could not find valid vote id.");
				System.exit(-1);
			}

			id = random.nextInt();

			if (VoteDatabaseInterface.hasAnyActiveVotes()) return id;

			for (Integer i : VoteDatabaseInterface.getActiveVotesIDs()) {
				if (i.equals(id)) {
					tryAgain = true;
					break;
				}
			}
		} while (tryAgain);

		return id;
	}
}
