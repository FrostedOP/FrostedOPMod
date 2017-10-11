package me.totalfreedom.totalfreedommod.rank;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum Title implements Displayable
{

    DEVELOPER("a", "Developer", ChatColor.DARK_PURPLE, "Dev"),
    MASTER_BUILDER("a", "Master Builder", ChatColor.DARK_AQUA, "Master-Builder"),
    EFD("the", "Executive Forums Developer", ChatColor.DARK_PURPLE, "Exec Forum Dev"),
    PFDEV("a", "Freedom Develoepr", ChatColor.DARK_PURPLE, "PF Dev"),
    SYS("a", "Executive Admin", ChatColor.DARK_RED, "Sys Admin"),
    EXEC("an", "Executive Admin", ChatColor.YELLOW, "Executive"),
    LEADDEV("the", "Executive Lead Developer!", ChatColor.DARK_PURPLE, "§5§oLead Developer"),
    COOWNER("the", "Co Owner!", ChatColor.YELLOW, "Co-Owner"),
    ASHLEY("the", "Executive Admin Officer", ChatColor.RED, "EAO"),
    HOST("is our", "Sexy Server Host", ChatColor.DARK_RED, "Server Host"),
    FROST("the", "Owner and Founder of FrostedOp", ChatColor.GREEN, "§a§lFounder"),
    SUSPENDED("a", "Suspended asshole", ChatColor.GRAY, "Op"),
    OWNER("the", "Owner", ChatColor.BLUE, "Owner");
    

    private final String determiner;
    @Getter
    private final String name;
    @Getter
    private final String tag;
    @Getter
    private final String coloredTag;
    @Getter
    private final ChatColor color;

    private Title(String determiner, String name, ChatColor color, String tag)
    {
        this.determiner = determiner;
        this.name = name;
        this.tag = "(" + tag + ")";
        this.coloredTag = ChatColor.DARK_GRAY + "(" + color + tag + ChatColor.DARK_GRAY + ")" + color;
        this.color = color;
    }

    @Override
    public String getColoredName()
    {
        return color + name;
    }

    @Override
    public String getColoredLoginMessage()
    {
        return determiner + " " + color + ChatColor.ITALIC + name;
    }

}
