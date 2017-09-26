package io.dashbase.avro;

import org.junit.Test;

import junit.framework.Assert;

public class DashbaseEventSerializerTest {

	@Test
	public void testSerialization() throws Exception {
        DashbaseEventBuilder builder = new DashbaseEventBuilder();
        DashbaseEvent event = builder
                .withTimeInMillis(13)
                .addNumber("number", 13)
                .addText("text", "brown fox")
                .addMeta("color", "red")
                .addMeta("color", "green")
                .withOmitPayload(true).build();

        String jsonString = event.toString();

        DashbaseEvent event2 = DashbaseEventSerializer.fromJson(jsonString);
        Assert.assertEquals(jsonString, event2.toString());
    }
}
