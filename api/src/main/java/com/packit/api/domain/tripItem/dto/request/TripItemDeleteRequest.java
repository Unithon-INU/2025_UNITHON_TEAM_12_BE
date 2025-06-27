package com.packit.api.domain.tripItem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TripItemDeleteRequest {

    @Schema(description = "삭제할 아이템 ID 리스트", example = "[1, 2, 3]")
    @NotEmpty(message = "삭제할 아이템 ID 리스트는 비어있을 수 없습니다.")
    private List<Long> tripItemIds;

    private TripItemDeleteRequest(List<Long> tripItemIds) {
        this.tripItemIds = tripItemIds;
    }

    public static TripItemDeleteRequest of(List<Long> ids) {
        return new TripItemDeleteRequest(ids);
    }
}
