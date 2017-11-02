package io.dashbase.avro;

import java.nio.ByteBuffer;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DashbaseEventBuilderTest {

    @Test
    public void testSerialziation() throws Exception {
        DashbaseEventBuilder eventBuilder =
                new DashbaseEventBuilder()
                .withTimeInMillis(1234567L)
                .addMeta("tags", "green")
                .addNumber("num", 1234.0)
                .addText("text", "dashbase is cool");

        DashbaseEvent event = eventBuilder.build();

        ByteBuffer byteBuffer = event.toByteBuffer();
        byte[] bytes = byteBuffer.array();

        Assert.assertNotNull(bytes);
        assertTrue(bytes.length > 0);

        DashbaseEvent newEvent = DashbaseEvent.fromByteBuffer(ByteBuffer.wrap(bytes));
        assertEquals(1234567L, newEvent.getTimeInMillis().longValue());

        Map<String, String> metaColumns = newEvent.getMetaColumns();
        assertEquals(1,  metaColumns.size());
        assertEquals("green", metaColumns.get("tags"));

        Map<String, Double> numberColumns = newEvent.getNumberColumns();
        assertEquals(1,  numberColumns.size());
        assertEquals(1234.0, numberColumns.get("num").doubleValue(), 0.0);

        Map<String, String> textColumns = newEvent.getTextColumns();
        assertEquals(1,  textColumns.size());
        assertEquals("dashbase is cool", textColumns.get("text"));
    }

    @Test
    public void testNull() {
        DashbaseEventBuilder builder = DashbaseEventBuilder.builder();

        DashbaseEvent event = builder.withTimeInMillis(100L)
            .addMeta(null, "val")
            .addMeta("meta", null)
            .addMeta(null, null)
            .addText(null, "val")
            .addText("text", null)
            .addText(null, null)
            .addNumber(null, 0.0)
            .build();

        assertEquals(100L, event.getTimeInMillis().longValue());
        assertTrue(event.getTextColumns().isEmpty());
        assertTrue(event.getMetaColumns().isEmpty());
        assertTrue(event.getNumberColumns().isEmpty());
    }
}