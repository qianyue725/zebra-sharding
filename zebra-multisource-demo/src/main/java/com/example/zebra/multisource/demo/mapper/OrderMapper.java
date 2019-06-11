package com.example.zebra.multisource.demo.mapper;

import com.dianping.zebra.dao.datasource.ZebraRouting;
import com.example.zebra.multisource.demo.entities.OrderEntity;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-09 17:34
 */
@ZebraRouting("order")
public interface OrderMapper {

    OrderEntity findOrderInfoById(int id);

}