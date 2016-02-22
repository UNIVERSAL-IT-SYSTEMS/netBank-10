package logging;

import java.util.logging.Level;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import exceptions.LoggerExeption;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Szabo
 */
public class LoggingInterceptor {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(Logger.class.getName());

    @AroundInvoke
    public Object loggingMethod(InvocationContext ic) {
        LOG.entering(ic.getTarget().getClass().getName(), ic.getMethod().getName());
        LOG.log(Level.INFO, "====== Loggable entering: {0} class {1} method ======", new Object[]{ic.getTarget().getClass().getName(), ic.getMethod().getName()});
        try {
            return ic.proceed();
        } catch (Exception e) {
            throw new LoggerExeption(e);
        } finally {
            LOG.exiting(ic.getTarget().getClass().getName(), ic.getMethod().getName());
            LOG.log(Level.INFO, "====== Loggable exiting: {0} class {1} method ======", new Object[]{ic.getTarget().getClass().getName(), ic.getMethod().getName()});
        }
    }
}
