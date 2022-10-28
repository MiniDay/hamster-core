package cn.hamster3.mc.plugin.core.common.data;

import cn.hamster3.mc.plugin.core.common.constant.CoreConstantObjects;
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
public class DisplayMessage {
    private Component message;
    private Component actionbar;
    private Title title;
    /**
     * <a href="https://minecraft.fandom.com/zh/wiki/Sounds.json">点击查看 minecraft 所有声音资源</a>
     */
    private Sound sound;

    public DisplayMessage() {
    }

    public static DisplayMessage message(@NotNull String message) {
        return new DisplayMessage().setMessage(message);
    }

    public static DisplayMessage message(@NotNull Component message) {
        return new DisplayMessage().setMessage(message);
    }

    public static DisplayMessage actionbar(@NotNull String message) {
        return new DisplayMessage().setActionBar(message);
    }

    public static DisplayMessage actionbar(@NotNull Component message) {
        return new DisplayMessage().setActionBar(message);
    }

    public static DisplayMessage title(@NotNull String title, @NotNull String subtitle) {
        return new DisplayMessage().setTitle(title, subtitle);
    }

    public static DisplayMessage title(@NotNull String title, @NotNull String subtitle, int fadeIn, int stay, int fadeOut) {
        return new DisplayMessage().setTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    public static DisplayMessage sound(@NotNull String sound) {
        return new DisplayMessage().setSound(sound);
    }

    public static DisplayMessage sound(@NotNull String sound, float volume, float pitch) {
        return new DisplayMessage().setSound(sound, volume, pitch);
    }

    public static DisplayMessage sound(@NotNull Sound sound) {
        return new DisplayMessage().setSound(sound);
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

    public void show(@NotNull Audience audience, @NotNull TextReplacementConfig replacement) {
        if (message != null) {
            audience.sendMessage(message.replaceText(replacement).compact());
        }
        if (actionbar != null) {
            audience.sendActionBar(actionbar.replaceText(replacement).compact());
        }
        if (title != null) {
            audience.showTitle(Title.title(
                    title.title().replaceText(replacement).compact(),
                    title.subtitle().replaceText(replacement).compact(),
                    title.times()
            ));
        }
        if (sound != null) {
            audience.playSound(sound);
        }
    }

    public void show(@NotNull Audience audience, @NotNull TextReplacementConfig... replacements) {
        if (message != null) {
            Component replacedMessage = message;
            for (TextReplacementConfig replacement : replacements) {
                replacedMessage = replacedMessage.replaceText(replacement);
            }
            audience.sendMessage(replacedMessage.compact());
        }
        if (actionbar != null) {
            Component replacedActionBar = actionbar;
            for (TextReplacementConfig replacement : replacements) {
                replacedActionBar = replacedActionBar.replaceText(replacement);
            }
            audience.sendActionBar(replacedActionBar.compact());
        }
        if (title != null) {
            Title replacedTitle = title;
            for (TextReplacementConfig replacement : replacements) {
                replacedTitle = Title.title(
                        title.title().replaceText(replacement).compact(),
                        title.subtitle().replaceText(replacement).compact(),
                        title.times()
                );
            }
            audience.showTitle(replacedTitle);
        }
        if (sound != null) {
            audience.playSound(sound);
        }
    }

    public JsonObject saveToJson() {
        JsonObject object = new JsonObject();
        if (message != null) {
            object.add("message", GsonComponentSerializer.gson().serializeToTree(message));
        }
        if (actionbar != null) {
            object.add("actionbar", GsonComponentSerializer.gson().serializeToTree(actionbar));
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
    public DisplayMessage setMessage(@NotNull String message) {
        this.message = Component.text(message);
        return this;
    }

    @NotNull
    public DisplayMessage setMessage(@NotNull Component message) {
        this.message = message.compact();
        return this;
    }

    @NotNull
    public DisplayMessage setActionBar(@NotNull String message) {
        this.actionbar = Component.text(message);
        return this;
    }

    @NotNull
    public DisplayMessage setActionBar(@NotNull Component message) {
        this.actionbar = message.compact();
        return this;
    }

    @NotNull
    public DisplayMessage setTitle(@NotNull String title, @NotNull String subtitle) {
        this.title = Title.title(Component.text(title), Component.text(subtitle));
        return this;
    }

    @NotNull
    public DisplayMessage setTitle(@NotNull String title, @NotNull String subtitle, int fadeIn, int stay, int fadeOut) {
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
    public DisplayMessage setTitle(@NotNull Component title, @NotNull Component subtitle) {
        this.title = Title.title(title.compact(), subtitle.compact());
        return this;
    }

    @NotNull
    public DisplayMessage setTitle(@NotNull Component title, @NotNull Component subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = Title.title(
                title.compact(),
                subtitle.compact(),
                Title.Times.times(
                        Ticks.duration(fadeIn),
                        Ticks.duration(stay),
                        Ticks.duration(fadeOut)
                )
        );
        return this;
    }

    @NotNull
    public DisplayMessage setTitle(@Nullable Title title) {
        this.title = title;
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public DisplayMessage setSound(@NotNull String sound) {
        this.sound = Sound.sound(Key.key(sound), Sound.Source.MASTER, 1, 1);
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public DisplayMessage setSound(@NotNull String namespace, @NotNull String path) {
        this.sound = Sound.sound(Key.key(namespace, path), Sound.Source.MASTER, 1, 1);
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public DisplayMessage setSound(@NotNull String sound, float volume, float pitch) {
        this.sound = Sound.sound(Key.key(sound), Sound.Source.MASTER, volume, pitch);
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public DisplayMessage setSound(@NotNull String namespace, @NotNull String value, float volume, float pitch) {
        this.sound = Sound.sound(Key.key(namespace, value), Sound.Source.MASTER, volume, pitch);
        return this;
    }

    @NotNull
    @SuppressWarnings("PatternValidation")
    public DisplayMessage setSound(@NotNull String namespace, @NotNull String value, @NotNull Sound.Source source, float volume, float pitch) {
        this.sound = Sound.sound(Key.key(namespace, value), source, volume, pitch);
        return this;
    }

    @NotNull
    public DisplayMessage setSound(@Nullable Sound sound) {
        this.sound = sound;
        return this;
    }

    @NotNull
    @SuppressWarnings("UnusedReturnValue")
    public DisplayMessage fromJson(@NotNull JsonElement element) {
        if (!element.isJsonObject()) {
            message = Component.text(element.toString());
            return this;
        }
        JsonObject object = element.getAsJsonObject();
        if (object.has("message")) {
            message = GsonComponentSerializer.gson().deserializeFromTree(object.get("message")).compact();
        }
        if (object.has("actionbar")) {
            actionbar = GsonComponentSerializer.gson().deserializeFromTree(object.get("actionbar")).compact();
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
        return CoreConstantObjects.GSON.toJson(this);
    }
}
