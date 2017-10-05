package io.dashbase.avro;

import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

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

    @Test
    public void testNaN() throws Exception {
	    DashbaseEvent event = new DashbaseEventBuilder().withTimeInMillis(123).addNumber("test", Double.NaN).build();
      assertFalse(event.getNumberColumns().containsKey("test"));

      event = new DashbaseEventBuilder().withTimeInMillis(123).addNumber("test", Double.NaN).build();
      assertFalse(event.getNumberColumns().containsKey("test"));
    }
}
