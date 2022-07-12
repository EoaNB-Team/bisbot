package commands.mariadb.devs;

import commands.interfaces.GeneralCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;

import java.awt.*;

public class ZonesCommand implements GeneralCommand {
    private final String commandName = "zones";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return !event.getAuthor().isBot();
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        StringBuilder vics = new StringBuilder();
        for (String key : Settings.vicari.keySet()) {
            if (vics.toString().equals("")) {
                vics = new StringBuilder("Zones: `" + key + "`");
            } else {
                vics.append(", `").append(key).append("`");
            }
        }
        ErrorHandler.CustomEmbed(vics.toString(), new Color(3, 193, 19), event);
    }

    public static void initVicari() {
        Settings.vicari.put("a", "721305581805240410");
        Settings.vicari.put("b", "608041250951528507");
        Settings.vicari.put("c", "721305674541170818");
        Settings.vicari.put("d", "608032654394261547");
        Settings.vicari.put("machina", "478479243991318536");
        Settings.vicari.put("artifex", "321670679743430657");
        AddVicariChans();
    }

    private static void AddVicariChans() {
        Settings.vicariChans.put("a", "452848411876786187");
        Settings.vicariChans.put("b", "455742250698407957");
        Settings.vicariChans.put("c", "452848424207908864");
        Settings.vicariChans.put("d", "452848440393728015");
        Settings.vicariChans.put("machina", "322105225324527627");
        Settings.vicariChans.put("artifex", "409151028362215424");
        Settings.vicariChans.put("scriptor", "810768332910624779");
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
        return "Lists all current zones.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
