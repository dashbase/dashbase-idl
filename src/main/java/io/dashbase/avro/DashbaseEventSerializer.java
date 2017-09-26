package io.dashbase.avro;

import java.io.IOException;

import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

public class DashbaseEventSerializer {
	private static final DecoderFactory DECODER_FACTORY =  new DecoderFactory();

	public static DashbaseEvent fromJson(String json) throws IOException {
		DashbaseEvent event = new DashbaseEvent();

        Decoder decoder = DECODER_FACTORY.jsonDecoder(event.getSchema(), json);

        SpecificDatumReader<DashbaseEvent> reader = new SpecificDatumReader<DashbaseEvent>(DashbaseEvent.class);

        return reader.read(event, decoder);
	}
}
