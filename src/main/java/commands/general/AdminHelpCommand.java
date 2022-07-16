package commands.general;

import commands.interfaces.AdminCommand;
import commands.interfaces.GeneralCommand;
import core.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// a lot of duplication with comHelp
public class AdminHelpCommand implements AdminCommand, GeneralCommand {
    private final String commandName = "ahelp";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCenturion(event);
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        StringBuilder coms = new StringBuilder();
        Map<String, List<String>> helpRepo = CommandHandler.commandHelpRepos.get("Admin");

        if (args.length > 0) {
            if (args[0].equals("general")) {
                /*for (int i = 0; i < CommandHandler.commandsAdminHelpGeneral.size(); i++) {
                    coms.append(CommandHandler.commandsAdminHelpGeneral.get(i));
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTitle("edbotJ General Admin Commands:");
                eb.setDescription("Required parameters: `<parameter>` , optional parameters: `[parameter]`");
                eb.addField("Page 1:", coms.toString(), false);

                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                EmbedBuilder ef = new EmbedBuilder();
                ef.setColor(new Color(3, 193, 19));
                ef.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                ef.setTitle("edbotJ:");
                ef.setDescription(":inbox_tray: The general admin commands have been sent to you via DM!");
                event.getTextChannel().sendMessage(ef.build()).queue();*/

                int totalFields = 1;
                LinkedList<LinkedList<String>> lHelpPages = new LinkedList<>();
                lHelpPages.add(new LinkedList<>());

                for (String s : helpRepo.get("General")) {
                    if (String.join("", lHelpPages.get(totalFields - 1)).length() + s.length() > 1024) {
                        totalFields++;
                        lHelpPages.add(new LinkedList<>());
                    }
                    lHelpPages.get(totalFields - 1).add(s);
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTitle("edbotJ General Admin Commands:");
                eb.setDescription("Required parameters: `<parameter>` , optional parameters: `[parameter]`");
                for (int i = 1; i <= lHelpPages.size(); i++) {
                    eb.addField("Page " + i + ":", String.join("", lHelpPages.get(i - 1)), false);
                }

                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                EmbedBuilder ef = new EmbedBuilder();
                ef.setColor(new Color(3, 193, 19));
                ef.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                ef.setTitle("edbotJ:");
                ef.setDescription(":inbox_tray: The general admin commands have been sent to you via DM!");
                event.getTextChannel().sendMessage(ef.build()).queue();
            } else if (args[0].equals("db")) {
                /*for (int i = 0; i < CommandHandler.commandsAdminHelpDB.size(); i++) {
                    coms.append(CommandHandler.commandsAdminHelpDB.get(i));
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTitle("edbotJ Database Admin Commands:");
                eb.setDescription("Required parameters: `<parameter>` , optional parameters: `[parameter]`");
                eb.addField("Page 1:", coms.toString(), false);

                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                EmbedBuilder ef = new EmbedBuilder();
                ef.setColor(new Color(3, 193, 19));
                ef.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                ef.setTitle("edbotJ:");
                ef.setDescription(":inbox_tray: The database admin commands have been sent to you via DM!");
                event.getTextChannel().sendMessage(ef.build()).queue();*/

                /*for (int i = 0; i < CommandHandler.commandsHelpDB.size(); i++) {
                    coms.append(CommandHandler.commandsHelpDB.get(i));
                }*/

                int totalFields = 1;
                LinkedList<LinkedList<String>> lHelpPages = new LinkedList<>();
                lHelpPages.add(new LinkedList<>());

                for (String s : helpRepo.get("DB")) {
                    if (String.join("", lHelpPages.get(totalFields - 1)).length() + s.length() > 1024) {
                        totalFields++;
                        lHelpPages.add(new LinkedList<>());
                    }
                    lHelpPages.get(totalFields - 1).add(s);
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTitle("edbotJ Database Admin Commands:");
                eb.setDescription("Required parameters: `<parameter>` , optional parameters: `[parameter]`");
                for (int i = 1; i <= lHelpPages.size(); i++) {
                    eb.addField("Page " + i + ":", String.join("", lHelpPages.get(i - 1)), false);
                }

                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                EmbedBuilder ef = new EmbedBuilder();
                ef.setColor(new Color(3, 193, 19));
                ef.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                ef.setTitle("edbotJ:");
                ef.setDescription(":inbox_tray: The database admin commands have been sent to you via DM!");
                event.getTextChannel().sendMessage(ef.build()).queue();
            }
        } else {
            CommandHandler.commandsAdminHelp.forEach(coms::append);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(3, 193, 19));
            eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
            eb.setTitle("edbotJ Admin Help:");
            eb.setDescription("Use `" + Settings.prefix + "ahelp [type]` to further define the area of admin commands you want help with. (e.g. `" + Settings.prefix + "ahelp db`)");
            eb.addField("Available admin `[type]` identifiers:", coms.toString(), false);
            event.getTextChannel().sendMessage(eb.build()).queue();
        }
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
        return "Shows a list of available admin commands.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
