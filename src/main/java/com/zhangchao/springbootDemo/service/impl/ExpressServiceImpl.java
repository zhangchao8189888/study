package com.zhangchao.springbootDemo.service.impl;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.zhangchao.springbootDemo.service.ExpressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ExpressServiceImpl implements ExpressService {
    @Override
    public void runExpress() {
        AviatorEvaluator.addFunction(new EqualsFunction());
        AviatorEvaluator.addFunction(new BelongToFunction());
        String expression = "equals(leftEqual,rightEqual)&&belongTo(leftDepart,rightDepart)&&leftValue=rightValue";
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("leftEqual", "2300");
        env.put("rightEqual", "1300");
        Double result = (Double) compiledExp.execute(env);
        System.out.println(result);
    }

    static class MinFunction extends AbstractFunction {
        @Override public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2, env);
            return new AviatorBigInt(Math.min(left.doubleValue(), right.doubleValue()));
        }

        public String getName() {
            return "min";
        }
    }
    static class EqualsFunction extends AbstractFunction {
        @Override public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            String left = FunctionUtils.getStringValue(arg1, env);
            String right = FunctionUtils.getStringValue(arg2, env);

            return AviatorBoolean.valueOf(left.equals(right));
        }

        public String getName() {
            return "equals";
        }
    }
    static class BelongToFunction extends AbstractFunction {
        @Override public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            //resObj = hcmFlowServiceClient.isParentContainSub(params);
            log.info("调用查询是否属于子部门:{}","hcmFlowServiceClient.isParentContainSub");
            return AviatorBoolean.valueOf(true);
        }

        public String getName() {
            return "belongTo";
        }
    }
}
