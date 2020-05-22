package com.rul.gather8002;

import org.springframework.web.bind.annotation.RequestMapping;
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

}
