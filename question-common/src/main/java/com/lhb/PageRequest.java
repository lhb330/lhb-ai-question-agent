package com.lhb;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页号
     */
    private int pageNum = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = "desc";


    public Page<T> getPage() {
        return new Page<>(this.pageNum,this.pageSize);
    }
}
