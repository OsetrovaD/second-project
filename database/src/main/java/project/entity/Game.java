package project.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.entity.baseentity.BaseEntity;
import project.entity.enumonly.AgeLimit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"comments", "subgenres", "gamePrices", "screenshots"})
@Builder
@EqualsAndHashCode(callSuper = true,
        exclude = {
        "name",
        "description",
        "issueYear",
        "image",
        "minimalSystemRequirements",
        "recommendedSystemRequirements",
        "ageLimit",
        "comments",
        "developerCompany",
        "screenshots",
        "gamePrices",
        "subgenres",
        })
@Entity
@Table(name = "game", schema = "computer_games_e_shop_storage")
public class Game extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "issue_year")
    private Integer issueYear;

    @Column(name = "image_url")
    private String image;

    @Column(name = "minimal_system_requirements")
    private String minimalSystemRequirements;

    @Column(name = "recommended_system_requirements")
    private String recommendedSystemRequirements;

    @Column(name = "age_limit")
    @Enumerated(EnumType.STRING)
    private AgeLimit ageLimit;

    @OneToMany(mappedBy = "game")
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private DeveloperCompany developerCompany;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<Screenshot> screenshots = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<GamePrice> gamePrices = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "game_subgenre", schema = "computer_games_e_shop_storage",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "subgenre_id"))
    private Set<Subgenre> subgenres = new HashSet<>();

    public void addSubgenre(Subgenre subgenre) {
        this.subgenres.add(subgenre);
    }
}
