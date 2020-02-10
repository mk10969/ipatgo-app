//package org.uma.vodka.util;
//
//import com.jacob.com.SafeArray;
//import com.jacob.com.Variant;
//import org.uma.vodka.common.reflection.ClassUtil;
//
//public class JacobUtil {
//    private JacobUtil() {
//    }
//
//    public static Variant getRefVariant(Class<?> type, Object... parameters) {
//        return new Variant(ClassUtil.newInstance(type, parameters), true);
//    }
//
////    public static Variant getRefVariant(Object object) {
////        return new Variant(object, true);
////    }
//
//    public static SafeArray getSafeByteArray() {
//        // 第二引数が、よくわからない。。
//        SafeArray safeArray = new SafeArray(Variant.VariantByte, 0);
//        // 配列の初期値セット。
//        safeArray.setByte(0, (byte) 0x00);
//        return safeArray;
//    }
//
//    public static Variant getArrayRefVariant(SafeArray array) {
//        Variant vBuff = new Variant(Variant.VariantArray, true);
//        vBuff.putSafeArrayRef(array);
//        return vBuff;
//    }
//
//}