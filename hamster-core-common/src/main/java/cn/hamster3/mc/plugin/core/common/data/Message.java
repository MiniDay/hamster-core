package cn.hamster3.mc.plugin.core.common.data;

import cn.hamster3.mc.plugin.core.common.constant.ConstantObjects;
import cn.hamster3.mc.plugin.core.common.util.SerializeUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class Message {
    private Component message;
    private Component actionbar;
    private Title title;
    /**
     * <a href="https://minecraft.fandom.com/zh/wiki/Sounds.json">点击查看 minecraft 所有声音资源</a>
     */
    private Sound sound;

    public Message() {
    }

    public void show(@NotNull Audience audience) {
        if (message != null) {
            audience.sendMessage(message);
        }
        if (actionbar != null) {
            audience.sendActionBar(actionbar);
        }
        if (title != null) {
            audience.showTitle(title);
        }
        if (sound != null) {
            audience.playSound(sound);
        }
    }

    public void show(@NotNull Audience audience, TextReplacementConfig replacement) {
        if (message != null) {
            audience.sendMessage(message.compact().replaceText(replacement));
        }
        if (actionbar != null) {
            audience.sendActionBar(actionbar.replaceText(replacement));
        }
        if (title != null) {
            audience.showTitle(Title.title(
                    title.title().replaceText(replacement),
                    title.subtitle().replaceText(replacement),
                    title.times()
            ));
        }
        if (sound != null) {
            audience.playSound(sound);
        }
    }

    public JsonObject saveToJson() {
        JsonObject object = new JsonObject();
        if (message != null) {
            object.add("message", GsonComponentSerializer.gson().serializeToTree(message.compact()));
        }
        if (actionbar != null) {
            object.add("actionbar", GsonComponentSerializer.gson().serializeToTree(actionbar.compact()));
        }
        if (title != null) {
            object.add("title", SerializeUtils.serializeTitle(title));
        }
        if (sound != null) {
            object.add("sound", SerializeUtils.serializeSound(sound));
        }
        return object;
    }

    @NotNull
    public Message message(@NotNull String message) {
        this.message = Component.text(message);
        return this;
    }

    @NotNull
    public Message message(@NotNull Component message) {
        this.message = message;
        return this;
    }

    @NotNull
    public Message actionbar(@NotNull String message) {
        this.actionbar = Component.text(message);
        return this;
    }

    @NotNull
    public Message actionbar(@NotNull Component message) {
        this.actionbar = message;
        return this;
    }

    @NotNull
    public Message title(@NotNull String title, @NotNull String subtitle) {
        this.title = Title.title(Component.text(title), Component.text(subtitle));
        return this;
    }

    @NotNull
    public Message title(@NotNull String title, @NotNull String subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = Title.title(
                Component.text(title),
                Component.text(subtitle),
                Title.Times.times(
                        Ticks.duration(fadeIn),
                        Ticks.duration(stay),
                        Ticks.duration(fadeOut)
                )
        );
        return this;
    }

    @NotNull
    public Message title(@NotNull Component title, @NotNull Component subtitle) {
        this.title = Title.title(title, subtitle);
        return this;
    }

    @NotNull
    public Message title(@NotNull Component title, @NotNull Component subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = Title.title(
                title,
                subtitle,
                Title.Times.times(
                        Ticks.duration(fadeIn),
                        Ticks.duration(stay),
                        Ticks.duration(fadeOut)
                )
        );
        return this;
    }

    @NotNull
    public Message title(@Nullable Title title) {
        this.title = title;
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public Message sound(@NotNull String sound) {
        this.sound = Sound.sound(Key.key(sound), Sound.Source.MASTER, 1, 1);
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public Message sound(@NotNull String namespace, @NotNull String path) {
        this.sound = Sound.sound(Key.key(namespace, path), Sound.Source.MASTER, 1, 1);
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public Message sound(@NotNull String sound, float volume, float pitch) {
        this.sound = Sound.sound(Key.key(sound), Sound.Source.MASTER, volume, pitch);
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public Message sound(@NotNull String namespace, @NotNull String value, float volume, float pitch) {
        this.sound = Sound.sound(Key.key(namespace, value), Sound.Source.MASTER, volume, pitch);
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public Message sound(@NotNull String namespace, @NotNull String value, @NotNull Sound.Source source, float volume, float pitch) {
        this.sound = Sound.sound(Key.key(namespace, value), source, volume, pitch);
        return this;
    }

    @NotNull
    public Message sound(@Nullable Sound sound) {
        this.sound = sound;
        return this;
    }

    @NotNull
    @SuppressWarnings("UnusedReturnValue")
    public Message json(@NotNull JsonElement element) {
        if (!element.isJsonObject()) {
            message = Component.text(element.toString());
            return this;
        }
        JsonObject object = element.getAsJsonObject();
        if (object.has("message")) {
            message = GsonComponentSerializer.gson().deserializeFromTree(object.get("message"));
        }
        if (object.has("actionbar")) {
            actionbar = GsonComponentSerializer.gson().deserializeFromTree(object.get("actionbar"));
        }
        if (object.has("title")) {
            title = SerializeUtils.deserializeTitle(object.getAsJsonObject("title"));
        }
        if (object.has("sound")) {
            sound = SerializeUtils.deserializeSound(object.getAsJsonObject("sound"));
        }
        return this;
    }

    @Override
    public String toString() {
        return ConstantObjects.GSON.toJson(this);
    }
}
