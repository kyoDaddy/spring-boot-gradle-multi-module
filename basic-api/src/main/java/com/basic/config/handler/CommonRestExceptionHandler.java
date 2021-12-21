package com.basic.config.handler;

import com.basic.process.models.response.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
// @Order 는 가장 낮은 숫자가 실행 순서가 가장 빠름 (HIGHEST_PRECEDENCE : -2147483648)
@Order(Ordered.HIGHEST_PRECEDENCE)
// @ResponseBody + @ControllerAdvice (@ExceptionHandler, @ModelAttribute, @InitBinder 가 적용된 메서드들을 AOP 적용 및 컨트롤러에 적용하기 위해 만들어진 애너테이션)
@RestControllerAdvice
public class CommonRestExceptionHandler {

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public CommonResult handleExceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {

		log.error("defaultExceptionHandler", e);
		CommonResult result = new CommonResult();
		result.setSuccess(false);
		result.setCode(9999);
		result.setMsg(e.getMessage());
		return result;

	}

}
