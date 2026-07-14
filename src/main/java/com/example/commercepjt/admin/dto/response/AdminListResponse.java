package com.example.commercepjt.admin.dto.response;

import com.example.commercepjt.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdminListResponse {

    private final List<AdminResponse> admins;
    private final PageInfo pageInfo;
}
