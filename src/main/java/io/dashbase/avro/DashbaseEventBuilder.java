package io.dashbase.avro;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DashbaseEventBuilder {
    private Map<String, String> textCols = new HashMap<>();
    private Map<String, Double> numberCols = new HashMap<>();
    private Map<String, String> metaCols = new HashMap<>();
    private Map<String, String> idCols = new HashMap<>();
    private long timeInMillis = 0;
    private ByteBuffer payload = ByteBuffer.wrap(new byte[0]);
    private boolean usePayload = false;
    private boolean omitPayload = false;

    public DashbaseEventBuilder withTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
        return this;
    }

    public DashbaseEventBuilder withUsePayload(boolean usePayload) {
        this.usePayload = usePayload;
        return this;
    }

    public DashbaseEventBuilder withOmitPayload(boolean omitPayload) {
        this.omitPayload = omitPayload;
        return this;
    }

    public DashbaseEventBuilder withPayload(byte[] payload) {
        if (payload != null) {
            this.payload = ByteBuffer.wrap(payload);
        }
        return this;
    }

    public DashbaseEventBuilder withPayloadBuffer(ByteBuffer payloadBuffer) {
        if (payloadBuffer != null) {
            this.payload = payloadBuffer;
        }
        return this;
    }

    public DashbaseEventBuilder withMetaColumns(Map<String, String> metaCols) {
        if (metaCols != null) {
            this.metaCols = metaCols;
        }
        return this;
    }

    public DashbaseEventBuilder addMeta(String name, String val) {
        this.metaCols.put(name, val);
        return this;
    }

    public DashbaseEventBuilder withTextColumns(Map<String, String> textCols) {
        if (textCols != null) {
            this.textCols = textCols;
        }
        return this;
    }

    public DashbaseEventBuilder addText(String name, String val) {
        this.textCols.put(name, val);
        return this;
    }

    public DashbaseEventBuilder withIdColumns(Map<String, String> idCols) {
        if (idCols != null) {
            this.idCols = idCols;
        }
        return this;
    }

    public DashbaseEventBuilder addId(String name, String val) {
        this.idCols.put(name, val);
        return this;
    }

    public DashbaseEventBuilder withNumberColumns(Map<String, Double> numberCols) {
        if (numberCols != null) {
            this.numberCols = numberCols;
        }
        return this;
    }

    public DashbaseEventBuilder addNumber(String name, double val) {
        this.numberCols.put(name, val);
        return this;
    }

    public DashbaseEvent build() {
        DashbaseEvent event = new DashbaseEvent();
        event.setTimeInMillis(timeInMillis);
        event.setPayload(payload);
        event.setOmitPayload(omitPayload);
        event.setUsePayload(usePayload);
        event.setIdColumns(idCols);
        event.setMetaColumns(metaCols);
        event.setTextColumns(textCols);
        event.setNumberColumns(numberCols);
        return event;
    }
}
