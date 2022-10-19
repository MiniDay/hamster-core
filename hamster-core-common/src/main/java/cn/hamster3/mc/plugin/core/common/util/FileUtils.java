package cn.hamster3.mc.plugin.core.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("unused")
public class FileUtils {
    @SuppressWarnings("IOStreamConstructor")
    public static void zipCompressionFolder(File folder, File zipFile) throws IOException {
        ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(zipFile));
        putFileToZipStream(stream, "", folder);
        stream.close();
    }

    public static void putFileToZipStream(ZipOutputStream stream, String path, File file) throws IOException {
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
}
