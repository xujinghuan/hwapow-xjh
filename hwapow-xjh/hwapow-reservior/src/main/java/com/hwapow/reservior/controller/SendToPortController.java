package com.hwapow.reservior.controller;

import com.hwapow.common.annotation.Log;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.controller.BaseController;
import com.hwapow.common.core.domain.AjaxResult;
import com.hwapow.common.core.page.TableDataInfo;
import com.hwapow.common.enums.BusinessType;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.reservior.domain.ResSenor;
import com.hwapow.reservior.service.IResSenorService;
import com.hwapow.rxtx.core.SerialPortUtil;
import com.hwapow.rxtx.service.IPortService;
import com.hwapow.webSocket.service.WebSocketServer;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 发送数据到设备
 *
 * @author hwapow
 * @date 2021-09-20
 */
@EnableAsync
@RestController
@RequestMapping("/reservior/sendToPort")
public class SendToPortController extends BaseController
{
    @Autowired
    private IResSenorService resSenorService;

    @Autowired
    private IPortService portService;

    /**
     * 发送取数指令到指定断面设备
     */
    @GetMapping("/section/{sectionId}")
    public AjaxResult sendToSection(@PathVariable("sectionId")Long sectionId)
    {
        ResSenor resSenor=new ResSenor();
        resSenor.setSectionId(sectionId);
        List<ResSenor> resSenors=resSenorService.selectResSenorList(resSenor);
        if(CollectionUtils.isEmpty(resSenors)){
            return AjaxResult.error("监测设备为空!");
        }
        SerialPortUtil serialPortUtil=portService.InitSerialPortUtil();
        for(ResSenor item:resSenors){
            portService.startRead(item,serialPortUtil);
        }
        return AjaxResult.success("监测数据已发出，请等待设备回传数据！");
    }

    /**
     * 发送取数指令到所有设备
     */
    @GetMapping("/all")
    public AjaxResult sendToAll()
    {
        List<ResSenor> resSenors=resSenorService.selectResSenorList(null);
        if(CollectionUtils.isEmpty(resSenors)){
            return AjaxResult.error("监测设备为空!");
        }
        SerialPortUtil serialPortUtil=portService.InitSerialPortUtil();
        for(ResSenor item:resSenors){
            portService.startRead(item,serialPortUtil);
        }
        return AjaxResult.success("监测数据已发出，请等待设备回传数据！");
    }

    /**
     * 发送取数指令到指定设备
     */
    @GetMapping("/senor/{senorId}")
    public AjaxResult sendToSenor(@PathVariable("senorId")Long senorId)
    {
        ResSenor resSenor=resSenorService.selectResSenorById(senorId);
        if(resSenor==null){
            return AjaxResult.error("监测设备为空!");
        }
        SerialPortUtil serialPortUtil=portService.InitSerialPortUtil();
        portService.startRead(resSenor,serialPortUtil);
        return AjaxResult.success("监测数据已发出，请等待设备回传数据！");
    }

}
