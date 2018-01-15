// automatically generated by the FlatBuffers compiler, do not modify

package io.dashbase.flatbuffers;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class Column extends Table {
  public static Column getRootAsColumn(ByteBuffer _bb) { return getRootAsColumn(_bb, new Column()); }
  public static Column getRootAsColumn(ByteBuffer _bb, Column obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public Column __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public byte type() { int o = __offset(6); return o != 0 ? bb.get(o + bb_pos) : 0; }
  public String strValue() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer strValueAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public int intValue() { int o = __offset(10); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public float floatValue() { int o = __offset(12); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public double doubleValue() { int o = __offset(14); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }

  public static int createColumn(FlatBufferBuilder builder,
      int nameOffset,
      byte type,
      int strValueOffset,
      int intValue,
      float floatValue,
      double doubleValue) {
    builder.startObject(6);
    Column.addDoubleValue(builder, doubleValue);
    Column.addFloatValue(builder, floatValue);
    Column.addIntValue(builder, intValue);
    Column.addStrValue(builder, strValueOffset);
    Column.addName(builder, nameOffset);
    Column.addType(builder, type);
    return Column.endColumn(builder);
  }

  public static void startColumn(FlatBufferBuilder builder) { builder.startObject(6); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addType(FlatBufferBuilder builder, byte type) { builder.addByte(1, type, 0); }
  public static void addStrValue(FlatBufferBuilder builder, int strValueOffset) { builder.addOffset(2, strValueOffset, 0); }
  public static void addIntValue(FlatBufferBuilder builder, int intValue) { builder.addInt(3, intValue, 0); }
  public static void addFloatValue(FlatBufferBuilder builder, float floatValue) { builder.addFloat(4, floatValue, 0.0f); }
  public static void addDoubleValue(FlatBufferBuilder builder, double doubleValue) { builder.addDouble(5, doubleValue, 0.0); }
  public static int endColumn(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}

