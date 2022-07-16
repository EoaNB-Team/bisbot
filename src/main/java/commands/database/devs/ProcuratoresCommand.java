package commands.database.devs;

import commands.interfaces.GeneralCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;

import java.awt.*;

public class ProcuratoresCommand implements GeneralCommand {
    private final String commandName = "jobs";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return !event.getAuthor().isBot();
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        StringBuilder procs = new StringBuilder();
        for (String key : Settings.procuratores.keySet()) {
            if (procs.toString().equals("")) {
                procs = new StringBuilder("Procuratores: `" + key + "`");
            } else {
                procs.append(", `").append(key).append("`");
            }
        }
        ErrorHandler.CustomEmbed(procs.toString(), new Color(3, 193, 19), event);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public static void initProcuratores() {
        Settings.procuratores.put("programmator", "319861468705325057");
        Settings.procuratores.put("historicus", "319861531879800832");
        Settings.procuratores.put("scriptor", "810765049840009237");
        Settings.procuratores.put("auditor", "810765455550840856");
    }

    @Override
    public String help() {
        return Settings.prefix + commandName;
    }

    @Override
    public String longhelp() {
        return "Lists all current job roles/procuratores.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
