package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * See https://github.com/TotalFreedom/License - This file may not be edited or removed.
 */
@CommandPermissions(level = Rank.NON_OP, source = SourceType.BOTH)
@CommandParameters(description = "Shows information about FrostedInfoMod or reloads it", usage = "/<command> [reload]", aliases = "fopm,frostedopmod")
public class Command_frostedinfomod extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 1)
        {
            if (!args[0].equals("reload"))
            {
                return false;
            }

            if (!plugin.al.isAdmin(sender))
            {
                noPerms();
                return true;
            }

            plugin.config.load();
            plugin.services.stop();
            plugin.services.start();

            final String message = String.format("%s v%s reloaded.",
                    TotalFreedomMod.pluginName,
                    TotalFreedomMod.pluginVersion);

            msg(message);
            FLog.info(message);
            return true;
        }

        TotalFreedomMod.BuildProperties build = TotalFreedomMod.build;
        msg(FUtil.colorize("&8&m--------------------------------"));
        msg(FUtil.colorize("&b&lThis is FrostedOPMod!"));
        msg(FUtil.colorize("&3&lVersion: &b1.1.0"));
        msg(FUtil.colorize("&3&lMade by: &bSavnith"));
        msg(FUtil.colorize("&8&m--------------------------------"));

        return true;
    }
}
