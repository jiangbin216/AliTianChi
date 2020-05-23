package com.rul.filter8001;

import com.rul.filter8001.pojo.DataSource;
import com.rul.filter8001.service.DealData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测评程序接口
 * @author rul
 */
@RestController
public class CommonController {

    /**
     * 状态接口
     * @return success
     */
    @RequestMapping("/ready")
    public String ready(){

        return "success";
    }

    /**
     * 接收数据源端口的接口
     * @param port 数据源端口
     * @return success
     */
    @RequestMapping("/setParameter")
    public String setParamter(@RequestParam Integer port){
        //设置数据源
        DataSource.setDataPort(port);
        Thread thread = new Thread(DealData::pullData);
        thread.start();
        return "success";
    }
}
