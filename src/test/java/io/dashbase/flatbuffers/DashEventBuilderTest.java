package io.dashbase.flatbuffers;

import io.dashbase.DashbaseSchema;
import io.dashbase.TestUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Map;

public class DashEventBuilderTest {

    static ObjectMapper mapper = new ObjectMapper();

    static byte[] buildFromJson(byte[] jsonBytes, DashbaseSchema schema, long timestamp) throws Exception {
        DashEventBuilder builder = DashEventBuilder.builder();
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
        DashEventBuilder builder = DashEventBuilder.builder();
        builder.addId("a", "1");
        builder.addMeta("b", "2");
        builder.addText("c", "3");
        builder.addNumber("d", 4);
        builder.addText("e", "5");
        builder.withTimeInMillis(6);
        builder.withOmitPayload(true);
        byte[] bytes = builder.build();

        ByteBuffer buf = java.nio.ByteBuffer.wrap(bytes);
        DashEvent event = DashEvent.getRootAsDashEvent(buf);

        Assert.assertEquals(6, event.timeInMillis(), 0);
        Assert.assertEquals(true, event.omitPayload());

        int numCols = event.columnsLength();
        Assert.assertEquals(5, numCols);

        for (int i = 0; i < numCols; ++i) {
            Column col = event.columns(i);
            String name = col.name();
            byte type = col.type();
            Object val = null;
            switch(type) {
                case Type.Text:
                    Map<String, String> textCols = builder.getTextCols();
                    val = textCols.get(name);
                    break;
                case Type.Meta:
                    Map<String, String> metaCols = builder.getMetaCols();
                    val = metaCols.get(name);
                    break;
                case Type.Id:
                    Map<String, String> idCols = builder.getIdCols();
                    val = idCols.get(name);
                    break;
                case Type.Double:
                    Map<String, Double> numberCols = builder.getNumberCols();
                    val = numberCols.get(name);
                    break;
                default:
                    Assert.fail("invalid type: " + type);
            }
            Assert.assertNotNull(val);

            if (val instanceof String) {
                Assert.assertEquals(col.strValue(), String.valueOf(val));
            } else if (val instanceof Number) {
                Assert.assertEquals(col.doubleValue(), ((Number)val).doubleValue(), 0.0);
            }
        }
    }

    @Test
    public void testOutputSize() throws Exception {
        byte[] jsonData = TestUtil.readTestFile();
        byte[] flatBuffer = buildFromJson(jsonData, TestUtil.TEST_SCHEMA, 5);
        System.out.println(jsonData.length);
        System.out.println(flatBuffer.length);
    }
}
