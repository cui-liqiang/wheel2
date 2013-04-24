package com.thoughtworks.mvc.core.urlAndVerb;

import com.thoughtworks.mvc.verb.HttpMethod;

public class SimpleUrlAndVerb extends UrlAndVerb {
    protected final HttpMethod httpMethod;
    protected final String url;

    public SimpleUrlAndVerb(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = normalizedUrl(url);
    }

    @Override
    public boolean match(SimpleUrlAndVerb simpleUrlAndVerb) {
        if (httpMethod != simpleUrlAndVerb.httpMethod) return false;
        if (url != null ? !url.equals(simpleUrlAndVerb.url) : simpleUrlAndVerb.url != null) return false;
        return true;
    }
}
