package commands.database.playtest;

import commands.interfaces.DBCommand;
import core.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;

public class ReqAllRequestsCommand implements DBCommand {
	private final String commandName = "lplayreq";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCuria(event) && !event.isFromType(ChannelType.PRIVATE);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String zone = null;
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
		}

		// Get stored playtest requests from database.
		Object[][] playreqs = PlaytestReportManager.getPlaytestReqsFromDB(event, zone);

		// Will happen if there aren't any registered playtest requests.
		if (playreqs == null) {
			EmbedGenerator.CustomEmbedError("No playtest requests are registered.", event);
			return;
		}

		int totalFields = 1;
		LinkedList<LinkedList<String>> lPlaytests = new LinkedList<>();
		lPlaytests.add(new LinkedList<>());
		lPlaytests.get(0).add("```md\n[ZONE][NAME] by REQUESTOR: COMMENT\n```\n");

		for (Object[] col : playreqs) {
			String curPlay = "```md\n[" + col[1] + "][" + col[0] + "] by " + col[3] + ": " + col[4] + "\n```";

			if (String.join("", lPlaytests.get(totalFields - 1)).length() + curPlay.length() > 1024) {
				totalFields++;
				lPlaytests.add(new LinkedList<>());
			}
			lPlaytests.get(totalFields - 1).add(curPlay);
		}

		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(new Color(3, 193, 19));
		eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
		eb.setTitle("Playtest Requests:");
		eb.setDescription("Use `" + Settings.prefix + "lplayreq [zone]` to get a list of playtest requests only related to specified zone. (e.g. `" + Settings.prefix + "lplayreq c`)");
		for (int i = 1; i <= lPlaytests.size(); i++) {
			eb.addField("Page " + i + ":", String.join("", lPlaytests.get(i - 1)), false);
		}
		event.getTextChannel().sendMessage(eb.build()).queue();
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return Settings.prefix + commandName + " [zone]";
	}

	@Override
	public String longhelp() {
		return "Requests a list of all playtest requests from the database. `zone` can be" +
			" specified to only display requests for said zone.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
