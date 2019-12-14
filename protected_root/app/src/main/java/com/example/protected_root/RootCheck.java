package com.example.protected_root;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RootCheck {
        public static boolean checkSuperUser(){
            Log.d("NamkiLog "+RootCheck.class.getSimpleName(), "");
            Log.d("Protected [RootCheck]",""+checkTags());
            //return (checkTags() == true) ? true : false;
            return (checkRootedFiles() == true || checkSuperUserCommand() == true  || checkSuperUserCommand2() == true ||  checkTags() == true) ? true : false;
        }

        private static boolean checkRootedFiles(){
            final String[] files = {
                    "/sbin/su",
                    "/system/su",
                    "/system/bin/su",
                    "/system/sbin/su",
                    "/system/xbin/su",
                    "/system/xbin/mu",
                    "/system/bin/.ext/.su",
                    "/system/usr/su-backup",
                    "/data/data/com.noshufou.android.su",
                    "/system/app/Superuser.apk",
                    "/system/app/su.apk",
                    "/system/bin/.ext",
                    "/system/xbin/.ext",
                    "/data/local/xbin/su",
                    "/data/local/bin/su",
                    "/system/sd/xbin/su",
                    "/system/bin/failsafe/su",
                    "/data/local/su",
                    "/su/bin/su"};

            for(int i = 0; i<files.length; i++){
                File file = new File(files[i]);
                if(null != file && file.exists()){
                    Log.d("NamkiLog "+RootCheck.class.getSimpleName(),"Rooted File : " + file.getAbsolutePath() + " : " + file.getName());
                    return true;
                }
            }
            return false;
        }

        /*
        루팅이 된 기기는 일반적으로 Build.TAGS 값이 제조사 키값이 아닌 test 키 값을 가지고 있습니다.
        */
        private static boolean checkTags() {
            String buildTags = android.os.Build.TAGS;
            Log.d("NamkiLog "+"FROCS", "test-keys : " + buildTags.contains("test-keys"));
            Log.d("NamkiLog "+"FROCS", "buildTags : " + buildTags);
            //실 디바이스 체크 로직
            if (buildTags != null || buildTags.contains("test-keys") ||buildTags.contains("dev-keys")) {
                return true;
            }
            return false;
        }

        private static boolean checkSuperUserCommand(){
            try {
                Runtime.getRuntime().exec("su");
                Log.d("NamkiLog"+RootCheck.class.getSimpleName(), "device has super user");
                return true;
            } catch (Error e){

            } catch (Exception e){

            }
            return false;
        }

        private static boolean checkSuperUserCommand2() {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                if (in.readLine() != null) return true;
                return false;
            } catch (Throwable t) {
                return false;
            } finally {
                if (process != null) process.destroy();
            }
        }
    //if(RootCheck.checkSuperUser()) {}
    }



