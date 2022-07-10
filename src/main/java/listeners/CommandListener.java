package listeners;

import core.CommandHandler;
import core.CommandParser;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import util.Settings;

public class CommandListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        if ((!event.isFromType(ChannelType.PRIVATE) || event.getMessage().getAuthor().getId().equals(Settings.OWNER))
            && event.getMessage().getContentRaw().startsWith(Settings.prefix)
            && !event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
            CommandHandler.HandleCommand(CommandParser.parser(event.getMessage().getContentRaw(), event));
        }
    }
}
