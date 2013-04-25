package com.thoughtworks.mvc.mime;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Sets.newHashSet;

public enum MimeType {
    HTML("text/html"),
    JSON("application/json"),
    XML("application/xml");

    private String value;

    MimeType(String value) {
        this.value = value;
    }

    public static Set<MimeType> getMimeTypes(HttpServletRequest req) {
        Set<MimeType> types = new LinkedHashSet<MimeType>();

        MimeType type = tryGetFromUrl(req);
        if(type != null) return newHashSet(type);

        types.addAll(tryGetFromHeader(req));

        return types;
    }

    private static Set<MimeType> tryGetFromHeader(HttpServletRequest req) {
        Set<MimeType> types = new LinkedHashSet<MimeType>();

        for (String s : req.getHeader("accept").split(",")) {
            for (MimeType mimeType : MimeType.values()) {
                if(mimeType.matches(s)) {
                    types.add(mimeType);
                }
            }
        }
        return types;
    }

    private boolean matches(String mimeValue) {
        return mimeValue.equals(value);
    }

    private static MimeType tryGetFromUrl(HttpServletRequest req) {
        Matcher matcher = Pattern.compile(".*\\.([A-Za-z]*)").matcher(req.getRequestURI());
        if(matcher.matches()) {
            return MimeType.valueOf(matcher.group(1).toUpperCase());
        }
        return null;
    }

    public String value() {
        return value;
    }
}
