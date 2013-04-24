package com.thoughtworks.mvc.core.urlAndVerb;

import com.thoughtworks.mvc.verb.HttpMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUrlAndVerb extends UrlAndVerb {

    private final HttpMethod httpMethod;
    private String paramName;
    private String urlRegex;

    public ParamUrlAndVerb(HttpMethod httpMethod, String paramName, String urlRegex) {
        this.httpMethod = httpMethod;
        this.paramName = paramName;
        this.urlRegex = normalizedUrl(urlRegex);
    }

    @Override
    public boolean match(SimpleUrlAndVerb simpleUrlAndVerb) {
        if (httpMethod != simpleUrlAndVerb.httpMethod) return false;

        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(simpleUrlAndVerb.url);
        return matcher.matches();
    }
}
