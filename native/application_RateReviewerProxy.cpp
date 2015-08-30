#include <jni.h>
#include <stdio.h>
#include "application_RateReviewerProxy.h"

// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT jstring JNICALL Java_application_RateReviewerProxy_initInterface (JNIEnv *env, jobject thisObj, jstring in){
	const char *input = env->GetStringUTFChars(in, NULL);
	if (input == NULL){
		return NULL;
	}
	printf(input);
	const char *out = "pfft";
	jstring output = env->NewStringUTF(out);
	return output;
}