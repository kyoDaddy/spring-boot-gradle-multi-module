package com.basic.process.controllers.book;

import com.basic.constants.common.CommonUrlConstants;
import com.basic.process.models.entities.book.User;
import com.basic.process.models.vo.book.UserVo;
import com.basic.process.service.book.UserService;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(path = CommonUrlConstants.API_PRE + "/users")
@RestController
public class UserApi {

    private final UserService userService;

    /**
     * jpa test
     * @param request
     * @param userSeq
     * @return
     */
    @GetMapping("/{userSeq}")
    public ResponseEntity<String> selectUserById(
            HttpServletRequest request,
            @PathVariable("userSeq") long userSeq
    ) {

        StringBuffer log = (StringBuffer) request.getAttribute("logSb");
        log.append(userSeq + "\r\n");

        Optional<User> oUser = userService.selectById(userSeq);
        JsonObject jObj = new JsonObject();

        if(oUser.isPresent()) {
            jObj.addProperty("userSeq", oUser.get().getUsername());
        }
        else {
            jObj.addProperty("userSeq", 0);
        }

        return new ResponseEntity<>(jObj.toString(), HttpStatus.OK);
    }



    @PostMapping("/")
    public ResponseEntity<String> getUser(HttpServletRequest request) {
        StringBuffer log = (StringBuffer) request.getAttribute("logSb");

        UserVo reqVo = new UserVo();
        reqVo.setUserSeq("46");

        UserVo resVo = userService.getUser(reqVo);
        log.append(resVo.getUserSeq() + "\r\n");

        JsonObject jObj = new JsonObject();
        jObj.addProperty("userSeq", resVo.getUserSeq());

        return new ResponseEntity<>(jObj.toString(), HttpStatus.OK);
    }


}
