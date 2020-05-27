package com.rul.filter8001;

import com.rul.filter8001.clientprocess.PullData;
import com.rul.filter8001.common.DataSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测评程序接口
 * @author RuL
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
        //开启线程拉取数据
        Thread thread = new Thread(PullData::pullData,"pull data thread");
        thread.start();
        return "success";
    }
}
