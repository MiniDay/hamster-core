package cn.hamster3.mc.plugin.core.common.data;

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

    public static DisplayMessage actionbar(@NotNull String actionbar) {
        return new DisplayMessage().setActionBar(actionbar);
    }

    public static DisplayMessage actionbar(@NotNull Component actionbar) {
        return new DisplayMessage().setActionBar(actionbar);
    }

    public static DisplayMessage title(@Nullable String title, @Nullable String subtitle) {
        return new DisplayMessage().setTitle(title, subtitle);
    }

    public static DisplayMessage title(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
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
        copy().replace(replacement).show(audience);
    }

    public void show(@NotNull Audience audience, @NotNull TextReplacementConfig... replacements) {
        copy().replace(replacements).show(audience);
    }

    public DisplayMessage replace(@NotNull TextReplacementConfig replacement) {
        if (message != null) {
            message = message.replaceText(replacement).compact();
        }
        if (actionbar != null) {
            actionbar = actionbar.replaceText(replacement).compact();
        }
        if (title != null) {
            title = Title.title(
                    title.title().replaceText(replacement).compact(),
                    title.subtitle().replaceText(replacement).compact(),
                    title.times()
            );
        }
        return this;
    }

    public DisplayMessage replace(@NotNull TextReplacementConfig... replacements) {
        if (message != null) {
            for (TextReplacementConfig replacement : replacements) {
                message = message.replaceText(replacement).compact();
            }
        }
        if (actionbar != null) {
            for (TextReplacementConfig replacement : replacements) {
                actionbar = actionbar.replaceText(replacement).compact();
            }
        }
        if (title != null) {
            for (TextReplacementConfig replacement : replacements) {
                title = Title.title(
                        title.title().replaceText(replacement).compact(),
                        title.subtitle().replaceText(replacement).compact(),
                        title.times()
                );
            }
        }
        return this;
    }

    @NotNull
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
    public DisplayMessage setMessage(@Nullable String message) {
        this.message = message == null ? null : Component.text(message);
        return this;
    }

    @NotNull
    public DisplayMessage setMessage(@Nullable Component message) {
        this.message = message == null ? null : message.compact();
        return this;
    }

    @NotNull
    public DisplayMessage setActionBar(@Nullable String actionbar) {
        this.actionbar = actionbar == null ? null : Component.text(actionbar);
        return this;
    }

    @NotNull
    public DisplayMessage setActionBar(@Nullable Component actionbar) {
        this.actionbar = actionbar == null ? null : actionbar.compact();
        return this;
    }

    @NotNull
    public DisplayMessage setTitle(@Nullable String title, @Nullable String subtitle) {
        this.title = Title.title(
                title == null ? Component.empty() : Component.text(title),
                subtitle == null ? Component.empty() : Component.text(subtitle)
        );
        return this;
    }

    @NotNull
    public DisplayMessage setTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = Title.title(
                title == null ? Component.empty() : Component.text(title),
                subtitle == null ? Component.empty() : Component.text(subtitle),
                Title.Times.times(
                        Ticks.duration(fadeIn),
                        Ticks.duration(stay),
                        Ticks.duration(fadeOut)
                )
        );
        return this;
    }

    @NotNull
    public DisplayMessage setTitle(@Nullable Component title, @Nullable Component subtitle) {
        this.title = Title.title(
                title == null ? Component.empty() : title.compact(),
                subtitle == null ? Component.empty() : subtitle.compact()
        );
        return this;
    }

    @NotNull
    public DisplayMessage setTitle(@Nullable Component title, @Nullable Component subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = Title.title(
                title == null ? Component.empty() : title.compact(),
                subtitle == null ? Component.empty() : subtitle.compact(),
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
    public DisplayMessage setSound(@Nullable String sound) {
        this.sound = sound == null ? null : Sound.sound(Key.key(sound), Sound.Source.MASTER, 1, 1);
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

    @NotNull
    public DisplayMessage copy() {
        return new DisplayMessage()
                .setMessage(message)
                .setActionBar(actionbar)
                .setTitle(title)
                .setSound(sound);
    }

    @Override
    public String toString() {
        return saveToJson().toString();
    }
}
