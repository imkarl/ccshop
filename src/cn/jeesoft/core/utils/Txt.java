package cn.jeesoft.core.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JDiy字符串操作工具类.
 *
 * @author 子秋(ziquee)  http://www.jdiy.org
 */
public final class Txt {

    private String str;

    /**
     * 创建一个新的Txt字符串处理对象.
     * 可以使用该对象处理与正则表达式相关的操作.
     *
     * @param str 要被处理的字符串.
     */
    public Txt(String str) {
        this.str = str;
    }

    /**
     * 返回字符串dest在字符串source中出现的次数.要查找的字符串是区分大小写的.
     * @param source 原字符串.
     * @param dest 要搜索的字符串.
     * @return 字符串dest在字符串source中出现的次数.
     */
    public static int containSum(String source, String dest) {
        //这是一个很好的字符串查找算法： 查找dest在source中出现的次数

        return (source.length() - new Txt(source).restr(dest, "").toString().length()) / dest.length();
    }

    /**
     * 截字符串前面的指定数量个字符.
     * 如果字节符度超过该数量,则后面用省略号代替(因为省略号会占去两个字符，因此当有省略号时,实际显示的字符数=toCount-2).
     *
     * @param str     要截取取的字符串。
     * @param toCount 要截取的字符数（一个汉字计两个字符）
     * @return 截取后的新字符串。
     */
    public static String cut(String str, int toCount) {
        try {
            StringBuilder reStr = new StringBuilder("");
            char[] tempChar = str.toCharArray();
            for (int i = 0; i < tempChar.length; i++) {
                byte[] b = String.valueOf(tempChar[i]).getBytes("utf-8");
                toCount -= b.length > 1 ? 2 : 1;
                if (toCount <= 0) {
                    if (i == tempChar.length - 1) {
                        reStr.append(tempChar[i]);
                    } else {
                        reStr.append("…");
                        break;
                    }
                } else {
                    reStr.append(tempChar[i]);
                }
            }
            return reStr.toString();
        } catch (java.io.UnsupportedEncodingException ex) {
            return str;
        }
    }

    /**
     * 这是一个字符串注入（或叫做字符串模板替换）方法.
     * 在JDiy日志管理中大量应用了此方法.
     * 首先定义一个带有一个或多个"{}"占位符的字符串模版，然后占位符处的内容会由后面的参数依次注入替换.
     * <br /><strong>下面是一个示例性的代码：</strong><br/>
     * String log = "对不起，要更新的字段出错。字段名:{}, 字段值:{}, 错误原因：{}";<br />
     * String log2 = Txt.inject(log, "name", "ziquee", "无此字段");<br />
     * //执行结果如下：<br />
     * log2="对不起，要更新的字段出错。字段名:name, 字段值:ziquee, 错误原因:无此字段";
     * @param inputString 原始字符串模板.
     * @param injects 注入进去的字符串列表.
     * @return 执行注入后的字符串.
     * @see #injectBy(String, String, String...)
     */
    public static String inject(String inputString, String... injects) {
        return injectBy(inputString, "{}", injects);
    }

    /**
     * 这是一个字符串注入（或叫做字符串模板替换）方法.
     * 与{@link #inject(String, String...)}不同的是，此方法可以自已定义占位符.
     * <br /><strong>下面是一个示例性的代码：</strong><br/>
     * String log = "对不起，要更新的字段出错。字段名:?, 字段值:?, 错误原因：?";<br />
     * String log2 = Txt.injectBy(log, "?", "name", "ziquee", "无此字段");<br />
     * //执行结果如下：<br />
     * log2="对不起，要更新的字段出错。字段名:name, 字段值:ziquee, 错误原因:无此字段";
     * @param inputString 原始字符串模板.
     * @param injectString 字符串占位符.
     * @param injects 注入进去的字符串列表.
     * @return 执行注入后的字符串.
     * @see #inject(String, String...)
     */
    public static String injectBy(String inputString, String injectString, String... injects) {
        if (inputString == null || injects == null || injects.length == 0) return inputString;
        if (injectString == null || "".equals(injectString)) injectString = "{}";
        StringBuilder sb = new StringBuilder();
        int begin=0, replaced=0, len =injectString.length(), len1=injects.length;
        while (true) {
            int sub = inputString.indexOf(injectString, begin);
            if(sub!=-1){
                if(replaced<len1){
                    sb.append(inputString.substring(begin, sub)).append(injects[replaced++]);
                    begin = sub + len;
                }else{
                    break;
                }
            }else{
                break;
            }
        }
        sb.append(inputString.substring(begin));
        return sb.toString();
    }

