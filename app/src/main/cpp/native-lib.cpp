#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "SignatureCheck"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_FGeminiPro_MainActivity_isValidApp(JNIEnv *env, jobject thiz, jobject context) {
    jclass contextClass = env->GetObjectClass(context);

    // getPackageManager()
    jmethodID getPM = env->GetMethodID(contextClass, "getPackageManager",
                                       "()Landroid/content/pm/PackageManager;");
    jobject pm = env->CallObjectMethod(context, getPM);

    // getPackageName()
    jmethodID getPN = env->GetMethodID(contextClass, "getPackageName",
                                       "()Ljava/lang/String;");
    jstring packageName = (jstring) env->CallObjectMethod(context, getPN);

    // PackageManager.getPackageInfo(packageName, GET_SIGNATURES)
    jclass pmClass = env->GetObjectClass(pm);
    jmethodID getPI = env->GetMethodID(pmClass, "getPackageInfo",
                                       "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jobject pkgInfo = env->CallObjectMethod(pm, getPI, packageName, 0x40); // GET_SIGNATURES = 64

    // Get signatures[0]
    jclass pkgInfoCls = env->GetObjectClass(pkgInfo);
    jfieldID sigField = env->GetFieldID(pkgInfoCls, "signatures", "[Landroid/content/pm/Signature;");
    jobjectArray sigArray = (jobjectArray) env->GetObjectField(pkgInfo, sigField);
    jobject sig = env->GetObjectArrayElement(sigArray, 0);

    // toByteArray()
    jclass sigCls = env->GetObjectClass(sig);
    jmethodID toByteArray = env->GetMethodID(sigCls, "toByteArray", "()[B");
    jbyteArray sigBytes = (jbyteArray) env->CallObjectMethod(sig, toByteArray);

    // Get MessageDigest instance (SHA-256)
    jclass mdCls = env->FindClass("java/security/MessageDigest");
    jmethodID getInstance = env->GetStaticMethodID(mdCls, "getInstance",
                                                   "(Ljava/lang/String;)Ljava/security/MessageDigest;");
    jstring sha256 = env->NewStringUTF("SHA-256");
    jobject md = env->CallStaticObjectMethod(mdCls, getInstance, sha256);

    // digest(byte[])
    jmethodID update = env->GetMethodID(mdCls, "update", "([B)V");
    env->CallVoidMethod(md, update, sigBytes);

    jmethodID digest = env->GetMethodID(mdCls, "digest", "()[B");
    jbyteArray digestBytes = (jbyteArray) env->CallObjectMethod(md, digest);

    // Convert to hex string
    jsize len = env->GetArrayLength(digestBytes);
    jbyte *buf = env->GetByteArrayElements(digestBytes, NULL);

    std::string hex;
    char temp[3];
    for (int i = 0; i < len; i++) {
        sprintf(temp, "%02x", (unsigned char) buf[i]);
        hex += temp;
    }
    env->ReleaseByteArrayElements(digestBytes, buf, 0);

    LOGE("App Signature Hash: %s", hex.c_str());

    // তোমার আসল signature hash এখানে বসাও (SHA-256)
    std::string original = "7e096e0b94d803fbbd0656645ea7804d2369a7009718e1dfd300cbf616d053ba";

    return (hex == original) ? JNI_TRUE : JNI_FALSE;
}