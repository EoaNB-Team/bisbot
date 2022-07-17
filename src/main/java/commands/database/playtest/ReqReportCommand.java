package commands.database.playtest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import commands.interfaces.DBCommand;
import core.ErrorHandler;
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
			name = args[1].toLowerCase(Locale.ROOT);
		} else {
			ErrorHandler.CustomEmbedError("Invalid playtest name.", event);
			return;
		}

		String playtests = PlaytestReportManager.getPlaytestsFromDB(event, null);
		JsonArray json = new Gson().fromJson(playtests, JsonArray.class);
		JsonArray desiredPlaytest = null;

		for (JsonElement j : json) {
			JsonArray inner = j.getAsJsonArray();
			if (inner.get(0).getAsString().toLowerCase(Locale.ROOT).equals(name)
				&& inner.get(1).getAsString().equals(zone)) {
				desiredPlaytest = inner;
				break;
			}
		}

		if (desiredPlaytest != null) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(new Color(3, 193, 19));
			eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
			eb.setTitle("Playtest Request:");
			eb.setDescription("Click the button below to get a link to the playtest.");
			eb.addField(desiredPlaytest.get(0).getAsString(), "by " + desiredPlaytest.get(4).getAsString() + " for "
				+ desiredPlaytest.get(1).getAsString() + "\n\n" + desiredPlaytest.get(5).getAsString(), false);

			Matcher match = General.WEB_URL().matcher(desiredPlaytest.get(5).getAsString());
			if (!match.find()) {
				ErrorHandler.CustomEmbedError("Playtest comment has no link attached.", event);
				return;
			}
			event.getTextChannel().sendMessage(eb.build())
				.setActionRow(Button.link(match.group(0), "Link to playtest"))
				.queue();
		} else {
			ErrorHandler.CustomEmbedError("Could not find specified playtest.", event);
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
