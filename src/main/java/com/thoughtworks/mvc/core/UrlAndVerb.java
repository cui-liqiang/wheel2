package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.verb.HttpMethod;

public class UrlAndVerb {
    private final HttpMethod httpMethod;
    private final String url;

    public UrlAndVerb(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = normalizedUrl(url);
    }

    private String normalizedUrl(String url) {
        return url.endsWith("/") ? url : url + "/";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlAndVerb)) return false;

        UrlAndVerb that = (UrlAndVerb) o;

        if (httpMethod != that.httpMethod) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = httpMethod != null ? httpMethod.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
