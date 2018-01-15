package io.dashbase.flatbuffers;

import io.dashbase.DashbaseSchema;
import io.dashbase.TestUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Map;

public class DashbaseEventBuilderTest {

    static ObjectMapper mapper = new ObjectMapper();

    static byte[] buildFromJson(byte[] jsonBytes, DashbaseSchema schema, long timestamp) throws Exception {
        DashbaseEventBuilder builder = DashbaseEventBuilder.builder();
        builder.withTimeInMillis(timestamp);
        builder.withOmitPayload(false);
        Map<String, Object> map = (Map<String, Object>) mapper.readValue(jsonBytes, Map.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String name = entry.getKey();
            Object val = entry.getValue();
            if (schema.text.contains(name)) {
                builder.addText(name, String.valueOf(val));
            } else if (schema.meta.contains(name)) {
                builder.addMeta(name, String.valueOf(val));
            } else if (schema.numeric.contains(name)) {
                if (val instanceof Number) {
                    double numVal = ((Number)val).doubleValue();
                    builder.addNumber(name, numVal);
                }
            }
        }
        return builder.build();
    }

    @Test
    public void build() throws Exception {
        DashbaseEventBuilder builder = new DashbaseEventBuilder();
        builder.addId("a", "1");
        builder.addMeta("b", "2");
        builder.addText("c", "3");
        builder.addNumber("d", 4);
        builder.addText("e", "5");
        builder.withTimeInMillis(6);
        builder.withOmitPayload(true);
        byte[] bytes = builder.build();

        ByteBuffer buf = java.nio.ByteBuffer.wrap(bytes);
        DashbaseEvent event = DashbaseEvent.getRootAsDashbaseEvent(buf);

        Assert.assertEquals("a", event.idCols(0).key());
        Assert.assertEquals("1", event.idCols(0).value());
        Assert.assertEquals("b", event.metaCols(0).key());
        Assert.assertEquals("2", event.metaCols(0).value());
        Assert.assertEquals("c", event.textCols(0).key());
        Assert.assertEquals("3", event.textCols(0).value());
        Assert.assertEquals("d", event.numberCols(0).key());
        Assert.assertEquals(4, event.numberCols(0).value(), 0);
        Assert.assertEquals("e", event.textCols(1).key());
        Assert.assertEquals("5", event.textCols(1).value());
        Assert.assertEquals(6, event.timeInMillis(), 0);
        Assert.assertEquals(true, event.omitPayload());

    }

    @Test
    public void testOutputSize() throws Exception {
        byte[] jsonData = TestUtil.readTestFile();
        byte[] flatBuffer = buildFromJson(jsonData, TestUtil.TEST_SCHEMA, 5);
        System.out.println(jsonData.length);
        System.out.println(flatBuffer.length);
    }
}