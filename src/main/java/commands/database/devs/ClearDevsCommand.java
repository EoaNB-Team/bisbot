package commands.database.devs;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

public class ClearDevsCommand implements AdminCommand, DBCommand {
    private final String commandName = "devclear";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCenturion(event);
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        DevManager.clearDevsDB(event);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return Settings.prefix + commandName;
    }

    @Override
    public String longhelp() {
        return "Resets the dev database. Use with caution!";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