    /**
     * 将字符串数组元素按指定的字符串分隔符连成一个新的字符串。
     *
     * @param arr     要连接的字符串数组.
     * @param joinStr 连接字符串. 如果此字符串为空(null)，则使用逗号加一个空格(, )进行连接。
     * @return 返回arr的每个下标元素按joinStr连接成的新的字符串. 如果数组arr为空(null),则返回结果也将为null.
     */
    public static String join(String[] arr, String joinStr) {
        if (null == arr) return null;
        if (null == joinStr) joinStr = ", ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(joinStr);
            sb.append(arr[i]);
        }
        return sb.toString();
    }


    /**
     * 将字符串按指定的分隔符分割成为一个数组.   <br />
     * 与String.split(String s)、StringTokenizer不同的是，此方法将比它们更加高效快速.
     * 在需要将字符串split转换为数组时，应该优先使用此方法.
     * @param str 要转换的字符串.
     * @param sp  分隔字符串.
     * @return 转换后的String数组.
     * @since JDiy-1.6 及以上版本新增的方法.
     */
    public static String[] split(String str, String sp) {
        List<String> arrayList = new ArrayList<String>();
        int index = 0, offset = 0, len = sp.length();
        while ((index = str.indexOf(sp, index + len)) != -1) {
            arrayList.add(str.substring(offset, index));
            offset = index + len;
        }
        if(offset<str.length()) arrayList.add(str.substring(offset));
        return arrayList.toArray(new String[arrayList.size()]);
    }


    /**
     * 此方法将HTML内容转换为普通文本.  <br/>
     * 此方法将通过对正则替换，删除inputString中的HTML标签，并返回纯文本内容．
     * @param inputString 要转换的HTML代码.
     * @return 转换后的纯文本内容.
     * @since 这是JDiy-2.1及后续版本新增的方法.
     */
    public static String htmlToText(String inputString) {
        String htmlStr = inputString;
        String textStr = "";
        String scriptRegEx = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
        String styleRegEx = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
        String htmlRegEx1 = "<[^>]*>";
        String htmlRegEx2 = "<[^>]*";
        try {
            Pattern scriptPattern = Pattern.compile(scriptRegEx, Pattern.CASE_INSENSITIVE);
            Matcher scriptMatcher = scriptPattern.matcher(htmlStr);
            htmlStr = scriptMatcher.replaceAll("");
            Pattern stylePattern = Pattern.compile(styleRegEx, Pattern.CASE_INSENSITIVE);
            Matcher styleMatcher = stylePattern.matcher(htmlStr);
            htmlStr = styleMatcher.replaceAll("");
            Pattern htmlPattern1 = Pattern.compile(htmlRegEx1, Pattern.CASE_INSENSITIVE);
            Matcher htmlMatcher1 = htmlPattern1.matcher(htmlStr);
            htmlStr = htmlMatcher1.replaceAll("");
            Pattern htmlPattern2 = Pattern.compile(htmlRegEx2, Pattern.CASE_INSENSITIVE);
            Matcher htmlMatcher2 = htmlPattern2.matcher(htmlStr);
            htmlStr = htmlMatcher2.replaceAll("");
            textStr = htmlStr;
        } catch (Exception e) {
            System.err.println("->Txt.htmlToText(String inputString) ERROR:" + e.getMessage());
        }
        textStr = textStr.replaceAll("&acute;", "\'");
        textStr = textStr.replaceAll("&quot;", "\"");
        textStr = textStr.replaceAll("&lt;", "<");
        textStr = textStr.replaceAll("&gt;", ">");
        textStr = textStr.replaceAll("&nbsp;", " ");
        textStr = textStr.replaceAll("&amp;", "&");
        return textStr;
    }

    /**
     * 将指定的字符串内容进行HTML标签转换过滤. <br />
     * 此方法会将内容中的HTML标签转换成普通字符输出以防止非法的HTML代码被执行; 同时此方法自动识别URL地址、邮件地址并为其添加链接.<br />
     * <strong>注意：</strong> 由在线WEB编辑器添加的内容，不应调用此方法，否则编辑器上传的所有HTML代码将被转换成普通字符输出。
     *
     * @param str 要进行HTML标签过滤处理处理的原字符串。
     * @return 转换之后的字符串。
     */
    @SuppressWarnings("unused")
    public static String encodeHTML(String str) {
        String s = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;").replaceAll("'", "&acute; ")
                .replaceAll("  ", " &nbsp;")
                .replaceAll("\\r\\n", "<br/>")
                .replaceAll("\\n", "<br/>");
        Txt rep = new Txt(s);
        rep.replace("http://([%#=&\\?\\./a-zA-Z0-9]+)", "<a href=\"http://$1\" target=\"_blank\">http://$1</a>");
        rep.replace("([-_a-z0-9]+?)@([-_\\.a-zA-Z0-9]{5,})", "<a href=\"mailto:$1@$2\" target=\"_blank\">$1@$2</a>");
        return rep.toString();
    }

    /**
     * 将给定的字符串s转化为一个双精度浮点数. 如果s不能转化, 则返回0.0d.
     * 此方法与Javascript脚本的parseFloat执行方式类似.
     * 即程序通过给定的字符串，从左往右匹配截取数字字符，并将其转化为double类型后返回.
     *
     * @param s 要被转化的字符串
     * @return 一个有效的双精度浮点数.
     * @see #parseDouble(String, double)
     */
    public static double parseDouble(String s) {
        return parseDouble(s, 0.0d);
    }

    /**
     * 将给定的字符串s转化为一个双精度浮点数. 如果s不能转化, 则返回defV
     *
     * @param s    要被转化的字符串
     * @param defV 当指定的字符串不能转换时,要返回的默认值.
     * @return 一个有效的双精度浮点数.
     * @see #parseDouble(String)
     */
    public static double parseDouble(String s, double defV) {
        try {
            return Double.parseDouble(new Txt(s).replace("^(\\d+)(\\.*)(\\d*)(.|\r|\n)*$", "$1$2$3").toString());
        } catch (Exception e) {
            return defV;
        }
    }

    /**
     * 将给定的字符串s转化为一个整数. 如果s不能转化为一个整数,则返回0.
     * 此方法与Javascript脚本的parseInt执行方式类似.
     * 即程序通过给定的字符串，从左往右匹配截取数字字符，并将其转化为整数类型后返回.
     * @param s 要被转化的字符串
     * @return 一个有效的整数.
     * @see #parseInt(String, int)
     */
    public static int parseInt(String s) {
        return parseInt(s, 0);
    }

    /**
     * 将给定的字符串s转化为一个整数. 如果s不能转化为一个整数,则返回defV
     *
     * @param s    要被转化的字符串
     * @param defV 如果字符串不能被转化成一个有效的整数，程序将返回defV.
     * @return 一个有效的整数.
     * @see #parseInt(String)
     */
    public static int parseInt(String s, int defV) {
        try {
            return Integer.parseInt(new Txt(s).replace("(\\d+)(.|\r|\n)*$", "$1").toString());
        } catch (Exception e) {
            return defV;
        }
    }

    /**
     * 将给定的字符串s转化为一个长整数. 如果s不能转化为一个长整数,则返回0L.
     *<br/>程序将通过给定的字符串，从左往右匹配截取数字字符，并将其转化为长整数类型后返回.
     *
     * @param s 要被转化的字符串
     * @return 一个有效的长整数.
     * @see #parseLong(String, long)
     */
    public static long parseLong(String s) {
        return parseLong(s, 0L);
    }

    /**
     * 将给定的字符串s转化为一个长整数. 如果s不能转化为一个长整数,则返回defV.
     *
     * @param s    要被转化的字符串.
     * @param defV 如果字符串不能被转化成一个有效的长整数，程序将返回defV.
     * @return 一个有效的长整数
     * @see #parseLong(String)
     */
    public static long parseLong(String s, long defV) {
        try {
            return Long.parseLong(new Txt(s).replace("(\\d+)(.|\r|\n)*$", "$1").toString());
        } catch (Exception e) {
            return defV;
        }
    }

    /**
     * 测试字符串是否能够匹配该正则模式(完全匹配).
     *
     * @param regStr 要匹配的正则模式.
     *               由于此方法为完全匹配，因此regStr正则模式往往以^开头，以$结尾.
     * @return 当匹配成功时返回true, 否则返回false.
     * @see #find(String)
     */
    public boolean test(String regStr) {
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 测试字符串是否包含该正则模式.
     *
     * @param regStr 要匹配的正则模式
     * @return 当找到匹配时返回true, 否则返回false
     * @see #test(String)
     */
    public boolean find(String regStr) {
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();

    }

    /**
     * 将字符串中符合regStr正则模式的字符串替换为s1. 此操作将替换所有符合正则匹配条件的字符串.
     * 此方法实现的功能与String对象的replaceAll(String, String)方法类似.
     *
     * @param regStr 用于匹配的正则表达式.
     * @param s1     要替换符合匹配内容的字符串.
     * @return 当前的Txt对象.
     * @see #replaceFirst(String, String)
     * @see #restr(String, String)
     */
    public Txt replace(String regStr, String s1) {
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll(s1);
        return this;
    }

    /**
     * 将字符串中符合正则匹配的字符串替换为s1. 此操作将仅替换一次符合正则匹配条件的字符串.
     * 此方法实现的功能与String对象的replaceFirst(String, String)方法类似.
     *
     * @param regStr 用于匹配的正则表达式
     * @param s1     要替换符合匹配内容的字符串.
     * @return 当前的Txt对象.
     * @see #replace(String, String)
     * @see #restr(String, String)
     */
    @SuppressWarnings("unused")
    public Txt replaceFirst(String regStr, String s1) {
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceFirst(s1);
        return this;
    }


    /**
     * 将字符串中的s1替换成s2. 与{@link #replace(String, String)}方法不同的是，此方法仅限普通字符串替换，而不使用正则表达式。
     *
     * @param s1 要替换的普通字符串.
     * @param s2 要替换成的字符串
     * @return 当前的Txt对象.
     * @see #replace(String, String)
     * @since 此方法为JDiy-1.9 及后续版本新增的功能.
     */
    public Txt restr(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        int b, e = 0, len = s1.length();
        while ((b = str.indexOf(s1, e)) != -1) {
            sb.append(str.substring(e, b));
            sb.append(s2);
            e = b + len;
        }
        if (e < str.length()) sb.append(str.substring(e));
        str = sb.toString();
        return this;
    }



    /**
     * 返回处理之后的字符串.
     * @return 处理之后的字符串.
     */
    @Override
    public String toString() {
        return str;
    }
}
