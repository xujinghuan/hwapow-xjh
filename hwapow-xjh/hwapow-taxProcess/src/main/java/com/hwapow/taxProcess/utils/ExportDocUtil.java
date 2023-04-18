package com.hwapow.taxProcess.utils;

import com.hwapow.common.config.HwapowConfig;
import com.hwapow.common.core.domain.AjaxResult;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.taxProcess.domain.*;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExportDocUtil {
    /**
     * 导出分析报告
     *
     * @param taxBase  基础数据
     *
     * **/
    public AjaxResult exportFxbg(TaxBase taxBase)throws Exception{
        String path = this.getClass().getResource("/template/").getPath() ;
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File(path));


        Map<String, Object> dataMap = new HashMap<String, Object>();
        if(StringUtils.isBlank(taxBase.getColO())){
            taxBase.setColO(taxBase.getColM());
        }
        if(StringUtils.isBlank(taxBase.getColP())){
            taxBase.setColP(taxBase.getColN());
        }

        dataMap.put("taxBase",taxBase);
        Template t = configuration.getTemplate("zjb.xml", "utf-8");

        String zjbName = taxBase.getColB();
        String zjbId = taxBase.getColA();
        String fileName = zjbName+ "-" + zjbId + "-纳税人缴费人税费诉求征集表";
        fileName= deleteSpecial(fileName)+".doc";

        File outFile = new File(getAbsoluteFile(fileName));

        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
        t.process(dataMap, out);
        out.close();
        taxBase.setDown("1");
        return AjaxResult.success(fileName);//fileName
    }


    private String deleteSpecial(String fileName){
        Pattern p = Pattern.compile("[\\\\/:\\*?\"<>|\\+&'!#%;$^_.]");
        Matcher m = p.matcher(fileName);
        return m.replaceAll("");
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename) {
        String downloadPath = HwapowConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }
}
