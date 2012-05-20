package net.big2.webrunner.core.common.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.junit.Assert.*;

public class LocalStorageServiceTest {
    File testDir;
    LocalStorageService storageService;
    private static final String BASE_URL = "baseUrl";

    @Before
    public void setUp() throws Exception {
        testDir = computeTestDataRoot(getClass());
        deleteDirectory(testDir);

        storageService = new LocalStorageService(testDir.getAbsolutePath(), BASE_URL);
    }

    @After
    public void tearDown() throws Exception {
        deleteDirectory(testDir);
    }

    @Test
    public void basic_ok() throws StorageServiceException {
        byte[] expected = "My Sample Data".getBytes();

        // store
        storageService.store("binary", "1", expected);
        // load
        assertArrayEquals(expected, storageService.load("binary", "1"));

        // exists
        assertTrue(storageService.exists("binary", "1"));

        // delete
        storageService.delete("binary", "1");
        assertFalse(storageService.exists("binary", "1"));
    }
    
    @Test
    public void pojo_ok() {
        assertEquals(BASE_URL, storageService.getBaseUrl());
    }
    
    @Test
    public void thatGetUrlWorks() throws StorageServiceException {
        String expected = BASE_URL + "bob/123";

        assertEquals(expected, storageService.getUrl("bob", "123"));
    }

    @Test(expected = StorageServiceException.class)
    public void thatStoreNullValueThrowsException() throws StorageServiceException {
        storageService.store("bob", "123", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullDirectory_throwsException() throws StorageServiceException {
        storageService = new LocalStorageService(null, null);
        storageService.store("bob", "123", "abc".getBytes());
    }

    @Test(expected = StorageServiceException.class)
    public void loadInvalidId_throwsException() throws StorageServiceException {
        storageService.load("abc", "123");
    }

    private File computeTestDataRoot(Class anyTestClass) {
        String clsUri = anyTestClass.getName().replace('.', '/') + ".class";
        URL url = anyTestClass.getClassLoader().getResource(clsUri);
        String clsPath = url.getPath();
        File root = new File(clsPath.substring(0, clsPath.length() - clsUri.length()));
        return new File(root.getParentFile(), "test-data");
    }
}

