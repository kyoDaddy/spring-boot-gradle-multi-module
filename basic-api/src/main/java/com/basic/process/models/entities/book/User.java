package com.basic.process.models.entities.book;

import com.basic.process.models.entities.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "user") // 테이블명, 인덱스 등에 관한 설정
@DynamicUpdate // 엔티티 update 할 때, 변경된 컬럼정보만을 이용하여 동적 쿼리를 생성합니다.
@DynamicInsert // insert 되기 전에 엔티티에 설정된 컬럼 정보 중 null이 아닌 컬럼만을 이용하여 동적 insert 쿼리를 생성합니다.
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -563329217866858622L;

    @Id // PK 키임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1)")
    private Long userSeq;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String username;

    @Builder
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public Object getSimple() {
        return null;
    }

}
