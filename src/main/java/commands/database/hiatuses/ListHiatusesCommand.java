package commands.database.hiatuses;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import core.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.SharedComRequirements;

import java.awt.*;
import java.util.LinkedList;

public class ListHiatusesCommand implements AdminCommand, DBCommand {
	private final String commandName = "lhiatuses";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCenturion(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		Object[][] hiatuses = HiatusManager.getHiatuses(event);

		// Will happen if there aren't any registered hiatuses.
		if (hiatuses.length == 0) {
			EmbedGenerator.CustomEmbedError("No hiatuses are registered.", event);
			return;
		}

		// First element is page, second element is hiatus.
		LinkedList<LinkedList<String>> hiatusList = new LinkedList<>();
		hiatusList.add(new LinkedList<>());
		hiatusList.get(0).add("```md\n[UserID] [Username] [Reason] [Start] [End]\n```\n");
		int totalFields = 1;
		for (Object[] hiatus : hiatuses) {
			String hiatusText = "```md\n[" + hiatus[0] + "] [" + hiatus[1] + "] [" + hiatus[2] + "] [" + hiatus[4] + "] [" + hiatus[3] + "]\n```";

			if (String.join("", hiatusList.get(totalFields - 1)).length() + hiatusText.length() > 1024) {
				totalFields++;
				hiatusList.add(new LinkedList<>());
			}

			hiatusList.get(totalFields - 1).add(hiatusText);
		}

		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(new Color(3, 193, 19));
		eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
		eb.setTitle("Registered hiatuses:");

		for (int i = 1; i <= hiatusList.size(); i++) {
			eb.addField("Page " + i + ":", String.join("", hiatusList.get(i - 1)), false);
		}

		event.getTextChannel().sendMessage(eb.build()).queue();
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
