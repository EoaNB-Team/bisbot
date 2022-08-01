package commands.database.devs;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import core.EmbedGenerator;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

public class RemoveDevCommand implements AdminCommand, DBCommand {
	private final String commandName = "devall";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCenturion(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String userid;
		try {
			if (args[0].contains("@")) {
				userid = args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
			} else {
				EmbedGenerator.CustomEmbedError("Invalid user. Use `@Username` (ping).", event);
				return;
			}
		} catch (Exception e) {
			EmbedGenerator.CustomEmbedError("Invalid user.", event);
			return;
		}
		DevManager.deleteDevFromDB(event, userid);
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return Settings.prefix + commandName + " <user>";
	}

	@Override
	public String longhelp() {
		return "Manually removes a user from the dev database.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
