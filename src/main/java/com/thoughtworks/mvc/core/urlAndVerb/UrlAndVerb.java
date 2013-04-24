package com.thoughtworks.mvc.core.urlAndVerb;

public abstract class UrlAndVerb {

    public abstract boolean match(SimpleUrlAndVerb simpleUrlAndVerb);

    protected String normalizedUrl(String url) {
        return url.endsWith("/") ? url : url + "/";
    }
}
