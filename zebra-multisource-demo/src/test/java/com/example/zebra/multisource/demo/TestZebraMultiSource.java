package com.example.zebra.multisource.demo;

import com.example.zebra.multisource.demo.mapper.GoodsMapper;
import com.example.zebra.multisource.demo.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-10 21:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestZebraMultiSource {


    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testFindGoods() throws Exception {
        System.out.println(goodsMapper.findGoodsInfoById(1));
    }

    @Test
    public void testFindOrder() throws Exception {
        System.out.println(orderMapper.findOrderInfoById(1));
    }
}