package com.rodrigobaraglia.urly.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RankingDTO {
    private List<String> mostVisited = new ArrayList<>();
   public RankingDTO(List<String> ranking) {
        this.mostVisited = ranking;
    }

}
