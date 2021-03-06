package com.rul.gather8002;

import com.rul.gather8002.clientprocess.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测评程序接口
 *
 * @author rul
 */
@RestController
public class CommonController {

    /**
     * 状态接口
     *
     * @return success
     */
    @RequestMapping("/ready")
    public String ready() {
        return "success";
    }

    /**
     * 接收数据源端口的接口
     *
     * @param port 数据源端口
     * @return success
     */
    @RequestMapping("/setParameter")
    public String setParamter(@RequestParam Integer port) {
        //设置数据源
        Data.dataPort = port;
        return "success";
    }

}
