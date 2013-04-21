package com.thoughtworks.mvc.core;

import org.junit.Test;
import testutil.MockHttpServletRequest;

import java.util.Enumeration;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ParamsCreatorTest {
    MockHttpServletRequest request = new MockHttpServletRequest();

    @Test
    public void should_extract_map_from_submit_params() {
        //person.name -> liqiang
        //person.age -> 13

        request.setParameterNames(new ArrayEnumerator<String>(new String[]{"person.name", "person.age"}));
        request.addParameterPair("person.name", "liqiang");
        request.addParameterPair("person.age", "13");

        Map map = ParamsCreator.create(request);

        Map person = (Map)map.get("person");
        assertEquals("liqiang", person.get("name"));
        assertEquals("13", person.get("age"));
    }

    @Test
    public void should_extract_for_nested_structure() {
        //person.phone.country = 86
        //person.phone.province = 29
        //person.phone.rest = 88888888
        //person.name = liqiang
        //person.age = 13
        request.setParameterNames(new ArrayEnumerator<String>(new String[]{"person.phone.country",
                                                                           "person.phone.province",
                                                                           "person.phone.rest",
                                                                           "person.name",
                                                                           "person.age"}));
        request.addParameterPair("person.phone.country", "86");
        request.addParameterPair("person.phone.province", "29");
        request.addParameterPair("person.phone.rest", "88888888");
        request.addParameterPair("person.name", "liqiang");
        request.addParameterPair("person.age", "13");

        Map map = ParamsCreator.create(request);

        Map person = (Map)map.get("person");
        assertEquals("liqiang", person.get("name"));
        assertEquals("13", person.get("age"));

        Map phone = (Map) person.get("phone");
        assertEquals("86", phone.get("country"));
        assertEquals("29", phone.get("province"));
        assertEquals("88888888", phone.get("rest"));

    }

    static class ArrayEnumerator<T> implements Enumeration<T> {
        private T[] array;
        private int index = 0;

        ArrayEnumerator(T[] array) {
            this.array = array;
        }

        @Override
        public boolean hasMoreElements() {
            return index < array.length;
        }

        @Override
        public T nextElement() {
            return array[index++];
        }
    }
}
