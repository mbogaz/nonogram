package com.codefirst.nonogram_fx.dto;

import lombok.Data;

import java.util.List;

@Data
public class NavigationBlock {
    List<NavigationCell> cells;
    int blockNavsCount;

    public NavigationBlock(List<NavigationCell> cells) {
        this.cells = cells;
        this.blockNavsCount = cells.size();
    }
}
