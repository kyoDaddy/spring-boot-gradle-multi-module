package com.basic.process.controllers.common;

import com.basic.constants.common.CommonUrlConstants;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(
            value = CommonUrlConstants.API_PRE + "/hello/{idx}",
            method = RequestMethod.GET
    )
    @ApiOperation(value = "안뇽~", notes = "첫 테스트 API")
    public ResponseEntity<String> hello(
            HttpServletRequest request,
            @PathVariable("idx") String idx,
            @RequestParam(value="name", required = false) String name
    ) {

        StringBuffer log = (StringBuffer) request.getAttribute("logSb");
        log.append(idx + "(" + name + ")\r\n");

        JsonObject jObj = new JsonObject();
        jObj.addProperty("id", idx);
        jObj.addProperty("name", name);

        return new ResponseEntity<>(jObj.toString(), HttpStatus.OK);
    }

}
