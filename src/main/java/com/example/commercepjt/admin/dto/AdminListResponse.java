package com.example.commercepjt.admin.dto;

import com.example.commercepjt.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdminListResponse {

    private List<AdminResponse> admins;
    private PageInfo pageInfo;
}
