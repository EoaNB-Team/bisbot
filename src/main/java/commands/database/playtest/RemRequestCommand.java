package commands.database.playtest;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

public class RemRequestCommand implements DBCommand {
	private final String commandName = "rmvplayreq";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCenturion(event) && !event.isFromType(ChannelType.PRIVATE);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String name;
		String zone = null;

        /*if (Args.length > 0) {
            String arg = Args[0].toLowerCase(Locale.ROOT);
            if (Settings.vicari.containsKey(arg)) {
                try {
                    zone = Objects.requireNonNull(event.getGuild().getRoleById(Settings.vicari.get(arg))).getName();
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
        if (Args.length > 1) {
            name = Args[1];
        } else {
            ErrorHandler.CustomEmbedError("Invalid playtest request name.", event);
            return;
        }*/

		if (args.length > 0) {
			name = args[0];
		} else {
			ErrorHandler.CustomEmbedError("Invalid playtest request name.", event);
			return;
		}

		PlaytestReportManager.removePlaytestRequestFromDB(event, name, zone);
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
		return "Removes a playtest request from the database.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
