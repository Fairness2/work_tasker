package ru.gb.worktaskmanager.managercore.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

/**
 * Справочник типов комментариев
 */
@Data
@Entity
@Table(name = "d_comment_type")
@NoArgsConstructor
@EqualsAndHashCode
public class CommentType {
    @Id
    @NaturalId
    @Column(name = "code")
    private String code;
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "type")
    private List<Comment> comments;

    public CommentType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    /*
    Я посмотрел реализации, если делать константой, то будут проблемы с коллекциями основанными на мапах.
    В итоге реализация как у ломбок довольно монструозная https://projectlombok.org/features/EqualsAndHashCode, но вроде как решает такие проблемы
    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentType that = (CommentType) o;
        return Objects.equals(code, that.code);
    }*/

    @Override
    public String toString() {
        return "{" +
                "\"code\": \"" + code + "\"," +
                "\"title\": \"" + title + "\"" +
                "}";
    }
}
