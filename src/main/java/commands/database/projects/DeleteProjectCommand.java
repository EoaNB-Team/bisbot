package commands.database.projects;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import core.EmbedGenerator;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;

@Deprecated
public class DeleteProjectCommand implements AdminCommand, DBCommand {
	private final String commandName = "delproject";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCenturion(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String projectid;

		if (args.length > 0) {
			projectid = args[0];
		} else {
			EmbedGenerator.CustomEmbedError("Invalid project ID.", event);
			return;
		}
		if (args.length > 1) {
			if (args[1].equals("confirm")) {
				ProjectManager.deleteProject(event, projectid);
			}
		} else {
			EmbedGenerator.CustomEmbed(":warning: Warning! You are about to delete a project! To confirm your decision, execute `ed!delproject <projectid> confirm`.", new Color(193, 114, 0), event);
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return Settings.prefix + commandName + " <projectid>";
	}

	@Override
	public String longhelp() {
		return "Deletes a project. Note that `projectid` is to be specified without the `p_` prefix.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
