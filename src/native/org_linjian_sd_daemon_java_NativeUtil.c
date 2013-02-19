/**
 * Licensed under the GNU Lesser General Public License
 * Copyright (c) 2012-2013
 * Jian Lin <http://linjian.org>
 */

#include <sd-daemon.h>
#include <unistd.h>
#include <stdlib.h>
#include "org_linjian_sd_daemon_java_NativeUtil.h"

/*
 * Class:     org_linjian_sd_daemon_java_NativeUtil
 * Method:    sd_listen_fds
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_linjian_sd_1daemon_1java_NativeUtil_sd_1listen_1fds
  (JNIEnv *env, jclass cls, jint unset)
{
	return (jint)sd_listen_fds((int)unset);
}

/*
 * Class:     org_linjian_sd_daemon_java_NativeUtil
 * Method:    sd_booted
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_linjian_sd_1daemon_1java_NativeUtil_sd_1booted
  (JNIEnv *env, jclass cls)
{
	return (jint)sd_booted();
}

/*
 * Class:     org_linjian_sd_daemon_java_NativeUtil
 * Method:    getpid
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_linjian_sd_1daemon_1java_NativeUtil_getpid
  (JNIEnv *env, jclass cls)
{
	return (jint)getpid();
}

/*
 * Class:     org_linjian_sd_daemon_java_NativeUtil
 * Method:    getenv
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_linjian_sd_1daemon_1java_NativeUtil_getenv
  (JNIEnv *env, jclass cls, jstring name)
{
	const char *str;
	char *ret;
	str = (*env)->GetStringUTFChars(env, name, NULL);
	ret = getenv(str);
	(*env)->ReleaseStringUTFChars(env, name, str);
	return (jstring)(*env)->NewStringUTF(env, (const char *)ret);
}

/*
 * Class:     org_linjian_sd_daemon_java_NativeUtil
 * Method:    setenv
 * Signature: (Ljava/lang/String;Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_org_linjian_sd_1daemon_1java_NativeUtil_setenv
  (JNIEnv *env, jclass cls, jstring name, jstring value, jint replace)
{
	const char *strn, *strv;
	int ret;
	strn = (*env)->GetStringUTFChars(env, name, NULL);
	strv = (*env)->GetStringUTFChars(env, value, NULL);
	ret = setenv(strn, strv, (int)replace);
	(*env)->ReleaseStringUTFChars(env, name, strn);
	(*env)->ReleaseStringUTFChars(env, value, strv);
	return (jint)ret;
}

/*
 * Class:     org_linjian_sd_daemon_java_NativeUtil
 * Method:    system
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_linjian_sd_1daemon_1java_NativeUtil_system
  (JNIEnv *env, jclass cls, jstring cmd)
{
	const char *str;
	int ret;
	str = (*env)->GetStringUTFChars(env, cmd, NULL);
	ret = system(str);
	(*env)->ReleaseStringUTFChars(env, cmd, str);
	return (jint)ret;
}
