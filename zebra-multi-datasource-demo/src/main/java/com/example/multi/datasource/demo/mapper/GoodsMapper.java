package com.example.multi.datasource.demo.mapper;

import com.dianping.zebra.dao.datasource.ZebraRouting;
import com.example.multi.datasource.demo.entities.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-09 17:34
 */
@ZebraRouting("goods")
@Mapper
public interface GoodsMapper {

    GoodsEntity findGoodsInfoById(int id);

}