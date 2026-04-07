package com.lhb;

import com.lhb.enums.ErrorCode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> extends BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Long pageNum;

    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 当前页数据列表
     */
    private List<T> data;

    /**
     * 快速构建分页结果（MyBatis-Plus IPage 专用）
     */
    public static <T> PageResult<T> build(List<T> list, long total,long pages, long pageNum, long pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotal(total);
        result.setPages(pages);
        result.setData(list);
        result.setCode(ErrorCode.SUCCESS.getCode());
        result.setMsg(ErrorCode.SUCCESS.getMsg());
        return result;
    }

}
