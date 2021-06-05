package commands;

import core.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Secrets;
import util.SharedComRequirements;

import java.awt.*;
import java.util.LinkedList;

public class comHelp implements Command{
    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkSelf(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        StringBuilder coms = new StringBuilder();

        if (Args.length > 0) {
            if (Args[0].equals("general")) {
                /*for (int i = 0; i < CommandHandler.commandsHelpGeneral.size(); i++) {
                    coms.append(CommandHandler.commandsHelpGeneral.get(i));
                }*/

                int totalFields = 1;
                LinkedList<LinkedList<String>> lHelpPages = new LinkedList<>();
                lHelpPages.add(new LinkedList<>());

                for (String s : CommandHandler.commandsHelpGeneral) {
                    if (String.join("", lHelpPages.get(totalFields - 1)).length() + s.length() > 1024) {
                        totalFields++;
                        lHelpPages.add(new LinkedList<>());
                    }
                    lHelpPages.get(totalFields - 1).add(s);
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTitle("edbotJ General Commands:");
                eb.setDescription("Required parameters: `<parameter>` , optional parameters: `[parameter]`");
                for (int i = 1; i <= lHelpPages.size(); i++) {
                    eb.addField("Page " + i + ":", String.join("", lHelpPages.get(i - 1)), false);
                }

                /*EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTitle("edbotJ General Commands:");
                eb.setDescription("Required parameters: `<parameter>` , optional parameters: `[parameter]`");
                eb.addField("Page 1:", coms.toString(), false);*/

                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                EmbedBuilder ef = new EmbedBuilder();
                ef.setColor(new Color(3, 193, 19));
                ef.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                ef.setTitle("edbotJ:");
                ef.setDescription(":inbox_tray: The general commands have been sent to you via DM!");
                event.getTextChannel().sendMessage(ef.build()).queue();
            } else if (Args[0].equals("db")) {
                /*for (int i = 0; i < CommandHandler.commandsHelpDB.size(); i++) {
                    coms.append(CommandHandler.commandsHelpDB.get(i));
                }*/

                int totalFields = 1;
                LinkedList<LinkedList<String>> lHelpPages = new LinkedList<>();
                lHelpPages.add(new LinkedList<>());

                for (String s : CommandHandler.commandsHelpDB) {
                    if (String.join("", lHelpPages.get(totalFields - 1)).length() + s.length() > 1024) {
                        totalFields++;
                        lHelpPages.add(new LinkedList<>());
                    }
                    lHelpPages.get(totalFields - 1).add(s);
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTitle("edbotJ Database Commands:");
                eb.setDescription("Required parameters: `<parameter>` , optional parameters: `[parameter]`");
                for (int i = 1; i <= lHelpPages.size(); i++) {
                    eb.addField("Page " + i + ":", String.join("", lHelpPages.get(i - 1)), false);
                }

                /*EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTitle("edbotJ Database Commands:");
                eb.setDescription("Required parameters: `<parameter>` , optional parameters: `[parameter]`");
                eb.addField("Page 1:", coms.toString(), false);*/

                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                EmbedBuilder ef = new EmbedBuilder();
                ef.setColor(new Color(3, 193, 19));
                ef.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                ef.setTitle("edbotJ:");
                ef.setDescription(":inbox_tray: The database commands have been sent to you via DM!");
                event.getTextChannel().sendMessage(ef.build()).queue();
            }
        } else {
            for (int i = 0; i < CommandHandler.commandsHelp.size(); i++) {
                coms.append(CommandHandler.commandsHelp.get(i));
            }

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(3, 193, 19));
            eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
            eb.setTitle("edbotJ Help:");
            eb.setDescription("Use `" + Secrets.prefix + "help [type]` to further define the area of commands you want help with. (e.g. `" + Secrets.prefix + "help db`)");
            eb.addField("Available `[type]` identifiers:", coms.toString(), false);
            event.getTextChannel().sendMessage(eb.build()).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return Secrets.prefix + "help [type]";
    }

    @Override
    public String longhelp() {
        return "Shows a list of publicly available commands. Use `[type]` to further define the area of commands you want help with.";
    }
}
