package commands.general;

import commands.interfaces.GeneralCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.General;
import util.Settings;

public class InfoCommand implements GeneralCommand {
    private final String commandName = "info";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return !event.getAuthor().isBot();
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        try { event.getTextChannel().sendMessage(General.getInfoEmbed(null, event, "edbotJ:").build()).queue(); }
        catch (Exception ignored) { }
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
        return "Shows general info about the bot.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
