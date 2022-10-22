package cn.hamster3.mc.plugin.core.bukkit.command.lore;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.bukkit.command.ParentCommand;
import cn.hamster3.mc.plugin.core.common.util.CommonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ParentLoreCommand extends ParentCommand {
    public static final ParentLoreCommand INSTANCE = new ParentLoreCommand("lore", HamsterCorePlugin.COMMAND_EXECUTOR);

    private ParentLoreCommand(@NotNull String name, @Nullable ParentCommand parent) {
        super(name, parent);
        addChildCommand(LoreAddCommand.INSTANCE);
        addChildCommand(LoreRemoveCommand.INSTANCE);
        addChildCommand(LoreSetCommand.INSTANCE);
        addChildCommand(LoreClearCommand.INSTANCE);
        addChildCommand(LoreNameCommand.INSTANCE);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommonUtils.replaceColorCode(args);
        return super.onCommand(sender, command, label, args);
    }
}
