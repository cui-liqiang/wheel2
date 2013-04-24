package com.thoughtworks.mvc.core.urlAndVerb;

import com.thoughtworks.mvc.verb.HttpMethod;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UrlAndVerbTest {

    @Test
    public void should_match_params_in_url_request() {
        UrlAndVerb route = UrlAndVerbFactory.create("/test", HttpMethod.GET, "/users/:id");


        SimpleUrlAndVerb request = new SimpleUrlAndVerb(HttpMethod.GET, "/test/users/28");

        assertTrue(route.match(request));
    }

    @Test
    public void should_match_simple_route() {
        UrlAndVerb route = UrlAndVerbFactory.create("/test", HttpMethod.GET, "/users/28");

        SimpleUrlAndVerb request = new SimpleUrlAndVerb(HttpMethod.GET, "/test/users/28");

        assertTrue(route.match(request));
    }

}
