package com.thoughtworks.mvc.core.urlAndVerb;

import com.thoughtworks.mvc.core.param.Param;
import com.thoughtworks.mvc.verb.HttpMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUrlAndVerb extends UrlAndVerb {

    private final HttpMethod httpMethod;
    private String paramName;
    private Pattern pattern;

    public ParamUrlAndVerb(HttpMethod httpMethod, String paramName, String urlRegex) {
        this.httpMethod = httpMethod;
        this.paramName = paramName;
        pattern = Pattern.compile(normalizedUrl(urlRegex));
    }

    @Override
    public boolean match(SimpleUrlAndVerb simpleUrlAndVerb) {
        if (httpMethod != simpleUrlAndVerb.httpMethod) return false;

        Matcher matcher = pattern.matcher(simpleUrlAndVerb.url);
        return matcher.matches();
    }

    @Override
    public Param extract(String requestUrl) {
        Matcher matcher = pattern.matcher(normalizedUrl(requestUrl));
        if (matcher.matches()) {
            return new Param(paramName, matcher.group(1));
        }
        return null;
    }
}
