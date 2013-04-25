package com.thoughtworks.mvc.core.urlAndVerb;

import com.thoughtworks.mvc.verb.HttpMethod;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SimpleUrlAndVerbTest {
    @Test
    public void should_ignore_mime_type_when_matching() {
        SimpleUrlAndVerb urlAndVerb = new SimpleUrlAndVerb(HttpMethod.GET, "/books/1.html");
        SimpleUrlAndVerb urlAndVerb1 = new SimpleUrlAndVerb(HttpMethod.GET, "/books/1");

        assertTrue(urlAndVerb.match(urlAndVerb1));
    }
}
