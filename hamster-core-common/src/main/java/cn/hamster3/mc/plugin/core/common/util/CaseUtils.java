package cn.hamster3.mc.plugin.core.common.util;

@SuppressWarnings("unused")
public final class CaseUtils {
    private CaseUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T caseObject(Object o) {
        return (T) o;
    }
}
