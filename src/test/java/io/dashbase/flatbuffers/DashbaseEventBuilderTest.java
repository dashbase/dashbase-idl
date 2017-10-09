package io.dashbase.flatbuffers;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class DashbaseEventBuilderTest {
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

}