package com.thoughtworks.orm.listeners;

import com.thoughtworks.orm.core.DB;
import org.dom4j.DocumentException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabaseInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DB.init();
        } catch (DocumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
