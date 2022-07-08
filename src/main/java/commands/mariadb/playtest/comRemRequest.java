package commands.mariadb.playtest;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Secrets;
import util.SharedComRequirements;

import java.util.Locale;
import java.util.Objects;

public class comRemRequest implements DBCommand {
    private final String commandName = "rmvplayreq";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCenturion(event) && !event.isFromType(ChannelType.PRIVATE);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        String name;
        String zone;

        if (Args.length > 0) {
            String arg = Args[0].toLowerCase(Locale.ROOT);
            if (Secrets.vicari.containsKey(arg)) {
                try {
                    zone = Objects.requireNonNull(event.getGuild().getRoleById(Secrets.vicari.get(arg))).getName();
                } catch (NullPointerException e) {
                    ErrorHandler.CustomEmbedError("Could not get role name by id.", event);
                    event.getChannel().sendMessage("<@" + Secrets.OWNER + ">").queue();
                    return;
                }
            } else {
                ErrorHandler.CustomEmbedError("Zone name not found. See `" + Secrets.prefix + "zones` for a list of all zones.", event);
                return;
            }
        } else {
            ErrorHandler.CustomEmbedError("Invalid zone name. See `" + Secrets.prefix + "zones` for a list of all zones.", event);
            return;
        }
        if (Args.length > 1) {
            name = Args[1];
        } else {
            ErrorHandler.CustomEmbedError("Invalid playtest request name.", event);
            return;
        }

        PlaytestReportmanager.RemovePlaytestRequestFromDB(event, name, zone);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return Secrets.prefix + commandName + " <zone> <name>";
    }

    @Override
    public String longhelp() {
        return "Removes a playtest request from the database.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
