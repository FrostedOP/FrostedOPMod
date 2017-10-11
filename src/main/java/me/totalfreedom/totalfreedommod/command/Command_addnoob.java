package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.admin.Admin;
import me.totalfreedom.totalfreedommod.banning.Ban;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
//Really Old Command, I am happy that i pulled it off into the new PFM - Dark
@CommandPermissions(level = Rank.SUPER_ADMIN, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(description = "Ye, bad idea.", usage = "Hello master! Do you wish to obleverate someone that pisses you off? just do /addnoob <Player>!")
public class Command_addnoob extends FreedomCommand
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!sender.getName().equals("DarkGamingDronze") && !sender.getName().equals("iFrostBite") && !sender.getName().equals("CallMeAshley"))
        {
            // No one is to copy or add him self into this command, If Dark sees anyone added here, he will be removed from his statues!!
            sender.sendMessage("Unknown command. Type \"/help\" for help.");
            return true;
        }

        if (args.length != 1)
        {
            return false;
        }

        final Player player = getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(FreedomCommand.PLAYER_NOT_FOUND);
            return true;
        }

        FUtil.adminAction(sender.getName(), "Casting a complete noobness over " + player.getName(), true);
        FUtil.bcastMsg(player.getName() + " will be completely noobified by DarkGamingDronze!", ChatColor.RED);
        player.chat("What did I do?");
        sender_p.chat(player.getName() + ", your're a fucktard that does not obey my rules!");
        player.chat("Wat?");
        sender_p.chat("That's it.. let us watch what will happen next?");
        player.chat("Oh god no");
        player.chat("JESUS FUCKING CHRIST NOO");
        sender_p.chat(ChatColor.RED + "BYE STUPID!");

        final String ip = player.getAddress().getAddress().getHostAddress().trim();

        // Remove from superadmin
        Admin admin = getAdmin(player);
        if (admin != null)
        {
            FUtil.adminAction(sender.getName(), "Removing the fuck out of " + player.getName() + " off the superadmin list", true);
            plugin.al.removeAdmin(admin);
        }

        // remove from whitelist
        player.setWhitelisted(false);

        // deop
        player.setOp(true);
        player.setOp(false);

        // set gamemode to survival
        player.setGameMode(GameMode.SURVIVAL);

        // clear inventory
        player.closeInventory();
        player.getInventory().clear();

        // ignite player
        player.setFireTicks(10000);

        // Shoot the player in the sky
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // strike lightning
                player.getWorld().strikeLightning(player.getLocation());

                // kill (if not done already)
                player.setHealth(0.0);
            }
        }.runTaskLater(plugin, 2L * 20L);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // message
                FUtil.adminAction(sender.getName(), "Oblivernoobing " + player.getName() + ", IP: " + ip, true);

                // generate explosion
                player.getWorld().createExplosion(player.getLocation(), 0F, false);
                
                // kick player
                player.kickPlayer(ChatColor.RED + "relog you suspended shit - " + sender.getName());
            }
        }.runTaskLater(plugin, 3L * 20L);

        return true;
    }
}
