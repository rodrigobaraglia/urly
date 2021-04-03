package com.rodrigobaraglia.urly.model;
import com.rodrigobaraglia.urly.utils.Base62;
import lombok.*;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    private String shortUrl;
    private String longUrl;

  public   Url(String url) {
        this.shortUrl = new Base62().encode(Math.abs(UUID.randomUUID().hashCode()));
        this.longUrl = url;
    }

}
