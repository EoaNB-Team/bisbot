package commands.mariadb.projects;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

public class comAddProject implements AdminCommand, DBCommand {
    private final String commandName = "addproject";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCenturion(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        String projectid;
        String projectname;
        String projectdesc;
        String projectowner;

        if (Args.length > 0) {
            projectid = Args[0];
        } else {
            ErrorHandler.CustomEmbedError("Invalid project ID.", event);
            return;
        }
        if (Args.length > 1) {
            projectname = Args[1];
        } else {
            ErrorHandler.CustomEmbedError("Invalid project name.", event);
            return;
        }
        if (Args.length > 2) {
            projectdesc = Args[2];
        } else {
            ErrorHandler.CustomEmbedError("Invalid project description.", event);
            return;
        }
        projectowner = event.getMessage().getAuthor().getName();

        PrjManager.AddProject(event, projectid, projectname, projectdesc, projectowner);
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
