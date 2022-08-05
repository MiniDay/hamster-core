package cn.hamster3.plugin.core.common.api;

@SuppressWarnings("unused")
public abstract class CommonAPI {
    protected static CommonAPI instance;

    public static CommonAPI getInstance() {
        return instance;
    }

    public void reportError(String apiKey, String projectID, Throwable exception) {

    }

    public void reportFile(String apiKey, String projectID, String filename) {
    }
}
