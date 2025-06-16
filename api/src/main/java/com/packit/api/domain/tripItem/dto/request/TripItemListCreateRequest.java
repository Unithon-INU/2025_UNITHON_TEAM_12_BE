package com.packit.api.domain.tripItem.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Builder
public class TripItemListCreateRequest {

    @Schema(description = "추가할 아이템 리스트", required = true)
    private List<TripItemCreateRequest> items;
}