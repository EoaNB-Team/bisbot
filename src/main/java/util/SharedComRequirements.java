package util;

import core.EmbedGenerator;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SharedComRequirements {
	public static boolean checkCenturion(MessageReceivedEvent event) {
		return checkId(event, Settings.CENTURION, true, "You have to be a centurion to be able to execute this command.");
	}

	public static boolean checkCuria(MessageReceivedEvent event) {
		return checkId(event, Settings.CURIA, true, "You have to be a developer to be able to execute this command.");
	}

	public static boolean checkSeniorDev(MessageReceivedEvent event) {
		return checkId(event, Settings.SENIOR, true, "You have to be a senior developer to be able to execute this command.");
	}

	public static boolean checkOwner(MessageReceivedEvent event) {
		return checkId(event, Settings.OWNER, false, "You have to be the owner of this bot to be able to execute this command.");
	}

	public static boolean checkId(MessageReceivedEvent event, String id, boolean idIsRole, String msg) {
		// Do not check if the author is a bot.
		if (event.getAuthor().isBot()) return false;

		if (idIsRole) {
			boolean userHasRole = event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete()
				.getRoles().contains(event.getGuild().getRoleById(id));

			if (!userHasRole) {
				EmbedGenerator.CustomEmbedError(msg, event);
				return false;
			}
		} else {
			if (!event.getAuthor().getId().equals(id)) {
				EmbedGenerator.CustomEmbedError(msg, event);
				return false;
			}
		}

		return true;
	}
}
