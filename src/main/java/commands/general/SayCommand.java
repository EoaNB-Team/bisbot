package commands.general;

import commands.interfaces.AdminCommand;
import commands.interfaces.GeneralCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import util.Settings;
import util.SharedComRequirements;

import java.util.Arrays;

public class SayCommand implements AdminCommand, GeneralCommand {
	private final String commandName = "say";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCenturion(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		try {
			TextChannel c;
			if (args[0].contains("#")) {
				String chan = args[0].replace("<", "").replace(">", "").replace("#", "");
				c = event.getGuild().getTextChannelById(chan);
			} else {
				c = event.getGuild().getTextChannelsByName(args[0], true).get(0);
			}
			c.sendMessage(StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ")).queue();
		} catch (Exception e) {
			ErrorHandler.CustomEmbedError("Predefined channel not valid.", event);
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
	}

	@Override
	public String help() {
		return Settings.prefix + commandName + " <channel> <message>";
	}

	@Override
	public String longhelp() {
		return "Displays a message sent by edbotJ in the predefined text channel.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
