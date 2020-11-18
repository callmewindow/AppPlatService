package buaa.edu.global;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;

/**
 * Created by Administrator on 14-9-1.
 */
public class LoadZBZH_PLANETDll implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        OrbitCalculate.getInstance().ini();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        OrbitCalculate.getInstance().free();
    }
}
