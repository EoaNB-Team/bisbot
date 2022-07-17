package commands.database.projects;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

@Deprecated
public class AddProjectCommand implements AdminCommand, DBCommand {
	private final String commandName = "addproject";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCenturion(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String projectid;
		String projectname;
		String projectdesc;
		String projectowner;

		if (args.length > 0) {
			projectid = args[0];
		} else {
			ErrorHandler.CustomEmbedError("Invalid project ID.", event);
			return;
		}
		if (args.length > 1) {
			projectname = args[1];
		} else {
			ErrorHandler.CustomEmbedError("Invalid project name.", event);
			return;
		}
		if (args.length > 2) {
			projectdesc = args[2];
		} else {
			ErrorHandler.CustomEmbedError("Invalid project description.", event);
			return;
		}
		projectowner = event.getMessage().getAuthor().getName();

		ProjectManager.addProject(event, projectid, projectname, projectdesc, projectowner);
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return Settings.prefix + commandName + " <projectid> <projectname> <projectdesc>";
	}

	@Override
	public String longhelp() {
		return "Creates a new project. Note that `projectid` is to be specified without the `p_` prefix.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
