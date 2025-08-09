package net.lotte.chamomile.admin.authgroup.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthGroupQuery {
    private String groupId;
    private String showEmptyTreeYn;
}
