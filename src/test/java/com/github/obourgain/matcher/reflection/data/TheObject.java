package com.github.obourgain.matcher.reflection.data;

import java.util.Arrays;

/**
 * @author olivier bourgain
 */
public class TheObject {

    private int anInt;
    private long aLong;
    private short aShort;
    private char aChar;
    private byte aByte;
    private boolean aBoolean;
    private float aFloat;
    private double aDouble;

    private int[] anIntArray;
    private long[] aLongArray;
    private short[] aShortArray ;
    private char[] aCharArray;
    private byte[] aByteArray;
    private boolean[] aBooleanArray;
    private float[] aFloatArray;
    private double[] aDoubleArray;

    private TheObject anObject;
    private TheObject[] anObjectArray;
    private TheObject[][] anArrayOfObjectArrays;

    private transient TheObject theTransientField;

    public void setAnObject(TheObject anObject) {
        this.anObject = anObject;
    }

    public void setTheTransientField(TheObject theTransientField) {
        this.theTransientField = theTransientField;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public void setAnArrayOfObjectArrays(TheObject[][] anArrayOfObjectArrays) {
        this.anArrayOfObjectArrays = anArrayOfObjectArrays;
    }

    public void setAnObjectArray(TheObject[] anObjectArray) {
        this.anObjectArray = anObjectArray;
    }

    public void setALong(long aLong) {
        this.aLong = aLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TheObject theObject = (TheObject) o;

        if (aBoolean != theObject.aBoolean) return false;
        if (aByte != theObject.aByte) return false;
        if (aChar != theObject.aChar) return false;
        if (Double.compare(theObject.aDouble, aDouble) != 0) return false;
        if (Float.compare(theObject.aFloat, aFloat) != 0) return false;
        if (aLong != theObject.aLong) return false;
        if (aShort != theObject.aShort) return false;
        if (anInt != theObject.anInt) return false;
        if (!Arrays.equals(aBooleanArray, theObject.aBooleanArray)) return false;
        if (!Arrays.equals(aByteArray, theObject.aByteArray)) return false;
        if (!Arrays.equals(aCharArray, theObject.aCharArray)) return false;
        if (!Arrays.equals(aDoubleArray, theObject.aDoubleArray)) return false;
        if (!Arrays.equals(aFloatArray, theObject.aFloatArray)) return false;
        if (!Arrays.equals(aLongArray, theObject.aLongArray)) return false;
        if (!Arrays.equals(aShortArray, theObject.aShortArray)) return false;
        if (!Arrays.equals(anIntArray, theObject.anIntArray)) return false;
        if (anObject != null ? !anObject.equals(theObject.anObject) : theObject.anObject != null) return false;
        if (!Arrays.equals(anObjectArray, theObject.anObjectArray)) return false;
        if (theTransientField != null ? !theTransientField.equals(theObject.theTransientField) : theObject.theTransientField != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = anInt;
        result = 31 * result + (int) (aLong ^ (aLong >>> 32));
        result = 31 * result + (int) aShort;
        result = 31 * result + (int) aChar;
        result = 31 * result + (int) aByte;
        result = 31 * result + (aBoolean ? 1 : 0);
        result = 31 * result + (aFloat != +0.0f ? Float.floatToIntBits(aFloat) : 0);
        temp = Double.doubleToLongBits(aDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (anIntArray != null ? Arrays.hashCode(anIntArray) : 0);
        result = 31 * result + (aLongArray != null ? Arrays.hashCode(aLongArray) : 0);
        result = 31 * result + (aShortArray != null ? Arrays.hashCode(aShortArray) : 0);
        result = 31 * result + (aCharArray != null ? Arrays.hashCode(aCharArray) : 0);
        result = 31 * result + (aByteArray != null ? Arrays.hashCode(aByteArray) : 0);
        result = 31 * result + (aBooleanArray != null ? Arrays.hashCode(aBooleanArray) : 0);
        result = 31 * result + (aFloatArray != null ? Arrays.hashCode(aFloatArray) : 0);
        result = 31 * result + (aDoubleArray != null ? Arrays.hashCode(aDoubleArray) : 0);
        result = 31 * result + (anObject != null ? anObject.hashCode() : 0);
        result = 31 * result + (anObjectArray != null ? Arrays.hashCode(anObjectArray) : 0);
        result = 31 * result + (theTransientField != null ? theTransientField.hashCode() : 0);
        return result;
    }
}
