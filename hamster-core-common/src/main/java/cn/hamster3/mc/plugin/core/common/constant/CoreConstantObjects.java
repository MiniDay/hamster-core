package cn.hamster3.mc.plugin.core.common.constant;

import cn.hamster3.mc.plugin.core.common.data.DisplayMessage;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

@SuppressWarnings("unused")
public abstract class CoreConstantObjects {
    /**
     * 异步线程
     */
    public static final ExecutorService WORKER_EXECUTOR = Executors.newCachedThreadPool(new NamedThreadFactory("HamsterCore - Executor"));
    /**
     * 调度器线程
     */
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors
            .newScheduledThreadPool(1, new NamedThreadFactory("HamsterCore - Scheduler"));
    /**
     * GSON 工具
     */
    public static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(DisplayMessage.class, MessageTypeAdapter.INSTANCE)
            .create();
    /**
     * GSON 工具，会使用格式化输出、且解析中包含null参数
     */
    public static Gson GSON_HUMAN = new GsonBuilder()
            .registerTypeAdapter(DisplayMessage.class, MessageTypeAdapter.INSTANCE)
            .serializeNulls()
            .setPrettyPrinting()
            .create();
}

class NamedThreadFactory implements ThreadFactory {
    private final String name;
    private int threadID;

    public NamedThreadFactory(String name) {
        this.name = name;
        threadID = 0;
    }

    @Override
    public Thread newThread(@NotNull Runnable runnable) {
        threadID++;
        return new Thread(runnable, name + "#" + threadID);
    }
}

class MessageTypeAdapter implements JsonSerializer<DisplayMessage>, JsonDeserializer<DisplayMessage> {
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
