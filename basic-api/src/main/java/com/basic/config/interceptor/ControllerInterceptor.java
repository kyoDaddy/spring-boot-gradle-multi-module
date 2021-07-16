package com.basic.config.interceptor;

import com.basic.utils.common.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ControllerInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String startTime = DateUtils.getDate("HH:mm:ss.SSS");
        StringBuffer log = new StringBuffer();

        log.append("\r\n");
        log.append("===================================================================\r\n");
        log.append("Request Time=" + startTime + "\r\n");
        log.append("Request URL=" + request.getRequestURL().toString() + "\r\n");
        log.append("Request IP=" + request.getRemoteAddr() + "\r\n");
        log.append("Request Method=" + request.getMethod() + "\r\n");
        log.append("Request Encoding=" + request.getCharacterEncoding() + "\r\n");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute("startTime", startTime.replaceAll("[:]", "").replaceAll("[.]", ""));

        try {
            Map<String, String> parameterMap = new HashMap<String, String>();

            /*
            log.append("\r\n");
            Enumeration headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements()) {
                String key = (String)headerNames.nextElement();
                log.append( key + ":" + request.getHeader(key) + "\r\n" );
            }
            */

            request.setAttribute("parametersMap", parameterMap);
            request.setAttribute("logSb", log);
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StringBuffer log = (StringBuffer)request.getAttribute("logSb");
        String startTime = (String)request.getAttribute("startTime");
        String endTime = DateUtils.getDate("HH:mm:ss.SSS");

        log.append("\r\n");
        log.append("Response Time=" + endTime + "\r\n");
        log.append("Elapsed Time=" + (Long.valueOf(endTime.replaceAll("[:]", "").replaceAll("[.]", ""))-Long.valueOf(startTime)) + "ms\r\n");
        log.append("===================================================================\r\n");

        logger.info(log.toString());
    }


}
