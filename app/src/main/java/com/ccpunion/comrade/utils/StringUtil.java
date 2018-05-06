package com.ccpunion.comrade.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    //------------------常量定义
    /**
     * 姓名正则表达式=[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*
     */
    public static final String NAME = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";
    /**
     * Email正则表达式=^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$
     */
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 电话号码正则表达式= (^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)
     */
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 手机号码正则表达式=^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$
     */
    public static final String MOBILE = "^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$";

    /**
     * IP地址正则表达式 ((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))
     */
    public static final String IPADDRESS = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";

    /**
     * Integer正则表达式 ^-?(([1-9]\d*$)|0)
     */
    public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";
    /**
     * 正整数正则表达式 >=0 ^[1-9]\d*|0$
     */
    public static final String INTEGER_NEGATIVE = "^[1-9]\\d*|0$";
    /**
     * 负整数正则表达式 <=0 ^-[1-9]\d*|0$
     */
    public static final String INTEGER_POSITIVE = "^-[1-9]\\d*|0$";
    /**
     * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
     */
    public static final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
    /**
     * 正Double正则表达式 >=0  ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$
     */
    public static final String DOUBLE_NEGATIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
    /**
     * 负Double正则表达式 <= 0  ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$
     */
    public static final String DOUBLE_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
    /**
     * 年龄正则表达式 ^(?:[1-9][0-9]?|1[01][0-9]|120)$ 匹配0-120岁
     */
    public static final String AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
    /**
     * 邮编正则表达式  [1-9]\d{5}(?!\d) 国内6位邮编
     */
    public static final String CODE = "[1-9]\\d{5}(?!\\d)";

    /**
     * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
     */
    public static final String STR_ENG_NUM = "^\\w+$";
    /**
     * 匹配由26个英文字母组成的字符串  ^[A-Za-z]+$
     */
    public static final String STR_ENG = "^[A-Za-z]+$";
    /**
     * 匹配中文字符 ^[\u0391-\uFFE5]+$
     */
    public static final String STR_CHINA = "^[\u0391-\uFFE5]+$";
    /**
     * 过滤特殊字符串正则
     * regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
     */
    public static final String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    /**
     * 只能输英文 数字 中文 ^[a-zA-Z0-9\u4e00-\u9fa5]+$
     */
    public static final String STR_ENG_CHA_NUM = "^[a-zA-Z0-9\u4e00-\u9fa5]+$";
    /**
     *
     */
    /***
     * 日期正则 支持：
     * YYYY-MM-DD
     * YYYY/MM/DD
     * YYYY_MM_DD
     * YYYYMMDD
     * YYYY.MM.DD的形式
     */
    public static final String DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)" +
            "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)" +
            "(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)" +
            "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|" +
            "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))";

    /**
     * URL正则表达式
     * 匹配 http www ftp
     */
    public static final String URL = "^(http|https|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?" +
            "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*" +
            "(\\w*:)*(\\w*\\+)*(\\w*\\.)*" +
            "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";

    /**
     * 身份证正则表达式
     */
    public static final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +
            "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +
            "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
    /**
     * 1.匹配科学计数 e或者E必须出现有且只有一次 不含Dd
     * 正则 ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$
     */
    public final static String SCIENTIFIC_A = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$";
    /**
     * 2.匹配科学计数 e或者E必须出现有且只有一次 结尾包含Dd
     * 正则 ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$
     */
    public final static String SCIENTIFIC_B = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$";
    /**
     * 3.匹配科学计数 是否含有E或者e都通过 结尾含有Dd的也通过（针对Double类型）
     * 正则 ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$
     */
    public final static String SCIENTIFIC_C = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$";
    /**
     * 4.匹配科学计数 是否含有E或者e都通过 结尾不含Dd
     * 正则 ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$
     */
    public final static String SCIENTIFIC_D = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$";

    /**
     * 匹配26个字母 或数字  或字母急数字 组合
     */
    public final static String PWD = "^[0-9A-Za-z]{6,20}$";

    public static final String REX_NOT_NUM = "\\D*";//非数字
    public static final String REX_NOT_PER = "[^\\d\\.]";//非百分比
    public static final String REX_NOT_PRICE = "[^\\d\\-\\.]*";//非金额

