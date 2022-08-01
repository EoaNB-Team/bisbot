package commands.database.vote;

import commands.interfaces.DBCommand;
import commands.interfaces.GeneralCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.SharedComRequirements;

public class ListVotesCommand implements GeneralCommand, DBCommand {
	private final String commandName = "listvotes";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCuria(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// Check if there are any active votes.
		if (!VoteDatabaseInterface.hasAnyActiveVotes()) {
			event.getChannel().sendMessage("There are no active votes.").queue();
			return;
		}

		EmbedBuilder message = new EmbedBuilder();
		message.setTitle("Active votes:");

		for (Object[] i : VoteDatabaseInterface.getVotes()) {
			message.addField(String.valueOf(i[0]), (String) i[1], false);
		}

		event.getChannel().sendMessage(message.build()).queue();
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
