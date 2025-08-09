package net.lotte.chamomile.admin.user.service;

import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;

public final class PasswordValidation {

    // 키보드 연속 배열
    private static final String[] SEQUENCES = {
            "1234567890-=",
            "qwertyuiop;'",
            "asdfghjkl;'",
            "zxcvbnm,./",
            "=-0987654321",
            "][poiuytrewq",
            "';lkjhgfdsa",
            "/.,mnbvcxz"
    };


    /**
     * 키보드 연속 배열 체크
     * (연속 3개) - default
     */
    public static void repeatPassword(String password) {
        String lowerCasePassword = password.toLowerCase();

        for (String seq : SEQUENCES) {
            for (int i = 0; i < seq.length() - 2; i++) {
                String subSeq = seq.substring(i, i + 3);
                if (lowerCasePassword.contains(subSeq)) {
                    throw new ChamomileException(ChamomileExceptionCode.USER_PASSWORD_IS_REPETITIVE);
                }
            }
        }
    }

    /**
     * 키보드 연속 배열 체크
     * (연속 인자값의 수)
     */
    public static void repeatPassword(String password, int cnt) {
        String lowerCasePassword = password.toLowerCase();

        int max = cnt - 1;

        for (String seq : SEQUENCES) {
            for (int i = 0; i < seq.length() - max; i++) {
                String subSeq = seq.substring(i, i + cnt);
                if (lowerCasePassword.contains(subSeq)) {
                    throw new ChamomileException(ChamomileExceptionCode.USER_PASSWORD_IS_REPETITIVE);
                }
            }
        }
    }

    /**
     * 아이디와 동일한지 체크
     * (연속 3개) - default
     */
    public static void checkSameUserId(String userId, String password) {
        String lowerCasePassword = password.toLowerCase();

        for (int i = 0; i < userId.length() - 2; i++) {
            String subSeq = userId.substring(i, i + 3);
            if (lowerCasePassword.contains(subSeq)) {
                throw new ChamomileException(ChamomileExceptionCode.USER_PASSWORD_AND_ID_ARE_SAME);
            }
        }
    }

    /**
     * 아이디와 동일한지 체크
     * (연속 인자값 개수)
     */
    public static void checkSameUserId(String userId, String password, int cnt) {
        String lowerCasePassword = password.toLowerCase();

        int max = cnt - 1;

        for (int i = 0; i < userId.length() - max; i++) {
            String subSeq = userId.substring(i, i + max);
            if (lowerCasePassword.contains(subSeq)) {
                throw new ChamomileException(ChamomileExceptionCode.USER_PASSWORD_AND_ID_ARE_SAME);
            }
        }
    }

}
