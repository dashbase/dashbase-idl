package io.dashbase.avro;

import java.nio.ByteBuffer;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class DashbaseEventBuilderTest {

    @Test
    public void testSerialziation() throws Exception {
        DashbaseEventBuilder eventBuilder =
                new DashbaseEventBuilder()
                .withPayload(new byte[0])
                .withTimeInMillis(1234567L)
                .addMeta("tags", "green")
                .addNumber("num", 1234.0)
                .addText("text", "dashbase is cool");

        DashbaseEvent event = eventBuilder.build();

        ByteBuffer byteBuffer = event.toByteBuffer();
        byte[] bytes = byteBuffer.array();

        Assert.assertNotNull(bytes);
        Assert.assertTrue(bytes.length > 0);

        DashbaseEvent newEvent = DashbaseEvent.fromByteBuffer(ByteBuffer.wrap(bytes));
        Assert.assertEquals(1234567L, newEvent.getTimeInMillis().longValue());

        Map<String, String> metaColumns = newEvent.getMetaColumns();
        Assert.assertEquals(1,  metaColumns.size());
        Assert.assertEquals("green", metaColumns.get("tags"));

        Map<String, Double> numberColumns = newEvent.getNumberColumns();
        Assert.assertEquals(1,  numberColumns.size());
        Assert.assertEquals(1234.0, numberColumns.get("num").doubleValue(), 0.0);

        Map<String, String> textColumns = newEvent.getTextColumns();
        Assert.assertEquals(1,  textColumns.size());
        Assert.assertEquals("dashbase is cool", textColumns.get("text"));

    }
}