package com.thoughtworks.mvc.core.urlAndVerb;

import com.thoughtworks.mvc.core.param.Param;

public abstract class UrlAndVerb {

    public abstract boolean match(SimpleUrlAndVerb simpleUrlAndVerb);

    protected String normalizedUrl(String url) {
        return url.endsWith("/") ? url : url + "/";
    }

    public abstract Param extract(String requestUrl);
}
