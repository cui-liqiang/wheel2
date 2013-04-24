package com.thoughtworks.mvc.core.urlAndVerb;

import com.thoughtworks.mvc.verb.HttpMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlAndVerbFactory {

    public static final String ROUTE_REGEX = ".*:([^/]*).*";
    public static final String PARAM_NAME_REGEX = ":[^/]*";

    public static UrlAndVerb create(String baseUrl, HttpMethod httpMethod, String url) {
        Pattern routePattern = Pattern.compile(ROUTE_REGEX);
        Matcher matcher = routePattern.matcher(url);

        if (matcher.matches()) {
            return new ParamUrlAndVerb(httpMethod, matcher.group(1), baseUrl + url.replaceFirst(PARAM_NAME_REGEX, "([^/]*)"));
        } else {
            return new SimpleUrlAndVerb(httpMethod, baseUrl + url);
        }
    }

}
