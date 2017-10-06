package io.dashbase.avro;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DashbaseEventBuilder {
    private Map<String, String> textCols = new HashMap<>();
    private Map<String, Double> numberCols = new HashMap<>();
    private Map<String, String> metaCols = new HashMap<>();
    private Map<String, String> idCols = new HashMap<>();
    private long timeInMillis = -1L;
    private boolean omitPayload = false;

    public DashbaseEventBuilder withTimeInMillis(long timeInMillis) {
        Preconditions.checkArgument(timeInMillis >= 0L);
        this.timeInMillis = timeInMillis;
        return this;
    }

    public DashbaseEventBuilder withOmitPayload(boolean omitPayload) {
        this.omitPayload = omitPayload;
        return this;
    }

    public DashbaseEventBuilder withMetaColumns(Map<String, String> metaCols) {
        Preconditions.checkNotNull(metaCols);
        this.metaCols = metaCols;
        return this;
    }

    public DashbaseEventBuilder addMeta(String name, String val) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(val);
        this.metaCols.put(name, val);
        return this;
    }

    public DashbaseEventBuilder withTextColumns(Map<String, String> textCols) {
        Preconditions.checkNotNull(textCols);
        this.textCols = textCols;
        return this;
    }

    public DashbaseEventBuilder addText(String name, String val) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(val);
        this.textCols.put(name, val);
        return this;
    }

    public DashbaseEventBuilder withIdColumns(Map<String, String> idCols) {
        Preconditions.checkNotNull(idCols);
        this.idCols = idCols;
        return this;
    }

    public DashbaseEventBuilder addId(String name, String val) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(val);
        this.idCols.put(name, val);
        return this;
    }

    public DashbaseEventBuilder withNumberColumns(Map<String, Double> numberCols) {
        Preconditions.checkNotNull(numberCols);
        this.numberCols = numberCols.entrySet().stream()
            .filter(e -> isValidDouble(e.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return this;
    }

    public DashbaseEventBuilder addNumber(String name, double val) {
        Preconditions.checkNotNull(name);
        if (!isValidDouble(val)) {
            return this;
        }
        this.numberCols.put(name, val);
        return this;
    }

    public DashbaseEvent build() {
        Preconditions.checkArgument(timeInMillis >= 0L, "Need to specify timestamp");
        DashbaseEvent event = new DashbaseEvent();
        event.setTimeInMillis(timeInMillis);
        event.setOmitPayload(omitPayload);
        event.setIdColumns(idCols);
        event.setMetaColumns(metaCols);
        event.setTextColumns(textCols);
        event.setNumberColumns(numberCols);
        return event;
    }

    private static boolean isValidDouble(double d) {
        // NaN and Infinite doubles are serialized as String "NaN" and "Infinite",
        // which causes AvroTypeException
        // https://issues.apache.org/jira/browse/AVRO-2032
        // Ignore those values for now.
        return !Double.isNaN(d) && !Double.isInfinite(d);
    }
}
