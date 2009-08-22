
package org.python.modules.jffi;

import org.python.core.Py;
import org.python.core.PyObject;

/**
 * Defines memory operations for a primitive type
 */
abstract class MemoryOp {
    public static final MemoryOp VOID = new VoidOp();
    public static final MemoryOp INT8 = new Signed8();
    public static final MemoryOp UINT8 = new Unsigned8();
    public static final MemoryOp INT16 = new Signed16();
    public static final MemoryOp UINT16 = new Unsigned16();
    public static final MemoryOp INT32 = new Signed32();
    public static final MemoryOp UINT32 = new Unsigned32();
    public static final MemoryOp INT64 = new Signed64();
    public static final MemoryOp UINT64 = new Unsigned64();
    public static final MemoryOp FLOAT = new Float32();
    public static final MemoryOp DOUBLE = new Float64();
    public static final MemoryOp POINTER = new PointerOp();
    public static final MemoryOp STRING = new StringOp();

    public static final MemoryOp getMemoryOp(NativeType type) {
        switch (type) {
            case VOID:
                return VOID;
            case BYTE:
                return INT8;
            case UBYTE:
                return UINT8;
            case SHORT:
                return INT16;
            case USHORT:
                return UINT16;
            case INT:
                return INT32;
            case UINT:
                return UINT32;
            case LONGLONG:
                return INT64;
            case ULONGLONG:
                return UINT64;
            case LONG:
                return com.kenai.jffi.Platform.getPlatform().longSize() == 32
                        ? INT32 : INT64;
            case ULONG:
                return com.kenai.jffi.Platform.getPlatform().longSize() == 32
                        ? UINT32 : UINT64;
            case FLOAT:
                return FLOAT;
            case DOUBLE:
                return DOUBLE;
            case POINTER:
                return POINTER;
            case STRING:
                return STRING;
            default:
                throw new UnsupportedOperationException("No MemoryOp for " + type);
        }
    }
    
    abstract PyObject get(Memory mem, long offset);
    abstract void put(Memory mem, long offset, PyObject value);

    private static final class VoidOp extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            throw Py.TypeError("Attempting to write void to memory");
        }

        public final PyObject get(Memory mem, long offset) {
            throw Py.TypeError("Attempting to read void from memory");
        }
    }
    static final class Signed8 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putByte(offset, Util.int8Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Util.newSigned8(mem.getByte(offset));
        }
    }

    static final class Unsigned8 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putByte(offset, (byte) Util.uint8Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Util.newUnsigned8(mem.getByte(offset));
        }
    }
    static final class Signed16 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putShort(offset, Util.int16Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Util.newSigned16(mem.getShort(offset));
        }
    }
    static final class Unsigned16 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putShort(offset, (short) Util.uint16Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Util.newUnsigned16(mem.getShort(offset));
        }
    }
    static final class Signed32 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putInt(offset, Util.int32Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Util.newSigned32(mem.getInt(offset));
        }
    }
    static final class Unsigned32 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putInt(offset, (int) Util.uint32Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Util.newUnsigned32(mem.getInt(offset));
        }
    }
    static final class Signed64 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putLong(offset, Util.int64Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Util.newSigned64(mem.getLong(offset));
        }
    }
    static final class Unsigned64 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putLong(offset, Util.uint64Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Util.newUnsigned64(mem.getLong(offset));
        }
    }
    static final class Float32 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putFloat(offset, Util.floatValue(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Py.newFloat(mem.getFloat(offset));
        }
    }
    static final class Float64 extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putDouble(offset, Util.doubleValue(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Py.newFloat(mem.getDouble(offset));
        }
    }
    private static final class PointerOp extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            mem.putAddress(offset, Util.int64Value(value));
        }

        public final PyObject get(Memory mem, long offset) {
            return Py.newLong(mem.getAddress(offset));
        }
    }
    private static final class StringOp extends MemoryOp {
        public final void put(Memory mem, long offset, PyObject value) {
            throw Py.NotImplementedError("Cannot set String");
        }

        public final PyObject get(Memory mem, long offset) {
            throw Py.NotImplementedError("Cannot get String");
        }
    }
}
