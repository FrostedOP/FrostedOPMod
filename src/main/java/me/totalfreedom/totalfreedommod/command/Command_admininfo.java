package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.NON_OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Shows information about applying for adminstrative status", aliases = "ai", usage = "/<command> [off]")
public class Command_admininfo extends FreedomCommand
{
    
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (ConfigEntry.ALLOW_AI.getBoolean())
        {
            msg(FUtil.colorize("&8&m--------------&8[ &b&lHow to apply for SA! &8]&8&m--------------"));
            msg(FUtil.colorize("&8- &b&lMake sure you have a forum account on our forums frostedop.boards.net"));
            msg(FUtil.colorize("&8- &b&lOnce you register, make sure you meet all the requirements"));
            msg(FUtil.colorize("&8- &b&lGood luck on your application, " + sender.getName() + "!"));
            msg(FUtil.colorize("&8&m----------------------------------------------------"));
        }else {
            msg(FUtil.colorize("&7Applications are currently on hold, you can still join the forums frostedop.boards.net"));
        }

    	return true;
    }
}