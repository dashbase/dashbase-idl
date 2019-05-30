// automatically generated by the FlatBuffers compiler, do not modify

package io.dashbase.flatbuffers;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class DashEvent extends Table {
  public static DashEvent getRootAsDashEvent(ByteBuffer _bb) { return getRootAsDashEvent(_bb, new DashEvent()); }
  public static DashEvent getRootAsDashEvent(ByteBuffer _bb, DashEvent obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public DashEvent __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public long timeInMillis() { int o = __offset(4); return o != 0 ? bb.getLong(o + bb_pos) : -1L; }
  public boolean omitPayload() { int o = __offset(6); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public Column columns(int j) { return columns(new Column(), j); }
  public Column columns(Column obj, int j) { int o = __offset(8); return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null; }
  public int columnsLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }

  public static int createDashEvent(FlatBufferBuilder builder,
      long timeInMillis,
      boolean omitPayload,
      int columnsOffset) {
    builder.startObject(3);
    DashEvent.addTimeInMillis(builder, timeInMillis);
    DashEvent.addColumns(builder, columnsOffset);
    DashEvent.addOmitPayload(builder, omitPayload);
    return DashEvent.endDashEvent(builder);
  }

  public static void startDashEvent(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addTimeInMillis(FlatBufferBuilder builder, long timeInMillis) { builder.addLong(0, timeInMillis, -1L); }
  public static void addOmitPayload(FlatBufferBuilder builder, boolean omitPayload) { builder.addBoolean(1, omitPayload, false); }
  public static void addColumns(FlatBufferBuilder builder, int columnsOffset) { builder.addOffset(2, columnsOffset, 0); }
  public static int createColumnsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startColumnsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endDashEvent(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishDashEventBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
}

