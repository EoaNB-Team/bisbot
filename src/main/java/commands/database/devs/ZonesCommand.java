package commands.database.devs;

import commands.interfaces.GeneralCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;

import java.awt.*;

public class ZonesCommand implements GeneralCommand {
    private final String commandName = "zones";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return !event.getAuthor().isBot();
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        StringBuilder vics = new StringBuilder();
        for (String key : Settings.zones.keySet()) {
            if (vics.toString().equals("")) {
                vics = new StringBuilder("Zones: `" + key + "`");
            } else {
                vics.append(", `").append(key).append("`");
            }
        }
        ErrorHandler.CustomEmbed(vics.toString(), new Color(3, 193, 19), event);
    }

    public static void initZones() {
        Settings.zones.put("a", "721305581805240410");
        Settings.zones.put("b", "608041250951528507");
        Settings.zones.put("c", "721305674541170818");
        Settings.zones.put("d", "608032654394261547");
		Settings.zones.put("e", "973234853162127420");
		Settings.zones.put("f", "988499655233003550");
		Settings.zones.put("g", "988500252803883008");
        Settings.zones.put("machina", "478479243991318536");
        Settings.zones.put("artifex", "321670679743430657");
        addZoneChannels();
    }

    private static void addZoneChannels() {
        Settings.zoneChannels.put("a", "452848411876786187");
        Settings.zoneChannels.put("b", "988502630252818482");
        Settings.zoneChannels.put("c", "452848424207908864");
        Settings.zoneChannels.put("d", "988502774532681801");
		Settings.zoneChannels.put("e", "556330458787479552");
		Settings.zoneChannels.put("f", "455742250698407957");
		Settings.zoneChannels.put("g", "452848440393728015");
        Settings.zoneChannels.put("artifex", "409151028362215424");
        Settings.zoneChannels.put("scriptor", "810768332910624779");
        Settings.zoneChannels.put("machina", "988880984822911076");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) { }

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
