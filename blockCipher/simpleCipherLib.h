#include <stdio.h>
#include <string.h>

void Enc(char PT[128], char key[10]);
void Dec(char CT[128], char key[10]);
void printState(char state[128]);

// 암호화
void Enc(char PT[128], char key[10]) {
    int num = 0; 
    while(num < strlen(PT)){
        for(int i=0; i<strlen(key); i++) {
            PT[num] = PT[num] ^ key[i];
            num++;
        }
    }
}

// 복호화
void Dec(char CT[128], char key[10]) {
    int num = 0;
    while(num < strlen(CT)){
        for(int i=0; i<strlen(key); i++) {
            CT[num] = CT[num] ^ key[i];
            num++;
        }
    }
}
 
// 암호문 출력 함수
void printState(char state[128]) {
    for (int i=0; i<strlen(state); i++) {
        printf("%02x ", state[i]);
    } printf("\n");
}

void printStateASCII(char state[128]) {
    for (int i=0; i<strlen(state); i++) {
        printf("%c ", state[i]);
    } printf("\n");
}