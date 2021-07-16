package com.basic.process.models.entities.test;

import com.basic.process.models.entities.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Entity
@Table(name = "product") // 테이블명, 인덱스 등에 관한 설정
@DynamicUpdate // 엔티티 update 할 때, 변경된 컬럼정보만을 이용하여 동적 쿼리를 생성합니다.
@DynamicInsert // insert 되기 전에 엔티티에 설정된 컬럼 정보 중 null이 아닌 컬럼만을 이용하여 동적 insert 쿼리를 생성합니다.
public class Product extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -563319217866858622L;

    @Id // PK 키임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1)")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Builder
    public Product(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Object getSimple() {
        return null;
    }

}
