package com.thoughtworks.mvc.core;

import app.domains.Person;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BaseControllerTest {
    BaseController baseController = new BaseController();

    @Test
    public void should_assign_values_to_object_from_map() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Map map = new HashMap();
        map.put("name", "liqiang");
        map.put("age", "13");

        Person person = baseController.toObject(Person.class, map);

        assertEquals("liqiang", person.getName());
        assertEquals(13, person.getAge());
    }

    @Test
    public void should_assign_complex_values_to_object_from_map() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Map map = new HashMap();
        map.put("name", "liqiang");
        map.put("age", "13");

        Map phoneMap = new HashMap();
        phoneMap.put("country", "86");
        phoneMap.put("province", "29");
        phoneMap.put("rest", "888888");

        map.put("phone", phoneMap);

        Person person = baseController.toObject(Person.class, map);

        assertEquals("liqiang", person.getName());
        assertEquals(13, person.getAge());
        assertEquals(86, person.getPhone().getCountry());
        assertEquals(29, person.getPhone().getProvince());
        assertEquals(888888, person.getPhone().getRest());
    }
}
