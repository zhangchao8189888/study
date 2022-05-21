package com.zhangchao.springbootDemo;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.zhangchao.springbootDemo.service.impl.ExpressServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class SpringbootDemoApplicationTests {

	@Test
	void contextLoads() {
		runExpress();
	}
	public void runExpress() {
		AviatorEvaluator.addFunction(new EqualsFunction());
		AviatorEvaluator.addFunction(new BelongToFunction());
		String expression = "equals(leftEqual,rightEqual)||belongTo(leftDepart,rightDepart)||leftValue==rightValue";
		Expression compiledExp = AviatorEvaluator.compile(expression, true);
		Map<String, Object> env = new HashMap<>();
		env.put("leftEqual", "2300");
		env.put("rightEqual", "1300");
		env.put("leftDepart", "2300");
		List<Long> departmentIds = new ArrayList<>();
		departmentIds.add(23L);
		departmentIds.add(22L);
		departmentIds.add(21L);
		env.put("rightDepart", departmentIds);
		env.put("leftValue", "21");
		env.put("rightValue", "23");
		Boolean result = (boolean) compiledExp.execute(env);
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
			log.info(left);
			log.info(right);
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
			Object rightValue = arg2.getValue(env);
			if (rightValue == null) {
				throw new NullPointerException("null seq");
			}
			Class<?> clazz = rightValue.getClass();
			if (List.class.isAssignableFrom(clazz)) {
				List<?> list = (List<?>) rightValue;
				String left = FunctionUtils.getStringValue(arg1, env);
				return AviatorBoolean.valueOf(list.contains(left));
			}
			return AviatorBoolean.valueOf(false);
		}

		public String getName() {
			return "belongTo";
		}
	}

}
