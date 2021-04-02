package com.rodrigobaraglia.urly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitCounter {
    private String id;
    private long visits;
}
