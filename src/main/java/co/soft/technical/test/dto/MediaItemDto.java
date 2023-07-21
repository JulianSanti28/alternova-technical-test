package co.soft.technical.test.dto;


import co.soft.technical.test.model.enums.MediaItemTypeEnum;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaItemDto {
    private Long id;
    @NotNull(message = "{mediaItem.name.not.null}")
    @NotEmpty(message = "{mediaItem.name.not.empty}")
    private String name;
    @NotNull(message = "{mediaItem.genre.not.null}")
    @NotEmpty(message = "{mediaItem.genre.not.empty}")
    private String genre;
    @NotNull(message = "{mediaItem.type.not.null}")
    @Enumerated(EnumType.STRING)
    private MediaItemTypeEnum type;
    private int views;
    private double rating;
}
