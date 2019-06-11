package com.example.zebra.multisource.demo.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-09 17:35
 */
@Setter
@Getter
public class OrderEntity {

    private Integer id;
    private String orderDetail;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", orderDetail='" + orderDetail + '\'' +
                '}';
    }
}