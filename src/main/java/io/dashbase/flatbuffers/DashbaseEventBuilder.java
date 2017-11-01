package io.dashbase.flatbuffers;

import com.google.common.base.Preconditions;
import com.google.flatbuffers.FlatBufferBuilder;
import io.dashbase.AbstractDashbaseEventBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DashbaseEventBuilder extends AbstractDashbaseEventBuilder<byte[], DashbaseEventBuilder> {
    public static DashbaseEventBuilder builder() {
        return new DashbaseEventBuilder();
    }

    @Override
    public byte[] build() {
        Preconditions.checkArgument(timeInMillis >= 0L, "Need to specify timestamp");
        FlatBufferBuilder builder = new FlatBufferBuilder(1024);

        int textColsOffsets = DashbaseEvent.createTextColsVector(builder,
                makeFbsStringCols(builder, textCols));
        int metaColsOffsets = DashbaseEvent.createMetaColsVector(builder,
                makeFbsStringCols(builder, metaCols));
        int idColsOffsets = DashbaseEvent.createIdColsVector(builder,
                makeFbsStringCols(builder, idCols));
        int numColsOffsets = DashbaseEvent.createNumberColsVector(builder,
                makeFbsNumCols(builder, numberCols));

        DashbaseEvent.startDashbaseEvent(builder);
        DashbaseEvent.addTimeInMillis(builder, timeInMillis);
        DashbaseEvent.addOmitPayload(builder, omitPayload);
        DashbaseEvent.addTextCols(builder, textColsOffsets);
        DashbaseEvent.addMetaCols(builder, metaColsOffsets);
        DashbaseEvent.addIdCols(builder, idColsOffsets);
        DashbaseEvent.addNumberCols(builder, numColsOffsets);

        int event = DashbaseEvent.endDashbaseEvent(builder);
        builder.finish(event);
        return builder.sizedByteArray();
    }

    private static int[] makeFbsStringCols(FlatBufferBuilder builder, Map<String, String> stringMap) {
        int[] fbsCols = new int[stringMap.size()];
        int index = 0;
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            int k = builder.createString(entry.getKey());
            int v = builder.createString(entry.getValue());
            int fbsCol = StringCol.createStringCol(builder, k, v);
            fbsCols[index++] = fbsCol;
        }
        return fbsCols;
    }

    private static int[] makeFbsNumCols(FlatBufferBuilder builder, Map<String, Double> stringMap) {
        int[] fbsCols = new int[stringMap.size()];
        int index = 0;
        for (Map.Entry<String, Double> entry : stringMap.entrySet()) {
            int k = builder.createString(entry.getKey());
            int fbsCol = NumCol.createNumCol(builder, k, entry.getValue());
            fbsCols[index++] = fbsCol;
        }
        return fbsCols;
    }
}
