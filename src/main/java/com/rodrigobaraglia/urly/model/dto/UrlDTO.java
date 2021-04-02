package com.rodrigobaraglia.urly.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlDTO {
    private String shortUrl;
    private String longUrl;
    private long visits;
}
