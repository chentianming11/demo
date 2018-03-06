//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lianjia.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Is {
    private static final Predicate<? super Object> pempty = (s) -> {
        return empty(s);
    };
    private static final Pattern EMAIL = Pattern.compile("^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))$", 2);
    private static final Pattern URL = Pattern.compile("^(?:(?:https?|ftp):\\/\\/)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))(?::\\d{2,5})?(?:\\/\\S*)?$", 2);
    private static final Pattern MOBILE = Pattern.compile("^(01|1)\\d{10}$");
    private static final Pattern IP4 = Pattern.compile("^(?:(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.){3}(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])$");
    private static final Pattern IP6 = Pattern.compile("^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$|^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$");

    private Is() {
    }

    public static boolean empty(String s) {
        return StringUtils.isEmpty(StringUtils.stripToEmpty(s));
    }

    public static boolean empty(Object o) {
        if(o == null) {
            return true;
        } else if(o instanceof CharSequence) {
            return StringUtils.isEmpty((CharSequence)o);
        } else if(o instanceof Collection) {
            return CollectionUtils.isEmpty((Collection)o);
        } else {
            if(o instanceof Map) {
                if(MapUtils.isEmpty((Map)o)) {
                    return true;
                }

                String s = ((Map)o).get("") + "";
                if(empty(s)) {
                    return true;
                }
            }

            return o instanceof Object[]?ArrayUtils.isEmpty((Object[])((Object[])o)):false;
        }
    }

    public static boolean include(String str, String searchStr) {
        return StringUtils.contains(StringUtils.stripToEmpty(str), StringUtils.stripToEmpty(searchStr));
    }

    public static boolean startWith(String str, String prefix) {
        return StringUtils.startsWith(StringUtils.stripToEmpty(str), StringUtils.stripToEmpty(prefix));
    }

    public static boolean endsWith(String str, String prefix) {
        return StringUtils.endsWith(StringUtils.stripToEmpty(str), StringUtils.stripToEmpty(prefix));
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return StringUtils.equalsIgnoreCase(str1, str2);
    }

    public static boolean equals(Object object1, Object object2) {
        return Objects.equals(object1, object2);
    }

    public static boolean email(String s) {
        return empty(s)?false:EMAIL.matcher(StringUtils.stripToEmpty(s)).matches();
    }

    public static boolean url(String s) {
        return empty(s)?false:URL.matcher(StringUtils.stripToEmpty(s)).matches();
    }

    public static boolean mobile(String... s) {
        return empty((Object)s)?false:Arrays.asList(s).stream().allMatch((t) -> {
            return MOBILE.matcher(StringUtils.stripToEmpty(t.replaceAll("([\\s\\t])|(^086|86)|", "").replaceAll("^\\+", ""))).matches();
        });
    }

    public static boolean ip(String s) {
        return empty(s)?false:ip4(StringUtils.stripToEmpty(s));
    }

    private static boolean ip4(String s) {
        return empty(s)?false:IP4.matcher(StringUtils.stripToEmpty(s)).matches();
    }

    private static boolean ip6(String s) {
        return empty(s)?false:IP6.matcher(StringUtils.stripToEmpty(s)).matches();
    }

    static final class all {
        all() {
        }

        public static boolean empty(Object o) {
            return Is.empty(o)?true:(o instanceof Collection?((Collection)o).stream().allMatch(Is.pempty):(o instanceof Map?MapUtils.isEmpty((Map)o):(o instanceof Object[]?Arrays.asList((Object[])((Object[])o)).stream().allMatch(Is.pempty):false)));
        }
    }

    static final class not {
        not() {
        }

        public static boolean empty(String s) {
            return !Is.empty(s);
        }

        public static boolean empty(Object o) {
            return !Is.empty(o);
        }

        public static boolean equals(Object object1, Object object2) {
            return !Objects.equals(object1, object2);
        }

        public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
            return !StringUtils.equalsIgnoreCase(str1, str2);
        }
    }
}
