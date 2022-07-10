package commands.mariadb.projects;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;

public class comDeleteProject implements AdminCommand, DBCommand {
    private final String commandName = "delproject";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCenturion(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        String projectid;

        if (Args.length > 0) {
            projectid = Args[0];
        } else {
            ErrorHandler.CustomEmbedError("Invalid project ID.", event);
            return;
        }
        if (Args.length > 1) {
            if (Args[1].equals("confirm")) {
                PrjManager.DeleteProject(event, projectid);
            }
        } else {
            ErrorHandler.CustomEmbed(":warning: Warning! You are about to delete a project! To confirm your decision, execute `ed!delproject <projectid> confirm`.", new Color(193, 114, 0), event);
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
