package bienew.board.article.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "tag")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    private Long tagId;

    @Column(unique = true, nullable = false)
    private String tagName;

    private Long count = 0L;

    public static Tag create(Long id, String tagName) {
        Tag tag = new Tag();

        tag.tagId = id;
        tag.tagName = tagName;

        return tag;
    }

    public void increase() {
        count += 1;
    }

    public void decrease() {
        count -= 1;
    }

    public void update(String tagName) {
        this.tagName = tagName;
    }
}
