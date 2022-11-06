package cn.hamster3.mc.plugin.core.bukkit.command.lore;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.bukkit.command.ParentCommand;
import cn.hamster3.mc.plugin.core.common.util.CommonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class ParentLoreCommand extends ParentCommand {
    public static final ParentLoreCommand INSTANCE = new ParentLoreCommand();

    public ParentLoreCommand() {
        super(HamsterCorePlugin.getInstance(), "lore");
        addChildCommand(LoreAddCommand.INSTANCE);
        addChildCommand(LoreRemoveCommand.INSTANCE);
        addChildCommand(LoreSetCommand.INSTANCE);
        addChildCommand(LoreClearCommand.INSTANCE);
        addChildCommand(LoreNameCommand.INSTANCE);
        addChildCommand(LoreCustomModelDataCommand.INSTANCE);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        CommonUtils.replaceColorCode(args);
        return super.onCommand(sender, command, label, args);
    }
}
