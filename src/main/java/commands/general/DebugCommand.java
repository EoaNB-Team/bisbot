package commands.general;

import commands.interfaces.AdminCommand;
import commands.interfaces.GeneralCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;

public class DebugCommand implements AdminCommand, GeneralCommand {
	private final String commandName = "debug";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkOwner(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		Settings.debug = !Settings.debug;

		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(new Color(3, 193, 19));
		eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
		eb.setTitle("edbotJ:");
		eb.setDescription(":white_check_mark: Debug mode set to " + Settings.debug + ".");
		event.getTextChannel().sendMessage(eb.build()).queue();
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return Settings.prefix + commandName;
	}

	@Override
	public String longhelp() {
		return "Toggles the debug mode of edbotJ. Only usable by the owner.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
