package io.dashbase;

import com.google.common.collect.ImmutableSet;

import java.io.File;
import java.io.FileInputStream;

public class TestUtil {

    public static final File ResourcesDirectory = new File("src/test/resources");
    public static final File TEST_DATA = new File(ResourcesDirectory, "test.json");

    public static DashbaseSchema TEST_SCHEMA = new DashbaseSchema();

    static {
        TEST_SCHEMA.meta = ImmutableSet.of("host", "response");
        TEST_SCHEMA.text = ImmutableSet.of("agent", "request");
        TEST_SCHEMA.numeric = ImmutableSet.of("bytesSent");
    }

    public static byte[] readTestFile() throws Exception {
        byte[] data = null;
        int len = (int) TEST_DATA.length();
        try (FileInputStream fin = new FileInputStream(TEST_DATA)) {
            data = new byte[len];
            fin.read(data);
        }
        return data;
    }
}
