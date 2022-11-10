package cn.hamster3.mc.plugin.core.bukkit.util;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    public static final ItemStackAdapter INSTANCE = new ItemStackAdapter();

    private ItemStackAdapter() {
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) {
            return null;
        }
        return BukkitSerializeUtils.deserializeItemStack(json.getAsString());
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        String s = BukkitSerializeUtils.serializeItemStack(src);
        if (s == null) {
            return JsonNull.INSTANCE;
        }
        return new JsonPrimitive(s);
    }
}
