package cn.hamster3.core.common.util;

import cn.hamster3.core.common.constant.ConstantObjects;
import com.google.gson.JsonObject;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class SerializeUtils {
    @NotNull
    public static JsonObject serializeTitle(@NotNull Title title) {
        JsonObject object = new JsonObject();
        object.add("title", GsonComponentSerializer.gson().serializeToTree(title.title()));
        object.add("subtitle", GsonComponentSerializer.gson().serializeToTree(title.subtitle()));
        Title.Times times = title.times();
        if (times != null) {
            object.add("times", serializeTitleTimes(times));
        }
        return object;
    }

    @NotNull
    public static Title deserializeTitle(@NotNull JsonObject object) {
        if (object.has("times")) {
            return Title.title(
                    ConstantObjects.GSON.fromJson(object.get("title"), Component.class),
                    ConstantObjects.GSON.fromJson(object.get("subtitle"), Component.class),
                    deserializeTitleTimes(object.getAsJsonObject("times"))
            );
        } else {
            return Title.title(
                    GsonComponentSerializer.gson().deserializeFromTree(object.get("title")),
                    GsonComponentSerializer.gson().deserializeFromTree(object.get("subtitle"))
            );
        }
    }

    @NotNull
    public static JsonObject serializeTitleTimes(@NotNull Title.Times times) {
        JsonObject object = new JsonObject();
        object.addProperty("fadeIn", times.fadeIn().toMillis());
        object.addProperty("stay", times.stay().toMillis());
        object.addProperty("fadeOut", times.fadeOut().toMillis());
        return object;
    }

    @NotNull
    public static Title.Times deserializeTitleTimes(@NotNull JsonObject object) {
        return Title.Times.times(
                Duration.ofMillis(object.get("fadeIn").getAsLong()),
                Duration.ofMillis(object.get("stay").getAsLong()),
                Duration.ofMillis(object.get("fadeOut").getAsLong())
        );
    }

    @NotNull
    public static JsonObject serializeSound(@NotNull Sound sound) {
        JsonObject object = new JsonObject();
        object.addProperty("key", sound.name().asString());
        object.addProperty("source", sound.source().name());
        object.addProperty("volume", sound.volume());
        object.addProperty("pitch", sound.pitch());
        return object;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public static Sound deserializeSound(@NotNull JsonObject object) {
        return Sound.sound(
                Key.key(object.get("key").getAsString()),
                Sound.Source.valueOf(object.get("source").getAsString()),
                object.get("volume").getAsFloat(),
                object.get("pitch").getAsFloat()
        );
    }
}
