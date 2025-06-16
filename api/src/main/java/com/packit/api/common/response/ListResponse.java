package com.packit.api.common.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse <T> extends  CommonResponse{
    List<T> data;

    @Builder
    public ListResponse(int code, String message, List<T> data) {
        super(true, code, message);
        this.data = data;
    }
}