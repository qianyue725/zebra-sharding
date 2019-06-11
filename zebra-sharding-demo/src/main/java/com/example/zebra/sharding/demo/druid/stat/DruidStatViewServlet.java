package com.example.zebra.sharding.demo.druid.stat;

import com.alibaba.druid.support.http.StatViewServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
/**
 * StatViewServlet用于展示Druid的统计信息。
 * 提供监控信息展示的html页面
 * 提供监控信息的JSON API
 *
 * 内置监控页面的首页是/druidstat/index.html
 *
 */
/**
 * @Webservlet
 * 有两个属性可以用来表示Servlet的访问路径，分别是value和urlPatterns。value和urlPatterns都是数组形式，
 * 表示我们可以把一个Servlet映射到多个访问路径，但是value和urlPatterns不能同时使用。
 *
 */
@WebServlet(
        urlPatterns = {"/druid/*"},
        initParams = {
                @WebInitParam(name = "loginUsername", value = "admin"),
                @WebInitParam(name = "loginPassword", value = "admin"),
                @WebInitParam(name = "resetEnable", value = "true")
//      @WebInitParam(name = "allow", value = "127.0.0.1")
    }
)
public class DruidStatViewServlet extends StatViewServlet {
    /**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = -7299455559901217248L;
    
}