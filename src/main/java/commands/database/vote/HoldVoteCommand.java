package commands.database.vote;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import core.EmbedGenerator;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.SharedComRequirements;

import java.util.ArrayList;
import java.util.Arrays;

public class HoldVoteCommand implements AdminCommand, DBCommand {
	private final String commandName = "holdvote";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCenturion(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		if (args.length < 1) {
			EmbedGenerator.CustomEmbedError("Too few arguments; You need to provide a voting system.", event);
			return;
		}

		// Generate id.
		int id = VoteManager.generateUniqueID();

		switch (args[0].toLowerCase()) {
			case "ranked":
				// Error
				if (args.length < 2) {
					EmbedGenerator.CustomEmbedError("Too few arguments; Ranked voting requires choices.", event);
					return;
				}

				// Put candidates into a list.
				ArrayList<String> candidates = new ArrayList<>(Arrays.asList(args).subList(2, args.length));

				// Create vote
				//VoteDatabaseInterface.addNewVote(event, id, "not implemented", "ranked");
				EmbedGenerator.SuccessEmbed("New ranked vote will be held", event);
				break;
			case "binary":
				// Error
				if (args.length < 2) {
					event.getChannel().sendMessage("Too few arguments; Binary voting requires a description.").queue();
					return;
				}

				// Combine the succeeding arguments into one string. (The description.)
				StringBuilder string = new StringBuilder();
				for (int i = 1; i < args.length; i++) string.append(args[i]).append(" ");

				// Create vote
				VoteDatabaseInterface.addNewVote(event, id, string.toString(), "binary");
				EmbedGenerator.SuccessEmbed("New vote is being held with description: " + string, event);
				break;
			default:
				EmbedGenerator.CustomEmbedError("Voting system is incorrect; should be 'Ranked' or 'Binary'.", event);
				return;
		}
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
