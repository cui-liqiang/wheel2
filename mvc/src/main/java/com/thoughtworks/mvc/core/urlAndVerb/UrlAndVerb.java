package com.thoughtworks.mvc.core.urlAndVerb;

import com.thoughtworks.mvc.core.param.Param;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UrlAndVerb {

    public abstract boolean match(SimpleUrlAndVerb simpleUrlAndVerb);

    protected String normalizedUrl(String url) {
        Pattern pattern = Pattern.compile("(.*/[^/]*)\\.[A-Za-z]*");

        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            url = matcher.group(1);
        }

        return url.endsWith("/") ? url : url + "/";
    }

    public abstract Param extract(String requestUrl);
}
