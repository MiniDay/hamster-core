package cn.hamster3.mc.plugin.core.bukkit.util;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import com.comphenix.protocol.utility.StreamSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.logging.Level;

@SuppressWarnings("unused")
public final class BukkitSerializeUtils {
    private BukkitSerializeUtils() {
    }

    @Nullable
    public static String serializeItemStack(@Nullable ItemStack stack) {
        if (BukkitUtils.isEmptyItemStack(stack)) {
            return null;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            HamsterCorePlugin.getInstance().getLogger().warning("ProtocolLib 前置插件未启用, 无法序列化物品！");
            return null;
        }
        try {
            return StreamSerializer.getDefault().serializeItemStack(stack);
        } catch (IOException e) {
            HamsterCorePlugin.getInstance().getLogger().log(Level.WARNING, "序列化物品 " + stack + " 时出错!", e);
            return null;
        }
    }

    @Nullable
    public static ItemStack deserializeItemStack(@Nullable String s) {
        if (s == null) {
            return null;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            HamsterCorePlugin.getInstance().getLogger().warning("ProtocolLib 前置插件未启用, 无法反序列化物品！");
            return null;
        }
        try {
            return StreamSerializer.getDefault().deserializeItemStack(s);
        } catch (Exception e) {
            HamsterCorePlugin.getInstance().getLogger().log(Level.WARNING, "反序列化物品 " + s + " 时出错!", e);
            return null;
        }
    }

    @Nullable
    public static JsonObject serializePotionEffect(@Nullable PotionEffect effect) {
        if (effect == null) {
            return null;
        }
        try {
            JsonObject object = new JsonObject();
            object.addProperty("type", effect.getType().getName());
            object.addProperty("duration", effect.getDuration());
            object.addProperty("amplifier", effect.getAmplifier());
            return object;
        } catch (Exception e) {
            HamsterCorePlugin.getInstance().getLogger().log(Level.WARNING, "序列化药水效果 " + effect + " 时出错!", e);
        }
        return null;
    }

    @Nullable
    public static PotionEffect deserializePotionEffect(@Nullable JsonElement element) {
        if (element == null || !element.isJsonObject()) {
            return null;
        }
        JsonObject effectObject = element.getAsJsonObject();
        try {
            //noinspection ConstantConditions
            return new PotionEffect(
                    PotionEffectType.getByName(effectObject.get("type").getAsString()),
                    effectObject.get("duration").getAsInt(),
                    effectObject.get("amplifier").getAsInt()
            );
        } catch (Exception e) {
            HamsterCorePlugin.getInstance().getLogger().log(Level.WARNING, "反序列化药水效果 " + element + " 时出错!", e);
            return null;
        }
    }
}
