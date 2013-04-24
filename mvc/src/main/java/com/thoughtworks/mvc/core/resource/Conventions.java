package com.thoughtworks.mvc.core.resource;

import java.util.Arrays;
import java.util.List;

public class Conventions {

    private static List<Convention> conventions = Arrays.asList(
            Convention.Index,
            Convention.Add,
            Convention.Create,
            Convention.Show,
            Convention.Edit,
            Convention.Update,
            Convention.Destroy
    );

    public static List<Convention> all() {
        return conventions;
    }

}
