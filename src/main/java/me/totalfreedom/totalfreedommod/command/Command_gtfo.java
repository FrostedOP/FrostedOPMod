package me.totalfreedom.totalfreedommod.command;

import java.text.SimpleDateFormat;
import me.totalfreedom.totalfreedommod.banning.Ban;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import net.pravian.aero.util.Ips;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.SUPER_ADMIN, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(description = "Makes someone GTFO (deop and ip ban by username).", usage = "/<command> <partialname> <rb time> <reason>")
public class Command_gtfo extends FreedomCommand
{
    private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        final Player player = getPlayer(args[0]);

        if (player == null)
        {
            msg(FreedomCommand.PLAYER_NOT_FOUND, ChatColor.RED);
            return true;
        }

        String reason = null;
        if (args.length >= 2)
        {
            reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        }

        FUtil.bcastMsg(player.getName() + " has been a VERY naughty, naughty boy.", ChatColor.RED);

        // Undo WorldEdits
        try
        {
            plugin.web.undo(player, 15);
        }
        catch (NoClassDefFoundError ex)
        {
        }
        
        // if they are a imposter don't rb them
        if (!plugin.al.isAdminImpostor(player)) 
        {
            // cheak if coreprotect is enabled if not use tfm rb
            if (server.getPluginManager().isPluginEnabled("CoreProtect"))
            {
                server.dispatchCommand(sender, "co rb u:" + player.getName() + " t:24h r:global #silent");
            } else {
                plugin.rb.rollback(player.getName());
            }
        }else {
            // do nothing.
        }
        
        // Deop
        player.setOp(false);

        // Gamemode suvival
        player.setGameMode(GameMode.SURVIVAL);

        // Clear inventory
        player.getInventory().clear();

        // Strike with lightning
        final Location targetPos = player.getLocation();
        for (int x = -1; x <= 1; x++)
        {
            for (int z = -1; z <= 1; z++)
            {
                final Location strike_pos = new Location(targetPos.getWorld(), targetPos.getBlockX() + x, targetPos.getBlockY(), targetPos.getBlockZ() + z);
                targetPos.getWorld().strikeLightning(strike_pos);
            }
        }

        String ip = FUtil.getFuzzyIp(Ips.getIp(player));

        // Broadcast
        final StringBuilder bcast = new StringBuilder()
                .append(ChatColor.RED)
                .append("Banning: ")
                .append(player.getName())
                .append(", IP: ")
                .append(ip);
        if (reason != null)
        {
            bcast.append(" - Reason: ").append(ChatColor.YELLOW).append(reason);
        }
        FUtil.bcastMsg(bcast.toString());

        // Ban player
        plugin.bm.addBan(Ban.forPlayerFuzzy(player, sender, null, reason));

        // Kick player
        player.kickPlayer(ChatColor.RED + "GTFO");

        return true;
    }
}
