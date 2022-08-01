package commands.database.playtest;

import commands.interfaces.DBCommand;
import core.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import util.General;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;

public class ReqReportCommand implements DBCommand {
	private final String commandName = "rqplay";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCuria(event) && !event.isFromType(ChannelType.PRIVATE);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String name;
		String zone;

		if (args.length > 0) {
			String arg = args[0].toLowerCase(Locale.ROOT);
			if (Settings.zones.containsKey(arg)) {
				try {
					zone = Objects.requireNonNull(event.getGuild().getRoleById(Settings.zones.get(arg))).getName();
				} catch (NullPointerException e) {
					EmbedGenerator.CustomEmbedError("Could not get role name by id.", event);
					event.getChannel().sendMessage("<@" + Settings.OWNER + ">").queue();
					return;
				}
			} else {
				EmbedGenerator.CustomEmbedError("Zone name not found. See `" + Settings.prefix + "zones` for a list of all zones.", event);
				return;
			}
		} else {
			EmbedGenerator.CustomEmbedError("Invalid zone name. See `" + Settings.prefix + "zones` for a list of all zones.", event);
			return;
		}
		if (args.length > 1) {
			name = args[1].toLowerCase(Locale.ROOT);
		} else {
			EmbedGenerator.CustomEmbedError("Invalid playtest name.", event);
			return;
		}

		// Get stored playtests from database.
		Object[][] playtests = PlaytestReportManager.getPlaytestsFromDB(event, null);
		Object[] desiredPlaytest = null;

		// Will happen if there aren't any registered playtests.
		if (playtests == null) {
			EmbedGenerator.CustomEmbedError("No playtests are registered.", event);
			return;
		}

		// Loop through the list we got from the database and see if the playtest exists.
		for (Object[] obj : playtests) {
			if (((String) obj[0]).equalsIgnoreCase(name) && obj[1].equals(zone)) {
				desiredPlaytest = obj;
				break;
			}
		}

		if (desiredPlaytest != null) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(new Color(3, 193, 19));
			eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
			eb.setTitle("Playtest Request:");
			eb.setDescription("Click the button below to get a link to the playtest.");
			eb.addField((String) desiredPlaytest[0], "by " + desiredPlaytest[4] + " for "
				+ desiredPlaytest[1] + "\n\n" + desiredPlaytest[5], false);

			Matcher match = General.WEB_URL().matcher((String) desiredPlaytest[5]);
			if (!match.find()) {
				EmbedGenerator.CustomEmbedError("Playtest comment has no link attached.", event);
				return;
			}
			event.getTextChannel().sendMessage(eb.build())
				.setActionRow(Button.link(match.group(0), "Link to playtest"))
				.queue();
		} else {
			EmbedGenerator.CustomEmbedError("Could not find specified playtest.", event);
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return Settings.prefix + commandName + " <zone> <name>";
	}

	@Override
	public String longhelp() {
		return "Requests a playtest report from the database.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
