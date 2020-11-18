package buaa.edu.global;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by winter on 2014/8/25.
 */
public class CloudStoreThread implements ServletContextListener {
    private static CloudStore cloudStore = new CloudStore();
    private static Thread storeThread = new Thread(cloudStore);
    public static CloudStore getCloudStore() {
        return cloudStore;
    }
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        storeThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        storeThread.stop();
    }
}
