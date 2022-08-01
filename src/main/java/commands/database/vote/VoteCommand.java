package commands.database.vote;

import commands.interfaces.DBCommand;
import commands.interfaces.GeneralCommand;
import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.SharedComRequirements;

public class VoteCommand implements GeneralCommand, DBCommand {
	private static final Logger logger = LoggerFactory.getLogger(VoteCommand.class);

	private final String commandName = "vote";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkSeniorDev(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		event.getChannel().sendMessage("Received voting request; check DMs.").queue();

		if (!VoteDatabaseInterface.hasAnyActiveVotes()) {
			event.getChannel().sendMessage("There are no active votes.").queue();
			return;
		}

		String id = event.getAuthor().getId();

		int voteID;
		try {
			voteID = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex) {
			logger.error(ex.getMessage());
			event.getChannel().sendMessage("Vote id must be a number.").queue();
			return;
		}

		if (!VoteDatabaseInterface.containsActiveVote(voteID)) {
			event.getChannel().sendMessage("Vote isn't valid, use ed!listvotes to get a list of active votes.").queue();
			return;
		}

		// TODO Start voting for this user.
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return null;
	}

	@Override
	public String longhelp() {
		return null;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
