package io.dashbase.avro;

import java.io.IOException;

import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

public class DashbaseEventSerializer {
  public static DashbaseEvent fromJson(String json) throws IOException {
    DashbaseEvent event = new DashbaseEvent();

    Decoder decoder = DecoderFactory.get().jsonDecoder(event.getSchema(), json);

    SpecificDatumReader<DashbaseEvent> reader = new SpecificDatumReader<DashbaseEvent>(DashbaseEvent.class);

    return reader.read(event, decoder);
  }
}
