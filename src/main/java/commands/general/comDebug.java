package commands.general;

import commands.interfaces.AdminCommand;
import commands.interfaces.GeneralCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Secrets;
import util.SharedComRequirements;

import java.awt.*;

public class comDebug implements AdminCommand, GeneralCommand {
    private final String commandName = "debug";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkOwner(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        Secrets.debug = !Secrets.debug;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(3, 193, 19));
        eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
        eb.setTitle("edbotJ:");
        eb.setDescription(":white_check_mark: Debug mode set to " + Secrets.debug + ".");
        event.getTextChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return Secrets.prefix + commandName;
    }

    @Override
    public String longhelp() {
        return "Toggles the debug mode of edbotJ. Only usable by the owner.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
