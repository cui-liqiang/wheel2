package com.thoughtworks.mvc.util;

import app.domains.Person;
import com.thoughtworks.mvc.core.param.Params;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ObjectBindingUtilTest {
    @Test
    public void should_assign_values_to_object_from_map() throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
        Params params = new Params();
        params.put("name", "liqiang");
        params.put("age", "13");

        Person person = ObjectBindingUtil.toObject(Person.class, params);

        assertEquals("liqiang", person.getName());
        assertEquals(13, person.getAge());
    }

    @Test
    public void should_assign_complex_values_to_object_from_map() throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
        Params params = new Params();
        params.put("name", "liqiang");
        params.put("age", "13");

        Params phoneMap = new Params();
        phoneMap.put("country", "86");
        phoneMap.put("province", "29");
        phoneMap.put("rest", "888888");

        params.put("phone", phoneMap);

        Person person = ObjectBindingUtil.toObject(Person.class, params);

        assertEquals("liqiang", person.getName());
        assertEquals(13, person.getAge());
        assertEquals(86, person.getPhone().getCountry());
        assertEquals(29, person.getPhone().getProvince());
        assertEquals(888888, person.getPhone().getRest());
    }

    @Test
    public void should_convert_map_array_mixture_to_map_and_array() throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
        Params params = new Params();
        params.put("name", "liqiang");
        params.put("age", "13");

        Params params1 = new Params();
        params1.put("name", "zhichao");
        params1.put("age", "14");

        List people = new ArrayList();
        people.add(params);
        people.add(params1);

        Params params2 = new Params();
        params2.put("people", people);

        List<Person> parsedPeople = ObjectBindingUtil.toList(Person.class, (List) params2.get("people"));

        assertEquals("liqiang", parsedPeople.get(0).getName());
        assertEquals(13, parsedPeople.get(0).getAge());

        assertEquals("zhichao", parsedPeople.get(1).getName());
        assertEquals(14, parsedPeople.get(1).getAge());
    }

    @Test
    public void should_convert_map_array_mixture_to_object_with_array() throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
        Params map = new Params();
        map.put("name", "liqiang");
        map.put("age", "13");

        List<Params> phones = new ArrayList<Params>();
        Params phone1 = new Params();
        phone1.put("country", "86");
        phone1.put("province", "29");

        Params phone2 = new Params();
        phone2.put("country", "64");
        phone2.put("province", "44");

        phones.add(phone1);
        phones.add(phone2);

        map.put("phones", phones);

        Person person = ObjectBindingUtil.toObject(Person.class, map);

        assertEquals(2, person.getPhones().size());

        assertEquals(86, person.getPhones().get(0).getCountry());
        assertEquals(29, person.getPhones().get(0).getProvince());

        assertEquals(64, person.getPhones().get(1).getCountry());
        assertEquals(44, person.getPhones().get(1).getProvince());
    }
}
