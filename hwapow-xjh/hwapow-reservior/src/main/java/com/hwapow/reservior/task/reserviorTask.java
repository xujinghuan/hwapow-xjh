package com.hwapow.reservior.task;


import com.hwapow.common.utils.spring.SpringUtils;
import com.hwapow.reservior.domain.ResSenor;
import com.hwapow.reservior.mapper.ResSenorMapper;
import com.hwapow.reservior.service.IResSenorService;
import com.hwapow.rxtx.core.SerialPortUtil;
import com.hwapow.rxtx.service.IPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class reserviorTask {

    private ResSenorMapper resSenorMapper;;

    private IPortService portService;

    private static Thread thread;

    public reserviorTask() {
        resSenorMapper = SpringUtils.getBean(ResSenorMapper.class);
        portService = SpringUtils.getBean(IPortService.class);
    }

    public void getSenorData()
    {
        List<ResSenor> resSenors=resSenorMapper.selectResSenorList(new ResSenor());
        if(CollectionUtils.isEmpty(resSenors)){
            return ;
        }
        SerialPortUtil serialPortUtil=portService.InitSerialPortUtil();
        if(thread!=null&&thread.isAlive()){
            thread.interrupt();//强制中断是为了不重复执行
        }
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for(ResSenor item:resSenors){
                    if(Thread.currentThread().isInterrupted()){
                        //处理中断逻辑
                        break;
                    }
                    portService.startRead(item,serialPortUtil);
                }
            }
        });
        thread.start();
    }
}
