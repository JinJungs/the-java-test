package me.whiteship.section0;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;


public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private final long THRESHOLD;

    public FindSlowTestExtension(long threshold) {
        this.THRESHOLD = threshold;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        /* 어노테이션 정보 가져오기 */
        Method requireTestMethod = context.getRequiredTestMethod();
        SlowTest annotation =   requireTestMethod.getAnnotation(SlowTest.class);

        String testMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = getStore(context);
        long startTime = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - startTime;
        if(duration > THRESHOLD && annotation == null) {
            System.out.printf("please consider mark method [%s] with @slowTest. \n", testMethodName);
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }
}
