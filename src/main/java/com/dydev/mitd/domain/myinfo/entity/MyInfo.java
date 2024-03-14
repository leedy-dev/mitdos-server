package com.dydev.mitd.domain.myinfo.entity;

import com.dydev.mitd.common.base.entity.BaseCUEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @Size(min = 1, max = 200)
    @Column
    private String nameKor;

    @NotNull
    @Size(min = 1, max = 200)
    @Column
    private String nameEng;

    @NotNull
    @Size(min = 1, max = 200)
    @Column
    private String phoneNum;

    @NotNull
    @Size(min = 1, max = 200)
    @Column
    private String email;

    @NotNull
    @Size(min = 1, max = 200)
    @Column
    private String notice;

    @NotNull
    @Size(min = 1, max = 40000)
    @Column
    private String introduction;

}
