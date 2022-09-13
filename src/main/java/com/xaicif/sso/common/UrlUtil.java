package com.xaicif.sso.common;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UrlUtil {
    public static Map<String, Object> parseUrlParams(String url) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isBlank(url)) {
            return params;
        }
        String[] urlParts = url.split("\\?");
        if (urlParts.length < 2) {
            return params;
        }
        String[] paramSplit = urlParts[1].split("&");
        for (String param : paramSplit) {
            String[] keyValue = param.split("=");
            if (keyValue.length != 2) {
                continue;
            }
            params.put(keyValue[0], keyValue[1]);
        }
        return params;
    }

    public static String joinParam(String url, String key, String value) {
        // 如果参数为空, 直接返回
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return url;
        }
        return joinParam(url, key + "=" + value);
    }

    public static String joinParam(String url, String parameStr) {
        // 如果参数为空, 直接返回
        if (parameStr == null || parameStr.length() == 0) {
            return url;
        }
        if (url == null) {
            url = "";
        }
        int index = url.lastIndexOf('?');
        // ? 不存在
        if (index == -1) {
            return url + '?' + parameStr;
        }
        // ? 是最后一位
        if (index == url.length() - 1) {
            return url + parameStr;
        }
        // ? 是其中一位
        if (index > -1 && index < url.length() - 1) {
            String separatorChar = "&";
            // 如果最后一位是 不是&, 且 parameStr 第一位不是 &, 就增送一个 &
            if (url.lastIndexOf(separatorChar) != url.length() - 1 && parameStr.indexOf(separatorChar) != 0) {
                return url + separatorChar + parameStr;
            } else {
                return url + parameStr;
            }
        }
        // 正常情况下, 代码不可能执行到此
        return url;
    }
}
