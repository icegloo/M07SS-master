#include <jni.h>
#include <string>
#include "TelloClientUDP.h"



TelloClientUDP *TelloClient;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_myapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Commande(JNIEnv *env, jobject thiz, jstring ip) {
    TelloClient = NULL;
    TelloClient = new TelloClientUDP(env->GetStringUTFChars(ip, NULL));
    TelloClient->ModeCommande();
    TelloClient->EnvoyerCommande("streamon");
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Avancer(JNIEnv *env, jobject thiz) {
    TelloClient->Avant(50);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Reculer(JNIEnv *env, jobject thiz) {
    TelloClient->Arriere(50);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Droite(JNIEnv *env, jobject thiz) {
    TelloClient->Droite(50);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Gauche(JNIEnv *env, jobject thiz) {
    TelloClient->Gauche(50);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Trigo(JNIEnv *env, jobject thiz, jint deg) {
    TelloClient->TournerTrigo((int)deg);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Horraire(JNIEnv *env, jobject thiz, jint deg) {
    TelloClient->TournerHoraire((int)deg);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Monter(JNIEnv *env, jobject thiz, jint hauteur) {
    TelloClient->Monter((int)hauteur);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Descendre(JNIEnv *env, jobject thiz, jint hauteur) {
    TelloClient->Descendre((int)hauteur);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Decoller(JNIEnv *env, jobject thiz) {
    TelloClient->Decoller();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Atterrir(JNIEnv *env, jobject thiz) {
    TelloClient->Atterrir();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_Go(JNIEnv *env, jobject thiz, jint x, jint y,
                                               jint vitesse) {
    TelloClient->DeplacerXYZ((int)y,(int)x,0,(int)vitesse);
}