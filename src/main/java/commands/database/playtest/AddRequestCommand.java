package commands.database.playtest;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class AddRequestCommand implements DBCommand {
	private final String commandName = "aplayreq";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCuria(event) && !event.isFromType(ChannelType.PRIVATE);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String name;
		String zone;
		String zoneRaw;
		Long userid = event.getAuthor().getIdLong();
		String username = event.getAuthor().getName();
		String comment = "";

		if (args.length > 0) {
			String arg = args[0].toLowerCase(Locale.ROOT);
			zoneRaw = arg;
			if (Settings.zones.containsKey(arg)) {
				try {
					zone = Objects.requireNonNull(event.getGuild().getRoleById(Settings.zones.get(arg))).getName();
				} catch (NullPointerException e) {
					ErrorHandler.CustomEmbedError("Could not get role name by id.", event);
					event.getChannel().sendMessage("<@" + Settings.OWNER + ">").queue();
					return;
				}
			} else {
				ErrorHandler.CustomEmbedError("Zone name not found. See `" + Settings.prefix + "zones` for a list of all zones.", event);
				return;
			}
		} else {
			ErrorHandler.CustomEmbedError("Invalid zone name. See `" + Settings.prefix + "zones` for a list of all zones.", event);
			return;
		}
		if (args.length > 1) {
			name = args[1];
		} else {
			ErrorHandler.CustomEmbedError("Invalid playtest name.", event);
			return;
		}
		if (args.length > 2) {
			comment = args[2];
		}

		boolean added = PlaytestReportManager.addPlaytestReqToDB(event, name, zone, userid, username, comment);
		if (added) {
			TextChannel chan = (TextChannel) Objects.requireNonNull(event.getGuild().getGuildChannelById("810768564218232852")); // auditor

			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(new Color(3, 193, 19));
			eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
			eb.setTitle("New Playtest Request Added for " + zone);
			eb.setDescription("Submit a playtest for this request with `" + Settings.prefix + "aplay <...>` when ready (for syntax see `" + Settings.prefix + "help db`).");
			eb.addField(name, "by " + username + "\n\n" + comment, false);

			chan.sendMessage(eb.build()).queue();
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return Settings.prefix + commandName + " <zone> <name> [comment]";
	}

	@Override
	public String longhelp() {
		return "Adds a playtest request to the database.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
