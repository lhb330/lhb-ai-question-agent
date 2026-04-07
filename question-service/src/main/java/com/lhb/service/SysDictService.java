package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.DictQueryDTO;
import com.lhb.entity.SysDict;
import com.lhb.enums.DictTypeEnum;
import com.lhb.vo.SysDictVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SysDictService extends IService<SysDict> {

    IPage<SysDictVO> pageList(DictQueryDTO dto);

    SysDict create(SysDict entity);

    SysDict update(SysDict entity);

    boolean deleteById(Long id);

    List<SysDict> getByDictType(DictTypeEnum dictTypeEnum);

    List<SysDict> getByDictType(String dictType);

    List<SysDict> getByDictType(List<String> dictTypeList);

    /**
     * 查询字典 => Map
     */
    Map<String, SysDict> getDictMap(DictTypeEnum dictTypeEnum);

    /**
     * 查询字典 => Map
     */
    Map<String, SysDict> getDictMap(List<String> dictType);




}
