#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_debanshu777_newsapp_util_Constant_00024Companion_getKeys(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "7dc8a54d3af244cbb8a4a21651a49b1c");
}