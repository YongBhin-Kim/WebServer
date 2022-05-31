#include <jni.h>
#include "Client.h"
#include <stdio.h>
// #include "../blockCipher/blockCipherLib.h"
#include "../blockCipher/simpleCipherLib.h"


JNIEXPORT jstring JNICALL Java_Client_input(JNIEnv* env, jobject obj, jstring msg) {
    char buf[128];
    char key[] = "mykeymykey";

    const char *str = (*env)->GetStringUTFChars(env, msg, 0); // JNI는 Native Method가 Java의 String을 조작할 수 있도록 UTF-8 변환을 처리해주는 함수를 제공
    printf("%s", str); // jstring타입의 msg를 GetStringUTFChars()함수를 이용하여 UTF-8 문자열로 변경해 준다.
    (*env)->ReleaseStringUTFChars(env, msg, str); // GetStringUTFChars()함수 사용 이후 ReleaseStringUTFChars()함수를 불러서 사용이 끝났음을 JVM에게 알려준다.
    scanf("%s", buf);

    // =========================== 암호화해서 보내기 ==============
    Enc(buf, key); // Enc(PT, key)
    printf("암호문 = "); printState(buf);
    Dec(buf, key);
    // printf("복호문 = "); printStateASCII(buf);
    // ========================================================
    // 복호화할 땐 이대로 하면 되는데
    // 암호화할 땐 UTF 씌우면 안될듯.
    return (*env)->NewStringUTF(env, buf);
}