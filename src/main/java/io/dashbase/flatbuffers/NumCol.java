// automatically generated by the FlatBuffers compiler, do not modify

package io.dashbase.flatbuffers;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class NumCol extends Table {
  public static NumCol getRootAsNumCol(ByteBuffer _bb) { return getRootAsNumCol(_bb, new NumCol()); }
  public static NumCol getRootAsNumCol(ByteBuffer _bb, NumCol obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public NumCol __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String key() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer keyAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public double value() { int o = __offset(6); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }

  public static int createNumCol(FlatBufferBuilder builder,
      int keyOffset,
      double value) {
    builder.startObject(2);
    NumCol.addValue(builder, value);
    NumCol.addKey(builder, keyOffset);
    return NumCol.endNumCol(builder);
  }

  public static void startNumCol(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addKey(FlatBufferBuilder builder, int keyOffset) { builder.addOffset(0, keyOffset, 0); }
  public static void addValue(FlatBufferBuilder builder, double value) { builder.addDouble(1, value, 0.0); }
  public static int endNumCol(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}

