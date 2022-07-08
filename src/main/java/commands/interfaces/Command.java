package commands.interfaces;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {
    boolean called(String[] Args, MessageReceivedEvent event);

    void action(String[] Args, MessageReceivedEvent event);

    void executed(boolean success, MessageReceivedEvent event);

    String help();

    String longhelp();

    String getCommandName();
}
