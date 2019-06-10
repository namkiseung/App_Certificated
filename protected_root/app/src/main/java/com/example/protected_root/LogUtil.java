package com.example.protected_root;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class LogUtil {
    private static final String LOG_TAG = "BONG";
    private static Boolean signed = null;
    Context context;
    /*해결 방법
로그를 출력하는 유틸 클래스 제작
이 유틸 클래스틑 통해서 로그 출력
실행되는 애플리케이션의 상태확인
앞서 제작한 클래스에서 로그 출력전 배포 전/후인지 확인
배포를 하기위해서는 Android Signinkey를 이용해 sign해야하기 때문에 이를 활용
코드 설명
isSigned()
애플리케이션의 sign여부(배포 유무)를 확인하는 함수
debug(), warn(), error()
로그를 출력하는 함수
Andorid.Util.Log는 인자로 String만 사용하기 때문에 불편함. 이를 해결하기 위해 Object를 인자로 받아서 처리.
BlogCodeApplication.getAppContext()
애플리케이션의 context를 가져오는 함수. 코드를 복사해서 사용할 경우 수정 필요*/
    private static boolean isSigned() {
        if(signed == null) {
//            try {
//                //Context context = Log.getAppContext();
//                signed = false;
//
//                if(!new String(context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures[0].toByteArray()).contains("Android Debug")) {
//                    signed = true;
//                }
//
//            } catch(PackageManager.NameNotFoundException e) {
//                printStackTrace(e);
//            }
        }

        return signed;
    }

    public static void debug(Object... parameters) {
        if(!isSigned()) {
            StringBuilder sb = new StringBuilder();
            Object object = null;

            for(int i = 0; i < parameters.length; i++) {
                object = parameters[i];

                sb.append(object);

                if(i != parameters.length - 1)
                    sb.append(", ");
            }

            Log.d(LOG_TAG, sb.toString());
        }
    }

    public static void error(Object... parameters) {
        if(!isSigned()) {
            StringBuilder sb = new StringBuilder();
            Object object = null;

            for(int i = 0; i < parameters.length; i++) {
                object = parameters[i];

                sb.append(object);

                if(i != parameters.length - 1)
                    sb.append(", ");
            }

            Log.e(LOG_TAG, sb.toString());
        }
    }

    public static void warn(Object... parameters) {
        if(!signed) {
            StringBuilder sb = new StringBuilder();
            Object object = null;

            for(int i = 0; i < parameters.length; i++) {
                object = parameters[i];

                sb.append(object);

                if(i != parameters.length - 1)
                    sb.append(", ");
            }

            Log.w(LOG_TAG, sb.toString());
        }
    }

    public static void printStackTrace(Throwable e) {
        if(!isSigned())
            Log.w(LOG_TAG, Log.getStackTraceString(e));
    }
}
/*참고사항
안드로이드 배포시 로그 처리관련해서 리서치를 하다보면 아래와 같은 내용을 확인 할 수있음. 테스트 결과 공유합니다.
Log.debug는 debug일때만 출력, Log.error는 배포 후에도 출력??
APK로 만들어서 테스트해본 결과 Log.debug, Log.error모두 출력됨
BuildConfig.debug
앱이 배포된 것인지, 디버깅용으로 실행중인지 판별하기 위해 BuildConfig.debug를 사용???
프로젝트 Run으로 실행한 경우,  APK로 만들어서 실행해본 결과 두 경우 모두 true값이 설정됨
e.printStackTrace
디버깅에 꼭필요하지만 보안상 배포 후에는 출력되서는 안됨
APK로 만들어서 테스트 해본 결과 출력됨
앞서 첨부한 코드의 printStackTrace함수를 활용하여 해결*/
//http://bongchan.blogspot.com/2014/05/android.html