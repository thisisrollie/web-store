package com.rolliedev.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {

    private static final String JSP_PATH_FORMAT = "WEB-INF/jsp/%s.jsp";

    public static String getPath(String jspName) {
        return String.format(JSP_PATH_FORMAT, jspName);
    }
}
