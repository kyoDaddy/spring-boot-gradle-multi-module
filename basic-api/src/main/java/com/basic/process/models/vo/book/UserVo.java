package com.basic.process.models.vo.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVo {
    /** 사용자 SEQ */
    String userSeq;
    /** 사용자 이메일 */
    String email;
    /** 사용자 이름 */
    String username;
    /** 사용자 권한 유형코드 */
    String roleTypeCd;
    /** 사용자 비밀번호 */
    String password;
    /** 사용자 정보 가입일시 */
    String regDtt;
    /** 사용자 정보 수정일시 */
    String uptDtt;
    /** 사용자 정보 수정자 SEQ */
    String uptUserSeq;
    /** 사용자 비밀번호 초기화 랜덤 번호 */
    String passwordReset;
    /** 사용자 비밀번호 초기화 랜덤 번호 (유입)  */
    String randomNum;
}
