package com.imooc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {

    final static Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * AOP 方法形式：
     * 1. 前置 before
     * 2. 后置 after
     * 3. 环绕 around
     * 4. 异常
     * 5. 最终 finally
     */

    /**
     * 切面表达式： execution 代表所要执行的表达式主体
     * 1. * 代表方法返回类型 * -- 代表所有类型
     * 2. 所在包
     * 3. .. 代表该包和子包下的所有类方法
     * 4. 类名 *代表所有类
     * 5. *(..) *代表类中的方法名是什么，(..)代表参数
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.imooc.service.impl..*.*(..) )")
    public Object recordTimeLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        /**
         * {类名}.{方法名}
         */
        logger.info("====== 开始执行 {}.{} ======",
                proceedingJoinPoint.getTarget().getClass(),
                proceedingJoinPoint.getSignature().getName());
        // 记录开始时间戳
        long beginTime = System.currentTimeMillis();

        // 执行目标service
        Object result = proceedingJoinPoint.proceed();

        // 记录结束时间
        long endTime = System.currentTimeMillis();

        long takeTime = endTime - beginTime;
        if (takeTime > 3000){
            logger.error(" === 执行结束，耗时：{} 毫秒 ===", takeTime);
        }else if (takeTime > 2000){
            logger.warn(" === 执行结束，耗时：{} 毫秒 ===", takeTime);
        }else {
            logger.info(" === 执行结束，耗时：{} 毫秒 ===", takeTime);
        }
        return result;
    }

}
