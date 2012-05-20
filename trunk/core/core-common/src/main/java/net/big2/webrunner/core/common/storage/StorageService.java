package net.big2.webrunner.core.common.storage;

public interface StorageService {
    void store(String type, String id, byte[] data) throws StorageServiceException;

    byte[] load(String type, String id) throws StorageServiceException;

    void delete(String type, String id) throws StorageServiceException;

    boolean exists(String type, String id) throws StorageServiceException;

    String getUrl(String type, String id) throws StorageServiceException;

    String getBaseUrl();
}
