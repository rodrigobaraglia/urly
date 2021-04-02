package com.rodrigobaraglia.urly.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Ranking {
    private List<String> mostVisited = new ArrayList<>();
   public Ranking(List<String> ranking) {
        this.mostVisited = ranking;
    }

}
