package com.dydev.mitd.domain.myinfo.entity;

import com.dydev.mitd.common.base.entity.BaseCUEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(
        name = "tb_my_info"
)
public class MyInfo extends BaseCUEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = 0L;

    @Column(nullable = false, length = 200)
    private String nameKor;

    @Column(nullable = false, length = 200)
    private String nameEng;

    @Column(nullable = false, length = 200)
    private String phoneNum;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(nullable = false, length = 200)
    private String notice;

    @Column(nullable = false, length = 40000)
    private String introduction;

}
