package com.lhb.constants;

import com.lhb.utils.DateUtil;

import java.time.LocalDate;

public class SysConstants {

    /**
     * 知识点编码前缀
     */
    public static final String KP_CODE_PREFIX = "KP_";
    public static String generateKpCode(Long id,int nextSeq) {
        String seqStr = String.format("%03d", nextSeq);
        return KP_CODE_PREFIX + id + "_" + seqStr;
    }

    /**
     * 生成题目编码前缀
     */
    public static final String QUESTION_NO_PREFIX = "Q";
    public static String generateCode(long seq) {
        String date = DateUtil.localDateToString(LocalDate.now(), DateUtil.YYYYMMDD);
        String seqStr = String.format("%03d", seq);
        return QUESTION_NO_PREFIX + date + seqStr;
    }

    public static String generateConversationId(long seq) {
        String date = DateUtil.localDateToString(LocalDate.now(), DateUtil.YYYYMMDD);
        return "conv-" + date + "-" + seq;
    }

}
