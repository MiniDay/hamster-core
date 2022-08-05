package cn.hamster3.core.bukkit.api;

import cn.hamster3.core.common.api.CommonAPI;

@SuppressWarnings("unused")
public class BukkitAPI extends CommonAPI {
    public static BukkitAPI getInstance() {
        return (BukkitAPI) instance;
    }

}
