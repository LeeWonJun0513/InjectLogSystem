package assistlog;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;


abstract class ServletTransform implements Transform {

    protected abstract String[] methodsToTrace();

    public boolean appliesTo(String className) {
        // Matching on StandardEngineValve to try to only hook in for top-level
        // requests (e.g. avoiding resetting the counter when one servlet
        // forwards to another)
        return true;
    }
    public byte[] instrument(String className, byte[] defaultBytes, ClassLoader loader) {
        byte[] result = defaultBytes;

        try {
            ClassPool cp = ClassPool.getDefault();
            cp.appendClassPath(new javassist.LoaderClassPath(loader));
            CtClass servletRequest = cp.get(className);

            for (String methodName : methodsToTrace()) {
                CtMethod invokeMethod = servletRequest.getDeclaredMethod(methodName);
//                invokeMethod.insertBefore("{net.dishevelled.sqlcounter.SQLQueryCounter.clearCount();}");
                invokeMethod.insertAfter("{" +
                                         "if (net.dishevelled.sqlcounter.SQLQueryCounter.getCount() > 0) {" +
                                         "System.err.println(Thread.currentThread() + \" [TOTAL]> \" + " +
                                         "request.getRequestURI() + " +
                                         "((request.getQueryString() == null) ? \"\" : (\"?\" + request.getQueryString())) " +
                                         " + \" - SQL queries executed: \" + net.dishevelled.sqlcounter.SQLQueryCounter.getCount());};}");
            }

            result = servletRequest.toBytecode();
            servletRequest.detach();

            System.err.println("\n\n*** Instrumented " + className + "\n\n");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
