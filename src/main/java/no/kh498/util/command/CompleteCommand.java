package no.kh498.util.command;

import com.google.common.base.Preconditions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class CompleteCommand extends HostCommand implements TabCompleter {

    private static final Logger logger = LoggerFactory.getLogger(CompleteCommand.class);

    /**
     * Register this command to the plugin
     *
     * @param plugin
     *     The plugin to register this command to
     * @param command
     *     The command to register
     */
    public CompleteCommand(JavaPlugin plugin, String command) {
        super(null);
        Preconditions.checkNotNull(plugin, "A valid java plugin instance is needed to register this command");
        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        logger.trace("tab complete event called");

        SubCommand currSubCommand = this;
        int argsId = 0;

        //get the sub command to tab-complete
        for (String arg : args) {
            logger.trace("currSubCommand is " + currSubCommand.getClass().getSimpleName());
            logger.trace("next arg is '" + arg + "'");
            SubCommand subCmd = currSubCommand.getSubCommands().get(arg);
            if (currSubCommand.getSubCommands() == null || subCmd == null) {
                break;
            }

            argsId++;
            currSubCommand = subCmd;
        }

        logger.trace("final subcommand is " + currSubCommand.getClass().getSimpleName());

        if (args.length == argsId) {
            if (args.length == 0) {
                return new ArrayList<>(getSubCommands().keySet());
            }
            else {
                return new ArrayList<>(currSubCommand.getParent().getSubCommands().keySet());
            }

        }

        List<String> suggestions = new ArrayList<>();
        for (String subCommand : currSubCommand.getSubCommands().keySet()) {
            if (subCommand.startsWith(args[argsId])) {
                suggestions.add(subCommand);
            }
        }
        return suggestions;
    }
}