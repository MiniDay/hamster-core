package cn.hamster3.mc.plugin.core.common.util;

import cn.hamster3.mc.plugin.core.common.data.DisplayMessage;
import com.google.gson.*;

import java.lang.reflect.Type;

public class MessageTypeAdapter implements JsonSerializer<DisplayMessage>, JsonDeserializer<DisplayMessage> {
    public static final MessageTypeAdapter INSTANCE = new MessageTypeAdapter();

    private MessageTypeAdapter() {
    }

    @Override
    public DisplayMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new DisplayMessage().fromJson(json);
    }

    @Override
    public JsonElement serialize(DisplayMessage src, Type typeOfSrc, JsonSerializationContext context) {
        return src.saveToJson();
    }
}
