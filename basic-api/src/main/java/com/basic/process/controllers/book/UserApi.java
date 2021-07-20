package com.basic.process.controllers.book;

import com.basic.constants.book.BookUrlConstants;
import com.basic.constants.common.CommonUrlConstants;
import com.basic.process.models.entities.book.User;
import com.basic.process.models.response.CommonResult;
import com.basic.process.models.response.ListResult;
import com.basic.process.models.response.SingleResult;
import com.basic.process.models.vo.book.UserVo;
import com.basic.process.repository.book.UserRepository;
import com.basic.process.service.ResponseService;
import com.basic.process.service.book.UserService;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Api(tags = {"User"})
@RequiredArgsConstructor
@RequestMapping(path = CommonUrlConstants.API_PRE + CommonUrlConstants.API_VERSION_1 + "/book")
@RestController
public class UserApi {

    private final UserService userService;

    private final UserRepository userRepository;

    private final ResponseService responseService;  // 결과를 처리할 response


    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과 데이터가 여러건일 경우 getListResult를 이용해서 결과를 반환
        return responseService.getListResult(userService.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userSeq로 회원을 조회")
    @GetMapping(value = "/user/{userSeq}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원번호", required = true) @PathVariable long userSeq) {
        Optional<User> user = userService.findById(userSeq);
        if(user.isPresent()) {
            return responseService.getSingleResult(user.get());
        }
        else {
            return (SingleResult<User>) responseService.getFailResult(9999, "조회된 회원이 없습니다.");
        }
        //return responseService.getSingleResult(userService.findById(userSeq).orElseThrow(CUserNotFoundException::new));
    }

    @ApiOperation(value = "회원 등록", notes = "회원정보를 등록")
    @RequestMapping(value = "/user", method= {RequestMethod.POST})
    public SingleResult<User> save(
            @ApiParam(value = "name", required = true) @RequestParam String name,
            @ApiParam(value = "email", required = true) @RequestParam String email
    ) {

        User user = User.builder()
                        .username(name)
                        .email(email)
                        .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam Long userSeq,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name,
            @ApiParam(value = "회원이메일", required = true) @RequestParam String email
    ) {
        User user = User.builder()
                .userSeq(userSeq)
                .username(name)
                .email(email)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }


    @ApiOperation(value = "회원 삭제", notes = "회원정보를 삭제")
    @DeleteMapping(value = "/user/{userSeq}")
    public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable long userSeq) {
        Optional<User> user = userRepository.findById(userSeq);
        if(user.isPresent()) {
            userRepository.deleteById(userSeq);
            return responseService.getSuccessResult();
        }
        else {
            return responseService.getFailResult(9999, "조회된 회원이 없습니다.");
        }
    }


/*
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
*/


}
