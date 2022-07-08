package commands.mariadb.devs;

import commands.interfaces.GeneralCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Secrets;
import util.SharedComRequirements;

import java.awt.*;

public class comVicari implements GeneralCommand {
    private final String commandName = "zones";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return !SharedComRequirements.checkSelf(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        StringBuilder vics = new StringBuilder();
        for (String key : Secrets.vicari.keySet()) {
            if (vics.toString().equals("")) {
                vics = new StringBuilder("Zones: `" + key + "`");
            } else {
                vics.append(", `").append(key).append("`");
            }
        }
        ErrorHandler.CustomEmbed(vics.toString(), new Color(3, 193, 19), event);
    }

    public static void AddVicari() {
        Secrets.vicari.put("a", "721305581805240410");
        Secrets.vicari.put("b", "608041250951528507");
        Secrets.vicari.put("c", "721305674541170818");
        Secrets.vicari.put("d", "608032654394261547");
        Secrets.vicari.put("machina", "478479243991318536");
        Secrets.vicari.put("artifex", "321670679743430657");
        AddVicariChans();
    }

    private static void AddVicariChans() {
        Secrets.vicariChans.put("a", "452848411876786187");
        Secrets.vicariChans.put("b", "455742250698407957");
        Secrets.vicariChans.put("c", "452848424207908864");
        Secrets.vicariChans.put("d", "452848440393728015");
        Secrets.vicariChans.put("machina", "322105225324527627");
        Secrets.vicariChans.put("artifex", "409151028362215424");
        Secrets.vicariChans.put("scriptor", "810768332910624779");
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
        return "Lists all current zones.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
