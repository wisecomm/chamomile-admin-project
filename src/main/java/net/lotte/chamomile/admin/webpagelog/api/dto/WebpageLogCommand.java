package net.lotte.chamomile.admin.webpagelog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import net.lotte.chamomile.admin.webpagelog.domain.WebpageLogVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebpageLogCommand {
    private String url;

    public WebpageLogVO toEntity() {
        return WebpageLogVO.builder()
                .url(url)
                .build();
    }
}
