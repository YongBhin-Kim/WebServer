#include <jni.h>
#include "Client.h"
#include <stdio.h>
// #include "../blockCipher/blockCipherLib.h"
#include "../blockCipher/simpleCipherLib.h"


// JNIEXPORT jstring JNICALL Java_Client_input(JNIEnv* env, jobject obj, jstring msg) {
//     char buf[128];
//     char key[] = "mykeymykey";

//     const char *str = (*env)->GetStringUTFChars(env, msg, 0); // JNI는 Native Method가 Java의 String을 조작할 수 있도록 UTF-8 변환을 처리해주는 함수를 제공
//     printf("%s", str); // jstring타입의 msg를 GetStringUTFChars()함수를 이용하여 UTF-8 문자열로 변경해 준다.
//     (*env)->ReleaseStringUTFChars(env, msg, str); // GetStringUTFChars()함수 사용 이후 ReleaseStringUTFChars()함수를 불러서 사용이 끝났음을 JVM에게 알려준다.
//     scanf("%s", buf); // buf = string

//     // =========================== 암호화해서 보내기 ==============
//     Enc(buf, key); // Enc(PT, key)
//     printf("1. 암호문 = "); printState(buf);
//     // Dec(buf, key);
//     // printf("복호문 = "); printStateASCII(buf);
//     // ========================================================
//     // 복호화할 땐 이대로 하면 되는데
//     // 암호화할 땐 UTF 씌우면 안될듯.
//     return (*env)->NewStringUTF(env, buf);
//     // return buf;
// }


// JNIEXPORT jstring JNICALL Java_Client_Dec(JNIEnv* env, jobject obj, jstring msg) {
//     char buf[128];
//     char key[] = "mykeymykey";
//     const char *str = (*env)->GetStringUTFChars(env, msg, 0); // JNI는 Native Method가 Java의 String을 조작할 수 있도록 UTF-8 변환을 처리해주는 함수를 제공
//     printf("2. 암호문 = %s\n", str); // jstring타입의 msg를 GetStringUTFChars()함수를 이용하여 UTF-8 문자열로 변경해 준다.
//     printf("22222\n");

//     for (int i = 0; i < strlen(str); i++) {
//         buf[i] = str[i];
//         printf("%02x\n", buf[i]);
//     }
//     printf("22222\n");

//     Dec(buf, key);
//     printf("2. 복호문 = "); printStateASCII(buf);
//     printf("22222\n");
//     (*env)->ReleaseStringUTFChars(env, msg, str); // GetStringUTFChars()함수 사용 이후 ReleaseStringUTFChars()함수를 불러서 사용이 끝났음을 JVM에게 알려준다.
    // scanf("%s", str);

    // =========================== 암호화해서 보내기 ==============
    // Enc(buf, key); // Enc(PT, key)
    // Dec(buf, key);
    // printf("복호문 = "); printStateASCII(buf);
    // ========================================================
    // 복호화할 땐 이대로 하면 되는데
    // 암호화할 땐 UTF 씌우면 안될듯.
//     return (*env)->NewStringUTF(env, buf);
// }

// JNIEXPORT void JNICALL Java_Client_addInteger(JNIEnv *env, jobject jobj, jint var1) {
//     // printf("%d + %d = %d\n",var1,var2,var1+var2);
//     return var1 ^ 0x01;
// }

// JNIEXPORT void JNICALL Java_Client_arr2(JNIEnv *env, jobject obj, jintArray *arr1) {
//         //  jintArray retArr = (*env)->NewIntArray(env, 128);
//         //  unsigned short src[128];
//          int tmpArr[128];
//          for (int i = 0; i < 128; i++){
//                   tmpArr[i] = 0;
//          }
//          (*env)->SetIntArrayRegion(env, arr1, 0, 128, tmpArr);
//          return arr1; // arr1에 tmparr 주입

// }

JNIEXPORT jintArray JNICALL Java_Client_Enc(JNIEnv *env, jobject jobj, jintArray arr2)
{
    jint *byteBuf;
    int size, i;
    jintArray out;
    char key[] = "mykeymykey";
    int intkey[10];
    for (int i = 0; i < 10; i++) {
        intkey[i] = key[i];
        // printf("%d", intkey[i]);
    }
    
    out = (*env)->NewIntArray(env, 128);
    size = (*env)->GetArrayLength(env, arr2);
    byteBuf = (*env)->GetIntArrayElements(env, arr2, 0);
    int tmp[128];
    for (int i = 0; i < 128; i++) {
        if (byteBuf[i] > 255) {
            tmp[i] = 0;
        }
        else if (byteBuf[i] < 0) {
            tmp[i] = 0;
        }
        else {
            tmp[i] = byteBuf[i];
        }
    }
    printf("[입력 메시지] \n");
    printState(tmp);

    Enc(tmp, intkey);
    printf("[암호화] \n");
    printState(tmp);
    // for (int i = 0; i < 128; i++) {
    //     printf("%d", byteBuf[i]);
    // }

    (*env)->SetIntArrayRegion(env, out, 0, 128, tmp);
    (*env)->ReleaseIntArrayElements(env, arr2, byteBuf, 0);

    return out;
}


JNIEXPORT jintArray JNICALL Java_Client_Dec(JNIEnv *env, jobject jobj, jintArray arr2)
{
    jint *byteBuf;
    int size, i;
    jintArray out;
    char key[] = "mykeymykey";
    int intkey[10];
    for (int i = 0; i < 10; i++) {
        intkey[i] = key[i];
    }
    
    out = (*env)->NewIntArray(env, 128);
    size = (*env)->GetArrayLength(env, arr2);
    byteBuf = (*env)->GetIntArrayElements(env, arr2, 0);
    int tmp[128];
    // printf("[받은 메시지 : C ]\n");
    for (int i = 0; i < 128; i++) {
        tmp[i] = byteBuf[i];
        // printf("%d ", tmp[i]);
    }
    Dec(tmp, intkey);

    (*env)->SetIntArrayRegion(env, out, 0, 128, tmp);
    (*env)->ReleaseIntArrayElements(env, arr2, byteBuf, 0);

    return out;
}