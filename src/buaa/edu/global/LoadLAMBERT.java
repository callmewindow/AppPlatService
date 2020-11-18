package buaa.edu.global;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by dell on 15-1-23.
 */
public class LoadLAMBERT implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        OrbitTransfer.getInstance().init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        OrbitTransfer.getInstance().free();
    }
}
