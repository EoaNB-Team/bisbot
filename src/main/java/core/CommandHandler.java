package core;

import commands.CommandBucket;
import commands.interfaces.Command;
import util.Secrets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class CommandHandler {
    public static CommandBucket commandBucket;

    public static final List<String> commandsHelp = List.of(
        "`general` : Collection of general commands everyone can use.\n",
        "`db` : Collection of commands connected to the EoaNB database. Can mostly only be used by devs.\n"
    );
    private static final List<String> commandsHelpGeneral = new ArrayList<>();
    private static final List<String> commandsHelpDB = new ArrayList<>();

    public static final List<String> commandsAdminHelp = List.of(
            "`general` : Collection of general admin commands.\n",
            "`db` : Collection of admin commands connected to the EoaNB database.\n"
    );
    private static final List<String> commandsAdminHelpGeneral = new ArrayList<>();
    private static final List<String> commandsAdminHelpDB = new ArrayList<>();

    private static final Map<String, List<String>> adminHelpRepos = Map.ofEntries(
        entry("General", commandsAdminHelpGeneral),
        entry("DB", commandsAdminHelpDB)
    );
    private static final Map<String, List<String>> helpRepos = Map.ofEntries(
        entry("General", commandsHelpGeneral),
        entry("DB", commandsHelpDB)
    );
    public static final Map<String, Map<String, List<String>>> commandHelpRepos = Map.ofEntries(
        entry("Admin", adminHelpRepos),
        entry("Dev", helpRepos)
    );

    public static void HandleCommand(CommandParser.CommandContainer cmd) {
        Map<String, Command> commands = commandBucket.getCommands();
        if (commands.containsKey(cmd.invoke)) {
            boolean canExecute = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if (canExecute) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
            }
            commands.get(cmd.invoke).executed(canExecute, cmd.event);
        } else {
            ErrorHandler.CustomEmbedError("Command *" + Secrets.prefix + cmd.invoke + "* does not exist.", cmd.event);
        }
    }
}
