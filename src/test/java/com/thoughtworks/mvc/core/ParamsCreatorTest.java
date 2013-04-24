package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.core.param.FormParamsCreator;
import com.thoughtworks.mvc.core.param.Params;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParamsCreatorTest {
    HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    public void should_extract_map_from_submit_params() {
        //person.name -> liqiang
        //person.age -> 13

        when(request.getParameterNames()).thenReturn(new ArrayEnumerator<String>(new String[]{"person.name", "person.age"}));
        when(request.getParameterValues("person.name")).thenReturn(new String[]{"liqiang"});
        when(request.getParameterValues("person.age")).thenReturn(new String[]{"13"});

        Params map = FormParamsCreator.create(request);

        Params person = (Params) map.get("person");
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
        when(request.getParameterNames()).thenReturn(new ArrayEnumerator<String>(new String[]{"person.phone.country",
                "person.phone.province",
                "person.phone.rest",
                "person.name",
                "person.age"}));
        when(request.getParameterValues("person.phone.country")).thenReturn(new String[]{"86"});
        when(request.getParameterValues("person.phone.province")).thenReturn(new String[]{"29"});
        when(request.getParameterValues("person.phone.rest")).thenReturn(new String[]{"88888888"});
        when(request.getParameterValues("person.name")).thenReturn(new String[]{"liqiang"});
        when(request.getParameterValues("person.age")).thenReturn(new String[]{"13"});

        Params map = FormParamsCreator.create(request);

        Params person = (Params) map.get("person");
        assertEquals("liqiang", person.get("name"));
        assertEquals("13", person.get("age"));

        Params phone = (Params) person.get("phone");
        assertEquals("86", phone.get("country"));
        assertEquals("29", phone.get("province"));
        assertEquals("88888888", phone.get("rest"));
    }

    @Test
    public void should_support_array_style_post_form_data() {
        //people[].name = liqiang
        //people[].age = 13
        //people[].name = zhichao
        //people[].age = 14
        when(request.getParameterNames()).thenReturn(new ArrayEnumerator<String>(new String[]{"people[].name",
                "people[].age"}));

        when(request.getParameterValues("people[].name")).thenReturn(new String[]{"liqiang", "zhichao"});
        when(request.getParameterValues("people[].age")).thenReturn(new String[]{"13", "14"});

        Params map = FormParamsCreator.create(request);

        List people = (ArrayList) map.get("people");

        assertEquals("liqiang", ((Params) people.get(0)).get("name"));
        assertEquals("13", ((Params) people.get(0)).get("age"));
        assertEquals("zhichao", ((Params) people.get(1)).get("name"));
        assertEquals("14", ((Params) people.get(1)).get("age"));
    }

    @Test
    public void should_support_array_style_post_form_data_with_last_name_part_with_array() {
        //people.names[] = liqiang
        //people.names[] = zhichao
        when(request.getParameterNames()).thenReturn(new ArrayEnumerator<String>(new String[]{"people.names[]"}));

        when(request.getParameterValues("people.names[]")).thenReturn(new String[]{"liqiang", "zhichao"});

        Params map = FormParamsCreator.create(request);

        ArrayList people = (ArrayList) ((Params) map.get("people")).get("names");

        assertEquals("liqiang", people.get(0));
        assertEquals("zhichao", people.get(1));
    }

    @Test
    public void should_support_array_style_post_form_data_with_one_name_part_with_array() {
        //names[] = liqiang
        //names[] = zhichao
        when(request.getParameterNames()).thenReturn(new ArrayEnumerator<String>(new String[]{"names[]"}));

        when(request.getParameterValues("names[]")).thenReturn(new String[]{"liqiang", "zhichao"});

        Params map = FormParamsCreator.create(request);

        ArrayList people = (ArrayList) map.get("names");

        assertEquals("liqiang", people.get(0));
        assertEquals("zhichao", people.get(1));
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
