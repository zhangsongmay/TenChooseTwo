package com.song.util;

import java.util.HashSet;
import java.util.Set;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-24
 */
public class ValidUtil {
    /**
     * 验证输入的字符串是否正确
     * 必须以英文逗号分隔5个数字，形如  1,2,3,4,5
     * 且每个数字必须为0,9以内的数字
     * @param inputText String
     * @return false 不正确，true  正确
     */
    public static boolean validInputText(String inputText, int num) {
        boolean flag = true;
        //只有在不为空的情况下才进行字符串校验
        if(null != inputText && !"".equals(inputText.trim())) {
            if(inputText.trim().startsWith(",") || inputText.trim().endsWith(",")) {
                return false;
            }
            String[] numbers = inputText.split("\\,");
            int len = numbers.length;
            if(len != num) {
                // 必须为num个数字
                flag = false;
            }
            for(int i = 0; i< num; i++) {
                try {
                    // 校验每个数字必须在0到9
                    int number = Integer.parseInt(numbers[i]);
                    if(number < 0 || number >= 10) {
                        flag = false;
                        break;
                    }
                } catch (Exception e) {
                    // 数字转换失败校验也不通过
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     *
     * @param inputText
     * @param num
     * @param same 是否允许inputText中的有相同的数字，true：可以相等，false：不可以相等
     * @return
     */
    public static boolean validInputText(String inputText, int num, boolean same) {
        boolean flag = true;
        flag = validInputText(inputText, num);
        if(null != inputText && !"".equals(inputText.trim())) {
            if(flag && !same) { // 验证数量通过且数字不可以相同
                if (isSameNumber(inputText)) return false;
            }
        }

        return flag;
    }

    /**
     * 判断inputText中是否有相同的数字，数字总数为num，且数字以英文逗号分隔
     * @param inputText
     * @return 如果有相同的数字，返回true，反之，返回false
     */
    public static boolean isSameNumber(String inputText) {
        if(null != inputText && !"".equals(inputText.trim())) {
            Set<Integer> setInt = new HashSet<>();
            String[] strs = inputText.split("\\,");
            int len = strs.length;
            for(int i=0; i< len; i++) {
                setInt.add(Integer.parseInt(strs[i]));
            }
            // 如果分隔后的长度不等于set后的长度，则说明有相同的数字
            if(len != setInt.size()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取英文逗号分隔的数字的set集合
     * @param inputText
     * @return
     */
    public static Set<Integer> getNumSet(String inputText) {
        Set<Integer> setInt = null;
        try {
            if(null != inputText && !"".equals(inputText.trim())) {
                setInt = new HashSet<>();
                String[] strs = inputText.split("\\,");
                int len = strs.length;
                for(int i=0; i< len; i++) {
                    if(null == strs[i] || "".equals(strs[i])) {
                        continue;
                    }
                    setInt.add(Integer.parseInt(strs[i]));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return setInt;
    }

    /**
     * 验证时间格式是否正确
     * @param dateStr
     * @return
     */
    public static boolean validDate(String dateStr) {
        boolean flag = true;
        try {
            if(null != dateStr && !"".equals(dateStr.trim())) {
                DateUtil.parseStrToDate(dateStr);
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
