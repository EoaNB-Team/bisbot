package commands.database.projects;

import commands.interfaces.DBCommand;
import core.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;

@Deprecated
public class RemoveUserFromProjectCommand implements DBCommand {
	private final String commandName = "prjremove";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCuria(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		try {
			String userid;
			String username;

			try {
				if (args[0].contains("@")) {
					userid = args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
					username = event.getJDA().retrieveUserById(userid).complete().getName();

					if (!userid.equals(event.getMessage().getAuthor().getId()) && !event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Settings.CENTURION))) {
						EmbedGenerator.CustomEmbedError("Invalid user. Only Centurions can submit project applications for other people.", event);
						return;
					}
				} else {
					EmbedGenerator.CustomEmbedError("Invalid user. Use `@Username` (ping).", event);
					return;
				}
			} catch (Exception e) {
				EmbedGenerator.CustomEmbedError("Invalid user.", event);
				return;
			}

			if (!event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Settings.CENTURION))) {
				Settings.projectRemList.put(userid, new ProjectRemoveRequest(event, userid, username));

				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(new Color(3, 193, 19));
				eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
				eb.setDescription(":white_check_mark: Your project dismissal application has been sent, " + event.getAuthor().getAsMention() + "! You will be DM'ed once your request has been reviewed by a Centurion, so do not leave the server.");
				event.getChannel().sendMessage(eb.build()).queue();

				eb.setTitle("Project dismissal application by " + event.getAuthor().getName() + ":");
				eb.setDescription("Profile: " + event.getAuthor().getAsMention());
				eb.setFooter(userid, event.getJDA().getSelfUser().getAvatarUrl());
				event.getJDA().getTextChannelById(Settings.projectEndchan).sendMessage(eb.build()).queue();
			} else {
				ProjectManager.deleteUserFromProject(event, userid);
			}
		} catch (Exception e) {
			EmbedGenerator.CustomEmbedError("Wrong syntax.", event);
		}
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
		return "Removes a user from any projects.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
