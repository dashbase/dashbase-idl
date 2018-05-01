package io.dashbase.avro;

import io.dashbase.AbstractDashbaseEventBuilder;

public class DashbaseEventBuilder extends AbstractDashbaseEventBuilder<DashbaseEvent, DashbaseEventBuilder> {
    public static DashbaseEventBuilder builder() {
        return new DashbaseEventBuilder();
    }

    @Override
    public DashbaseEvent build() {
        DashbaseEvent event = new DashbaseEvent();
        event.setTimeInMillis(timeInMillis);
        event.setOmitPayload(omitPayload);
        event.setIdColumns(idCols);
        event.setMetaColumns(metaCols);
        event.setTextColumns(textCols);
        event.setNumberColumns(numberCols);
        event.setRaw(raw);
        return event;
    }
}
