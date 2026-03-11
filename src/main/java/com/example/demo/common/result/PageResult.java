package com.example.demo.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> {
    private List<T> records;
    private Long total;
    private Long page;
    private Long size;

    public PageResult(List<T> records, Long total, Long page, Long size) {
        this.records = records;
        this.total = total;
        this.page = page;
        this.size = size;
    }
}