////------------------验证方法        

    /**
     * 判断字段是否为空 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static synchronized boolean StrisNull(String str) {
        return null == str || str.trim().length() <= 0 ? true : false;
    }

    /**
     * 判断字段是非空 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean StrNotNull(String str) {
        return !StrisNull(str);
    }

    /**
     * 字符串null转空
     *
     * @param str
     * @return boolean
     */
    public static String nulltoStr(String str) {
        return StrisNull(str) ? "" : str;
    }

    /**
     * 字符串null赋值默认值
     *
     * @param str    目标字符串
     * @param defaut 默认值
     * @return String
     */
    public static String nulltoStr(String str, String defaut) {
        return StrisNull(str) ? defaut : str;
    }

    /**
     * 判断字段是否为Email 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str) {
        return Regular(str, EMAIL);
    }

    /**
     * 判断是否为电话号码 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isPhone(String str) {
        return Regular(str, PHONE);
    }

    /**
     * 判断是否为手机号码 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        return Regular(str, MOBILE);
    }

    /**
     * 判断是否为Url 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isUrl(String str) {
        return Regular(str, URL);
    }

    /**
     * 判断是否为IP地址 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isIpaddress(String str) {
        return Regular(str, IPADDRESS);
    }

    /**
     * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isNumber(String str) {
        return Regular(str, DOUBLE);
    }

    /**
     * 判断字段是否为INTEGER  符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isInteger(String str) {
        return Regular(str, INTEGER);
    }

    /**
     * 判断字段是否为正整数正则表达式 >=0 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isINTEGER_NEGATIVE(String str) {
        return Regular(str, INTEGER_NEGATIVE);
    }

    /**
     * 判断字段是否为负整数正则表达式 <=0 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isINTEGER_POSITIVE(String str) {
        return Regular(str, INTEGER_POSITIVE);
    }

    /**
     * 判断字段是否为DOUBLE 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isDouble(String str) {
        return Regular(str, DOUBLE);
    }

    /**
     * 判断字段是否为正浮点数正则表达式 >=0 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isDOUBLE_NEGATIVE(String str) {
        return Regular(str, DOUBLE_NEGATIVE);
    }

    /**
     * 判断字段是否为负浮点数正则表达式 <=0 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isDOUBLE_POSITIVE(String str) {
        return Regular(str, DOUBLE_POSITIVE);
    }

    /**
     * 判断字段是否为日期 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isDate(String str) {
        return Regular(str, DATE_ALL);
    }

    /**
     * 判断字段是否为年龄 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isAge(String str) {
        return Regular(str, AGE);
    }

    /**
     * 判断字段是否超长
     * 字串为空返回fasle, 超过长度{leng}返回ture 反之返回false
     *
     * @param str
     * @param leng
     * @return boolean
     */
    public static boolean isLengOut(String str, int leng) {
        return StrisNull(str) ? false : str.trim().length() > leng;
    }

    /**
     * 判断字段是否为身份证 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isIdCard(String str) {
        if (StrisNull(str)) return false;
        if (str.trim().length() == 15 || str.trim().length() == 18) {
            return Regular(str, IDCARD);
        } else {
            return false;
        }

    }

    /**
     * 判断字段是否为邮编 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isCode(String str) {
        return Regular(str, CODE);
    }

    /**
     * 判断字符串是不是全部是汉字
     *
     * @param str
     * @return boolean
     */
    public static boolean isChina(String str) {
        return Regular(str, STR_CHINA);
    }

    /**
     * 判断字符串是不是全部是英文字母
     *
     * @param str
     * @return boolean
     */
    public static boolean isEnglish(String str) {
        return Regular(str, STR_ENG);
    }

    /**
     * 判断字符串是不是全部是英文字母+数字
     *
     * @param str
     * @return boolean
     */
    public static boolean isENG_NUM(String str) {
        return Regular(str, STR_ENG_NUM);
    }

    /**
     * 判断字符串是不是英文、数字、英文数字组合
     */
    public static boolean isPWD(String str) {
        return Regular(str, PWD);
    }

    /**
     * 过滤特殊字符串 返回过滤后的字符串
     *
     * @param str
     * @return boolean
     */
    public static String filterStr(String str) {
        Pattern p = Pattern.compile(STR_SPECIAL);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     *
     * @param str     匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    private static boolean Regular(String str, String pattern) {
        System.out.println("pattern=" + pattern);
        if (null == str || str.trim().length() <= 0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断是不是科学计数法 如果是 返回true
     * 匹配科学计数 e或者E必须出现有且只有一次 结尾不含D
     * 匹配模式可参考本类定义的 SCIENTIFIC_A，SCIENTIFIC_B,SCIENTIFIC_C,SCIENTIFIC_D
     * 若判断为其他模式可调用 Regular(String str,String pattern)方法
     *
     * @param str 科学计数字符串
     * @return boolean
     */
    public static boolean isScientific(String str) {
        if (StrisNull(str))
            return false;
        return Regular(str, SCIENTIFIC_A);
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || "".equals(obj);
    }

    /**
     * 随机生成数字与字母的组合
     *
     * @param len
     * @return
     */
    public static String randomString(int len) {
        StringBuilder random = new StringBuilder();
        while (random.length() < len) {
            String uuid = UUID.randomUUID().toString();
            random.append(uuid.replace("-", ""));
        }
        return random.substring(0, len - 1);
    }

    /**
     * 生成A~Z的大写字母
     * 函数功能说明
     * miracle  2014年5月28日
     * 修改者名字
     * 修改日期
     * 修改内容
     *
     * @return
     */
    public static List<String> getUppercaseLetters() {
        return StringUtil.getLetters('A', 'Z');
    }

    /**
     * 生成a~z的小写字母
     * 函数功能说明
     * miracle  2014年5月28日
     * 修改者名字
     * 修改日期
     * 修改内容
     *
     * @return
     */
    public static List<String> getLowercaseLetters() {
        return StringUtil.getLetters('a', 'z');
    }

    /**
     * 生成连续的字母（A~Z  a~z 任意区间）
     * 函数功能说明
     * miracle  2014年5月28日
     * 修改者名字
     * 修改日期
     * 修改内容
     *
     * @param from
     * @param to
     * @return
     */
    public static List<String> getLetters(char from, char to) {
        List<String> list = new ArrayList<String>();
        int f = (int) from;
        int t = (int) to;
        for (int i = f; i <= t; i++) {
            char value = (char) i;
//			System.out.println(value);
            list.add(value + "");
        }
        return list;
    }

    public static boolean validLenth(String string, int min, int max) {
        if (string != null && string.length() >= min && string.length() <= max) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validLimitLenth(String string, int length) {
        if (string != null && string.length() == length) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 判断是否包含数字
     *
     * @param content
     * @return
     */
    public static boolean hasDigit(String content) {
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        return m.matches();

    }

    public static boolean hasLetter(String content) {
        return Pattern.compile("(?i)[a-z]").matcher(content).find();

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub  
        StringUtil.getLetters('a', 'z');
    }


    /**
     * 判断 一个字符串是否为Empty
     */
    public static boolean isEmpty(String str) {
        return str.length() == 0 && str.trim().length() == 0;
    }

    /**
     * 判断 一个字符串是否为null或“”
     */
    public static boolean isNull(String s) {
        if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    /**
     * 判断 一个字符串是否为null或“”
     */
    public static boolean isNullOrEmpty(String s) {
        if (null == s || s.equals("") || s.equalsIgnoreCase("null") || s.length() == 0 || s.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取字符串的字符长度
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 数据库字符转义
     *
     * @param keyWord
     * @return
     */
    public static String sqliteEscape(String keyWord) {
        keyWord = keyWord.replace("/", "//");
        keyWord = keyWord.replace("'", "''");
        keyWord = keyWord.replace("[", "/[");
        keyWord = keyWord.replace("]", "/]");
        keyWord = keyWord.replace("%", "/%");
        keyWord = keyWord.replace("&", "/&");
        keyWord = keyWord.replace("_", "/_");
        keyWord = keyWord.replace("(", "/(");
        keyWord = keyWord.replace(")", "/)");
        return keyWord;
    }


    //得到两位小数
    public static String getDoubleTwoPointFromString(String doubleMessage) {
        double doublePriceCart = getDoubleFromString(doubleMessage);
        DecimalFormat df = new DecimalFormat(".00");
        return df.format(doublePriceCart);
    }

    /**
     * String得到doule类型
     */
    public static double getDoubleFromString(String priceString) {
        if (!TextUtils.isEmpty(priceString)) {
            double price = Double.parseDouble(priceString);
            return price;
        }
        return 0.00;
    }

}
