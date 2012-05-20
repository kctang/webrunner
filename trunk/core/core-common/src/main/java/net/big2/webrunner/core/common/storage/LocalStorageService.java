package net.big2.webrunner.core.common.storage;

import org.apache.commons.io.FileUtils;

import java.io.*;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.io.IOUtils.*;

public class LocalStorageService implements StorageService {
    private String directory;
    private String baseUrl;

    public LocalStorageService(String directory, String baseUrl) {
        this.directory = directory;
        this.baseUrl = baseUrl;
    }

    @Override
    public void store(String type, String id, byte[] data) throws StorageServiceException {
        OutputStream out = null;
        try {
            if (data == null) {
                throw new IOException("Cannot store null data");
            }

            String filename = getFilename(type, id);
            FileUtils.forceMkdir(new File(getDirectory(type)));

            out = new BufferedOutputStream(new FileOutputStream(filename));
            write(data, out);

        } catch (IOException e) {
            throw new StorageServiceException(
                    format("Error storing data [type: %s, id: %s]", type, id), e);
        } finally {
            closeQuietly(out);
        }
    }

    @Override
    public byte[] load(String type, String id) throws StorageServiceException {
        InputStream in = null;

        try {
            String filename = getFilename(type, id);
            in = new BufferedInputStream(new FileInputStream(filename));

            return toByteArray(in);

        } catch (IOException e) {
            throw new StorageServiceException(
                    format("Error loading data [type: %s, id: %s]", type, id), e);
        } finally {
            closeQuietly(in);
        }
    }

    @Override
    public void delete(String type, String id) throws StorageServiceException {
        String filename = getFilename(type, id);
        //noinspection ResultOfMethodCallIgnored
        new File(filename).delete();
    }

    @Override
    public boolean exists(String type, String id) throws StorageServiceException {
        String filename = getFilename(type, id);
        return new File(filename).exists();
    }

    @Override
    public String getUrl(String type, String id) throws StorageServiceException {
        return baseUrl + type + "/" + id;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    String getFilename(String type, String id) throws StorageServiceException {
        checkArgument(!type.contains("..") && !id.contains(".."));

        return getDirectory(type) + File.separator + id;
    }

    String getDirectory(String type) {
        checkArgument(directory != null);

        return directory + File.separator + type;
    }

    public String getDirectory() {
        return directory;
    }
}
