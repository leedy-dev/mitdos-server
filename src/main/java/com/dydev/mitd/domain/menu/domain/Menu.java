package com.dydev.mitd.domain.menu.domain;

import com.dydev.mitd.common.base.entity.BaseCUEntity;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(
        name = "tb_menu"
)
public class Menu extends BaseCUEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private final Long menuId = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_menu_id", foreignKey = @ForeignKey(name = "fk_menu"))
    private Menu upperMenu;

    @OrderBy("index asc")
    @Builder.Default
    @OneToMany(mappedBy = "upperMenu")
    private List<Menu> subMenus = new ArrayList<>();

    @NotNull
    @Size(min = 1, max = 30)
    @Column
    private String menuName;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length = 30)
    private RoleTypes auth;

    @Size(max = 100)
    @Column
    private String url;

    @Builder.Default
    @Column(columnDefinition = "integer default 1")
    private Integer level = 1;

    @Builder.Default
    @Column(columnDefinition = "integer default 1")
    private Integer index = 1;

    @Size(max = 30)
    @Column
    private String icon;

    public void applyUpperMenu(Menu upperMenu) {
        this.upperMenu = upperMenu;
    }

    public void applySubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

}
