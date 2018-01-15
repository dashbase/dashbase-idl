package io.dashbase.flatbuffers;

import com.google.common.base.Preconditions;
import com.google.flatbuffers.FlatBufferBuilder;
import io.dashbase.AbstractDashbaseEventBuilder;

import java.util.Map;

public class DashEventBuilder extends AbstractDashbaseEventBuilder<byte[], DashEventBuilder> {
    public static DashEventBuilder builder() {
        return new DashEventBuilder();
    }

    private DashEventBuilder() {

    }

    @Override
    public byte[] build() {
        Preconditions.checkArgument(timeInMillis >= 0L, "Need to specify timestamp");
        FlatBufferBuilder builder = new FlatBufferBuilder(1024);

        int colsOffsets = DashEvent.createColumnsVector(builder,
                makeFbsCols(builder, textCols, metaCols, idCols, numberCols));

        DashEvent.startDashEvent(builder);
        DashEvent.addTimeInMillis(builder, timeInMillis);
        DashEvent.addOmitPayload(builder, omitPayload);
        DashEvent.addColumns(builder, colsOffsets);

        int event = DashEvent.endDashEvent(builder);
        builder.finish(event);
        return builder.sizedByteArray();
    }

    private static int makeStringColumn(FlatBufferBuilder builder, String name, String val, byte type) {
        int nameOffset = builder.createString(name);
        int valOffset = builder.createString(val);
        Column.startColumn(builder);
        Column.addName(builder, nameOffset);
        Column.addType(builder, type);
        Column.addStrValue(builder, valOffset);
        return Column.endColumn(builder);
    }

    private static int makeDoubleColumn(FlatBufferBuilder builder, String name, Double val) {
        int nameOffset = builder.createString(name);
        Column.startColumn(builder);
        Column.addName(builder, nameOffset);
        Column.addType(builder, Type.Double);
        Column.addDoubleValue(builder, val.doubleValue());
        return Column.endColumn(builder);
    }

    private static int[] makeFbsCols(FlatBufferBuilder builder,
                                     Map<String, String> textMap,
                                     Map<String, String> metaMap,
                                     Map<String, String> idMap,
                                     Map<String, Double> numberMap) {
        int numColsTotal = textMap.size() + metaMap.size() + idMap.size() + numberMap.size();
        int[] fbsCols = new int[numColsTotal];
        int index = 0;

        // Text
        for (Map.Entry<String, String> entry : textMap.entrySet()) {
            fbsCols[index++] = makeStringColumn(builder, entry.getKey(), entry.getValue(), Type.Text);
        }

        // Meta
        for (Map.Entry<String, String> entry : metaMap.entrySet()) {
            fbsCols[index++] = makeStringColumn(builder, entry.getKey(), entry.getValue(), Type.Meta);
        }

        // Id
        for (Map.Entry<String, String> entry : idMap.entrySet()) {
            fbsCols[index++] = makeStringColumn(builder, entry.getKey(), entry.getValue(), Type.Id);
        }

        // Number
        for (Map.Entry<String, Double> entry : numberMap.entrySet()) {
            fbsCols[index++] = makeDoubleColumn(builder, entry.getKey(), entry.getValue());
        }
        return fbsCols;
    }
}
