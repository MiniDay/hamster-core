package cn.hamster3.mc.plugin.core.bukkit.command.core;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.bukkit.command.ParentCommand;

public class ParentCoreCommand extends ParentCommand {
    public static final ParentCoreCommand INSTANCE = new ParentCoreCommand();

    private ParentCoreCommand() {
        super(HamsterCorePlugin.getInstance(), "core");
        addChildCommand(YamlCommand.INSTANCE);
        addChildCommand(GCCommand.INSTANCE);
        addChildCommand(BlockInfoCommand.INSTANCE);
    }
}
