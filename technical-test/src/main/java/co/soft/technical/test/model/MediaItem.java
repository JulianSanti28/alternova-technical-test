package co.soft.technical.test.model;

import co.soft.technical.test.model.enums.MediaItemTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "media_item")
@AllArgsConstructor
@NoArgsConstructor
public class MediaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String genre;
    @Enumerated(EnumType.STRING)
    private MediaItemTypeEnum type;
    private int views;
    private double rating;
    @OneToMany(mappedBy = "mediaItem", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
