package core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Secrets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParser {
    public static CommandContainer parser(String raw, MessageReceivedEvent event) {
        String beheaded = raw.replaceFirst(Secrets.prefix, ""); // prefix
        beheaded = beheaded.replaceAll(" +", " "); // multiple spaces
        beheaded = beheaded.replaceAll("[\\u201C\\u201D\\u0022\\u00AB\\u00BB\\u201E\\u201F]", "\""); // unicode quotes

        String[] splitBeheaded;
        if (raw.contains("\"") && raw.contains(" ")) {
            String[] splitTemp = beheaded.split("\"");
            String[] splitTempZero = splitTemp[0].split(" ");

            List<String> list = new ArrayList<>(Arrays.asList(splitTempZero));
            for (String sd: splitTemp) {
                if (!sd.equals(" ") && !sd.equals(splitTemp[0])) {
                    list.add(sd);
                }
            }
            splitBeheaded = list.toArray(new String[0]);
        } else if (raw.contains("\"")) {
            splitBeheaded = beheaded.replace(" \"", "\"").split("\"");
        } else {
            splitBeheaded = beheaded.split(" ");
        }
        String invoke = splitBeheaded[0];
        ArrayList<String> split = new ArrayList<>(Arrays.asList(splitBeheaded));
        String[] Args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(Args);

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, Args, event);
    }

    public static class CommandContainer {
        public final String raw;
        public final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        public final String[] args;
        public final MessageReceivedEvent event;

        public CommandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent e) {
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = e;
        }
    }
}
