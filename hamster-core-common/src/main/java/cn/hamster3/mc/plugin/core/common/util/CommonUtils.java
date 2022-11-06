package cn.hamster3.mc.plugin.core.common.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("unused")
public final class CommonUtils {
    private CommonUtils() {
    }

    public static void zipCompressionFolder(@NotNull File folder, @NotNull File zipFile) throws IOException {
        try (ZipOutputStream stream = new ZipOutputStream(Files.newOutputStream(zipFile.toPath()))) {
            putFileToZipStream(stream, "", folder);
        }
    }

    public static void putFileToZipStream(@NotNull ZipOutputStream stream, @NotNull String path, @NotNull File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                throw new IOException();
            }
            for (File subFile : files) {
                putFileToZipStream(stream, path + file.getName() + "/", subFile);
            }
            return;
        }
        ZipEntry entry = new ZipEntry(path + file.getName());
        stream.putNextEntry(entry);
        stream.write(Files.readAllBytes(file.toPath()));
        stream.closeEntry();
    }

    /**
     * 替换颜色代码
     *
     * @param string 要替换的字符串
     * @return 替换后的字符串
     */
    @Nullable
    public static String replaceColorCode(@Nullable String string) {
        if (string == null) return null;
        return string.replace("&", "§");
    }

    /**
     * 替换颜色代码
     * <p>
     * 添加这个方法是因为 ConfigurationSection 中的 getString 方法有 @Nullable 注解
     * <p>
     * 导致 idea 会弹出某些警告，让人非常不爽
     *
     * @param string       要替换的字符串
     * @param defaultValue 若 string 为空则使用该字符串
     * @return 替换后的字符串
     */
    @NotNull
    public static String replaceColorCode(@Nullable String string, @NotNull String defaultValue) {
        return replaceColorCode(Objects.requireNonNullElse(string, defaultValue));
    }

    /**
     * 替换颜色代码
     *
     * @param strings 要替换的字符串
     * @return 替换后的字符串
     */
    @NotNull
    public static ArrayList<String> replaceColorCode(@Nullable Iterable<String> strings) {
        ArrayList<String> list = new ArrayList<>();
        if (strings == null) return list;
        for (String s : strings) {
            list.add(replaceColorCode(s));
        }
        return list;
    }

    /**
     * 替换颜色代码
     *
     * @param strings 要替换的字符串
     */
    public static void replaceColorCode(@NotNull String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace("&", "§");
        }
    }

    @NotNull
    public static String[] replaceStringList(@NotNull String[] strings, @NotNull String key, @NotNull String value) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace(key, value);
        }
        return strings;
    }

    @NotNull
    public static List<String> replaceStringList(@NotNull Iterable<String> strings, @NotNull String key, @NotNull String value) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : strings) {
            list.add(s.replace(key, value));
        }
        return list;
    }

    @NotNull
    public static List<String> replaceStringList(@NotNull List<String> strings, @NotNull String key, @NotNull String value) {
        strings.replaceAll(s -> s.replace(key, value));
        return strings;
    }

    public static boolean startsWithIgnoreCase(@NotNull String string, @NotNull String prefix) {
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static boolean endsWithIgnoreCase(@NotNull String string, @NotNull String suffix) {
        int strOffset = string.length() - suffix.length();
        return string.regionMatches(true, strOffset, suffix, 0, suffix.length());
    }

    @NotNull
    public static ArrayList<String> startsWith(@NotNull Iterable<String> strings, @NotNull String with) {
        ArrayList<String> list = new ArrayList<>();
        for (String string : strings) {
            if (string.startsWith(with)) {
                list.add(string);
            }
        }
        return list;
    }

    @NotNull
    public static ArrayList<String> endsWith(@NotNull Iterable<String> strings, @NotNull String with) {
        ArrayList<String> list = new ArrayList<>();
        for (String string : strings) {
            if (string.endsWith(with)) {
                list.add(string);
            }
        }
        return list;
    }

    @NotNull
    public static ArrayList<String> startsWithIgnoreCase(@NotNull Iterable<String> strings, @NotNull String start) {
        ArrayList<String> list = new ArrayList<>();
        for (String string : strings) {
            if (startsWithIgnoreCase(string, start)) {
                list.add(string);
            }
        }
        return list;
    }

    @NotNull
    public static ArrayList<String> endsWithIgnoreCase(@NotNull Iterable<String> strings, @NotNull String end) {
        ArrayList<String> list = new ArrayList<>();
        for (String string : strings) {
            if (endsWithIgnoreCase(string, end)) {
                list.add(string);
            }
        }
        return list;
    }


}
