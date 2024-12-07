package com.linkun.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NumberUtils {
    private static final Logger logger = LoggerFactory.getLogger(NumberUtils.class);

    /**
     * parse a string to long with default value
     * 
     * @param str a string
     * @param defaultValue if string is empty or is not a number, return default value
     * @return a long
     */
    public static Long toLong(final String str, final Long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str.trim());
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * parse a string to long with a default value 0
     * 
     * @param str a string
     * @return a long
     */
    public static long toLong(final String str) {
        return toLong(str, 0L);
    }

    /**
     * parse a string to int with a default value 0
     * 
     * @param str a string
     * @return a long
     */
    public static int toInt(final String str) {
        return toInt(str, 0);
    }

    /**
     * parse a string to int with default value
     * 
     * @param str a string
     * @param defaultValue if string is empty or is not a number, return default value
     * @return a int
     */
    public static int toInt(final String str, final int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static Integer toInteger(final String str, final Integer defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 仅返回成功解析的数字，无有效数据时返回空list，不会返回null
     */
    public static List<Long> convert(List<String> ids) {
        if (ids == null || ids.isEmpty())
            return Collections.emptyList();

        List<Long> list = new ArrayList<Long>();
        for (String id : ids) {
            Long l = toLong(id, null);
            if (l != null)
                list.add(l);
        }
        return list;
    }

    public static Integer parseInt(String source) {
        return parseInt(source, null);
    }

    public static Integer parseInt(String source, Integer defaultValue) {
        if (StringUtils.isEmpty(source))
            return defaultValue;

        try {
            Integer value = Integer.parseInt(source);
            return value;
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return defaultValue;
        }
    }

    public static Short parseShort(String source) {
        return parseShort(source, null);
    }

    public static Short parseShort(String source, Short defaultValue) {
        if (StringUtils.isEmpty(source))
            return defaultValue;

        try {
            Short value = Short.parseShort(source);
            return value;
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return defaultValue;
        }
    }

    public static Double parseDouble(String source) {
        return parseDouble(source, null);
    }

    public static Double parseDouble(String source, Double defaultValue) {
        if (StringUtils.isEmpty(source))
            return defaultValue;

        try {
            return Double.parseDouble(source);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return defaultValue;
        }
    }

    /**
     * 若某数为null，则强制转为 rst
     * 
     * @param first
     * @param rst
     * @return
     */
    public static int nvl(Integer first, int rst) {
        return first == null ? rst : first;
    }

    public static int nvl(Integer first) {
        return nvl(first, 0);
    }

    public static float nvl(Float first, float rst) {
        return first == null ? rst : first;
    }

    public static float nvl(Float first) {
        return nvl(first, 0);
    }

    public static int getRandInt(int rangeStart, int rangeEnd) {
        int randResult = rangeStart + (int) (Math.random() * (rangeEnd - rangeStart + 1));
        return randResult;
    }

    /**
     * 功能描述: 支持将BigDecimal/Long/Integer/String转换成int<br>
     */
    public static int convertObj2Int(final Object obj) {
        if (obj == null)
            return 0;

        if (obj instanceof BigDecimal)
            return ((BigDecimal) obj).intValue();
        else if (obj instanceof Long)
            return ((Long) obj).intValue();
        else if (obj instanceof Integer)
            return ((Integer) obj).intValue();
        else if (obj instanceof String)
            return toInt((String) obj);
        else
            return 0;
    }

    /**
     * 功能描述: 判断一批long类型的数据，是否都大于0<br>
     */
    public static boolean isAllGreaterThanZero(long... ids) {
        if (ids == null) {
            return false;
        }

        for (long id : ids) {
            if (id <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证Long参数
     */
    public static boolean checkLongParams(Long... params) {
        if (params == null) {
            return false;
        }
        for (Long param : params) {
            if (param == null || param <= 0) {
                return false;
            }
        }
        return true;
    }

    /*
     * int转byte数组,高位在前
     */
    public static byte[] int2Bytes(int count) {
        byte[] byteArr = new byte[4];
        byteArr[3] = (byte) (count & 0xFF);
        byteArr[2] = (byte) (count >> 8 & 0xFF);
        byteArr[1] = (byte) (count >> 16 & 0xFF);
        byteArr[0] = (byte) (count >> 24 & 0xFF);
        return byteArr;
    }

    /**
     * 高位在前bytes数组转int
     * 
     * @param byteArr
     * @return
     */
    public static int bytes2int(byte[] byteArr) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            count <<= 8;
            count |= byteArr[i] & 0xff;
        }
        return count;
    }

    /**
     * double四舍五入保存两位小数
     * 
     * @param number
     * @return
     */
    public static Double doubleFormat(Double number) {
        if (number == null) {
            return null;
        }
        return Double.valueOf(String.format("%.2f", number));
    }

    /**
     * 处理的最大数字达千万亿位 精确到分
     * 
     * @return
     */
    public static String digitUppercase(Number num) {
        if (num == null || num.doubleValue() <= 0) {
            return null;
        }
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        /**
         * 仟 佰 拾 ' ' ' ' $4 $3 $2 $1 万 $8 $7 $6 $5 亿 $12 $11 $10 $9
         */
        String unit1[] = { "", "拾", "佰", "仟" };// 把钱数分成段,每四个一段,实际上得到的是一个二维数组
        String unit2[] = { "元", "万", "亿", "万亿" }; // 把钱数分成段,每四个一段,实际上得到的是一个二维数组
        BigDecimal bigDecimal = new BigDecimal(num.doubleValue());
        bigDecimal = bigDecimal.multiply(new BigDecimal(100));
        // Double bigDecimal = new Double(name*100); 存在精度问题 eg：145296.8
        String strVal = String.valueOf(bigDecimal.toBigInteger());
        String head = strVal.substring(0, strVal.length() - 2); // 整数部分
        String end = strVal.substring(strVal.length() - 2); // 小数部分
        String endMoney = "";
        String headMoney = "";
        if ("00".equals(end)) {
            endMoney = "整";
        } else {
            if (!end.substring(0, 1).equals("0")) {
                endMoney += digit[Integer.valueOf(end.substring(0, 1))] + "角";
            } else if (end.substring(0, 1).equals("0") && !end.substring(1, 2).equals("0")) {
                endMoney += "零";
            }
            if (!end.substring(1, 2).equals("0")) {
                endMoney += digit[Integer.valueOf(end.substring(1, 2))] + "分";
            }
        }
        char[] chars = head.toCharArray();
        Map<String, Boolean> map = new HashMap<String, Boolean>();// 段位置是否已出现zero
        boolean zeroKeepFlag = false;// 0连续出现标志
        int vidxtemp = 0;
        for (int i = 0; i < chars.length; i++) {
            int idx = (chars.length - 1 - i) % 4;// 段内位置 unit1
            int vidx = (chars.length - 1 - i) / 4;// 段位置 unit2
            String s = digit[Integer.valueOf(String.valueOf(chars[i]))];
            if (!"零".equals(s)) {
                headMoney += s + unit1[idx] + unit2[vidx];
                zeroKeepFlag = false;
            } else if (i == chars.length - 1 || map.get("zero" + vidx) != null) {
                headMoney += "";
            } else {
                headMoney += s;
                zeroKeepFlag = true;
                map.put("zero" + vidx, true);// 该段位已经出现0；
            }
            if (vidxtemp != vidx || i == chars.length - 1) {
                headMoney = headMoney.replaceAll(unit2[vidx], "");
                headMoney += unit2[vidx];
            }
            if (zeroKeepFlag && (chars.length - 1 - i) % 4 == 0) {
                headMoney = headMoney.replaceAll("零", "");
            }
        }
        return headMoney + endMoney;
    }

    public static boolean isEmpty(Number number) {
        return number == null || number.longValue() < 0;
    }

    public static boolean isInvalid(Number number) {
        return number == null || number.longValue() <= 0;
    }

    public static boolean hasInvalid(Number... numbers) {
        return Arrays.stream(numbers).anyMatch(NumberUtils::isInvalid);
    }

    public static void main(String[] args) {
        System.out.println(digitUppercase(1));
    }
}
