#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_debanshu777_newsapp_util_Constant_00024Companion_getKeys(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "YOUR-API-KEY");
}