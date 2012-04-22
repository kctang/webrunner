package net.big2.webrunner.core.common;

import java.io.InputStream;

public interface StorageService {
    void store(String key, String value);

    void store(String key, InputStream data);

    InputStream loadAsOutputStream(String key);

    byte[] loadAsBytes(String key);

    String loadAsString(String key);
}
