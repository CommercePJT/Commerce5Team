package com.example.commercepjt.admin.dto.response;

import com.example.commercepjt.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class UpdateStatusResponse {

    private AdminStatus status;

    public UpdateStatusResponse(AdminStatus status){
        this.status = status;
    }
}
