package me.totalfreedom.totalfreedommod;

import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FSync;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static me.totalfreedom.totalfreedommod.util.FUtil.playerMsg;

public class ChatManager extends FreedomService
{

    public static ChatColor acc = ChatColor.GOLD;
    public static boolean acr = false;
    public static boolean acn = false;

    public ChatManager(TotalFreedomMod plugin)
    {
        super(plugin);
    }

    @Override
    protected void onStart()
    {
    }

    @Override
    protected void onStop()
    {
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChatFormat(AsyncPlayerChatEvent event)
    {
        try
        {
            handleChatEvent(event);
        }
        catch (Exception ex)
        {
            FLog.severe(ex);
        }
    }

    private void handleChatEvent(AsyncPlayerChatEvent event)
    {
        final Player player = event.getPlayer();
        String message = event.getMessage().trim();

        // Truncate messages that are too long - 256 characters is vanilla client max
        if (message.length() > 256)
        {
            message = message.substring(0, 256);
            FSync.playerMsg(player, "Message was shortened because it was too long to send.");
        }

        // Check for caps
        if (message.length() >= 6)
        {
            int caps = 0;
            for (char c : message.toCharArray())
            {
                if (Character.isUpperCase(c))
                {
                    caps++;
                }
            }
            if (((float) caps / (float) message.length()) > 0.65) //Compute a ratio so that longer sentences can have more caps.
            {
                message = message.toLowerCase();
            }
        }

        // Check for adminchat
        final FPlayer fPlayer = plugin.pl.getPlayerSync(player);
        if (fPlayer.inAdminChat())
        {
            FSync.adminChatMessage(player, message);
            event.setCancelled(true);
            return;
        }

        event.setMessage(message);
        String format = ChatColor.translateAlternateColorCodes('&', "%1$s &6»&7 %2$s");
        String tag = fPlayer.getTag();
        if (tag != null && !tag.isEmpty())
        {
            format = tag.replace("%", "%%") + " " + format;
        }
        event.setFormat(format);
    }

    public void adminChat(CommandSender sender, String message)
    {
        String name = sender.getName() + " " + plugin.rm.getDisplay(sender).getColoredTag() + ChatColor.WHITE;
        FLog.info("[AC] " + name + ": " + message);

        for (Player player : server.getOnlinePlayers())
        {
            if (plugin.al.isAdmin(player))
            {
                ChatColor cc = acc;
                if (acr == true)
                {
                    cc = FUtil.randomChatColor();
                    player.sendMessage("[" + ChatColor.AQUA + "AC" + ChatColor.WHITE + "] " + ChatColor.DARK_RED + name + ": " + cc + message);
                }
                else if (acn == true)
                {
                    String rm = "";
                    for (char c : message.toCharArray())
                    {
                        ChatColor rc = FUtil.randomChatColor();
                        rm = rm + rc + c;
                    }
                    player.sendMessage("[" + ChatColor.AQUA + "AC" + ChatColor.WHITE + "] " + ChatColor.DARK_RED + name + ": " + rm);
                }
                else
                {
                    player.sendMessage("[" + ChatColor.AQUA + "AC" + ChatColor.WHITE + "] " + ChatColor.DARK_RED + name + ": " + cc + message);
                }

            }
        }
    }

    public void reportAction(Player reporter, Player reported, String report)
    {
        for (Player player : server.getOnlinePlayers())
        {
            if (plugin.al.isAdmin(player))
            {
                playerMsg(player, FUtil.colorize(
                        "&8&m---------------&8[ &c&lREPORT &8]&8&m---------------\n"
                        + "&8- &c&lReporter: &e&l" + reporter.getName()
                        + "\n&8- &c&lReported: &e&l" + reported.getName()
                        + "\n&8- &c&lReported for: &e&l" + report
                        + "\n&8&m----------------------------------------"));
            }
        }
    }
}
