package cn.hamster3.mc.plugin.core.common.constant;

import cn.hamster3.mc.plugin.core.common.data.DisplayMessage;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.UUID;
import java.util.concurrent.*;

@SuppressWarnings("unused")
public interface CoreConstantObjects {
    /**
     * Minecraft 默认指定的空 UUID
     */
    UUID NIL_UUID = new UUID(0L, 0L);

    /**
     * GSON 工具
     */
    Gson GSON = new GsonBuilder()
            .registerTypeAdapter(DisplayMessage.class, MessageTypeAdapter.INSTANCE)
            .create();

    /**
     * GSON 工具，会使用格式化输出、且解析中包含null参数
     */
    Gson GSON_HUMAN = new GsonBuilder()
            .registerTypeAdapter(DisplayMessage.class, MessageTypeAdapter.INSTANCE)
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    /**
     * JSON 解析器
     */
    JsonParser JSON_PARSER = new JsonParser();

    /**
     * 异步线程
     */
    ExecutorService WORKER_EXECUTOR = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60, TimeUnit.MINUTES, new SynchronousQueue<>(), new APIThreadFactory("HamsterCore - Executor"));
    /**
     * 调度器线程
     */
    ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(1, new APIThreadFactory("HamsterCore - Scheduler"));

    class APIThreadFactory implements ThreadFactory {
        private final String name;
        private int threadID;

        public APIThreadFactory(String name) {
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
}
