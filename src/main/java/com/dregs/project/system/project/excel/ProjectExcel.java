package com.dregs.project.system.project.excel;

import com.dregs.common.utils.StringUtils;
import com.dregs.project.system.project.domain.StaCarProject;
import com.dregs.project.system.project.domain.StaProject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ProjectExcel {

    public static void export(HttpServletResponse response, Map<String, List<?>> data) {
        //声明输出流
        OutputStream os = null;
        //设置响应头
        setResponseHeader(response, "数据.xlsx");
        //获取输出流
        try {
            os = response.getOutputStream();
            //内存中保留1000条数据，以免内存溢出，其余写入硬盘
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);
            //获取该工作区的第一个sheet
            AtomicInteger index = new AtomicInteger();
            data.forEach((k, dataList) -> {
                Sheet sheet1 = wb.createSheet(k);
                if (index.get() == 0) {
                    int excelRow = 0;
                    //创建标题行
                    Row titleRow = sheet1.createRow(excelRow++);
                    setTitle(titleRow);
                    if (dataList != null && dataList.size() > 0) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Object d = dataList.get(i);
                            if (d instanceof StaProject) {
                                StaProject staProject = (StaProject) d;
                                Row dataRow = sheet1.createRow(excelRow++);
                                Cell cell = dataRow.createCell(0);
                                cell.setCellValue(staProject.getName());
                                cell = dataRow.createCell(1);
                                cell.setCellValue(staProject.getPushCarTotalMoney());
                                cell = dataRow.createCell(2);
                                cell.setCellValue(staProject.getPushSlaTotalMoney());
                                cell = dataRow.createCell(3);
                                cell.setCellValue(staProject.getPullMoney());
                                cell = dataRow.createCell(4);
                                cell.setCellValue(staProject.getPushSlaTotalMoney());
                                cell = dataRow.createCell(5);
                                cell.setCellValue(staProject.getCarTotal());
                                cell = dataRow.createCell(6);
                                cell.setCellValue(staProject.getSlaTotal());
                            }
                        }
                    }
                    index.getAndIncrement();
                }else {
                    if (dataList != null && dataList.size() > 0) {
                        //创建标题行
                        Object d = dataList.get(0);
                        if (d instanceof StaCarProject){
                            StaCarProject staCarProject = (StaCarProject)d;
                            Row tr = sheet1.createRow(0);
                            setCarTitle(tr,staCarProject.getCarNum(),sheet1);
                            Row tr2 = sheet1.createRow(1);
                            setCarTitle2(tr2);
                            Row titleRow = sheet1.createRow(2);
                            Cell cell = titleRow.createCell(0);
                            cell.setCellValue(isNull(staCarProject.getProjectName()));
                            cell = titleRow.createCell(1);
                            cell.setCellValue(isNull(staCarProject.getSlaName()));
                            cell = titleRow.createCell(2);
                            cell.setCellValue(isNull(staCarProject.getCarNum()));
                            cell = titleRow.createCell(3);
                            cell.setCellValue(staCarProject.getTransportNum()==null?0:staCarProject.getTransportNum());
                            cell = titleRow.createCell(4);
                            cell.setCellValue(isNullZero(staCarProject.getPushCarMoney()));
                            cell = titleRow.createCell(5);
                            cell.setCellValue(isNullZero(staCarProject.getTotalMoney()));
                            cell = titleRow.createCell(6);
                            cell.setCellValue(isNullZero(staCarProject.getPayMoney()));
                        }
                    }
                }
            });

            //将整理好的excel数据写入流中
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String isNull(String value){
        if (StringUtils.isNotEmpty(value)){
            return value;
        }else{
            return "";
        }
    }
    private static String isNullZero(String value){
        if (StringUtils.isNotEmpty(value)){
            return value;
        }else{
            return "0";
        }
    }

    private static void setCarTitle( Row titleRow,String carNum,Sheet sheet){
        Cell cell = titleRow.createCell(0);
        cell.setCellValue("车牌号"+carNum);
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 1);
        sheet.addMergedRegion(region);
    }
    private static void setCarTitle2( Row titleRow){
        Cell cell = titleRow.createCell(0);
        cell.setCellValue("项目");
        cell = titleRow.createCell(1);
        cell.setCellValue("渣场");
        cell = titleRow.createCell(2);
        cell.setCellValue("车牌");
        cell = titleRow.createCell(3);
        cell.setCellValue("票数");
        cell = titleRow.createCell(4);
        cell.setCellValue("单价");
        cell = titleRow.createCell(5);
        cell.setCellValue("应付");
        cell = titleRow.createCell(6);
        cell.setCellValue("已付");
    }

    private static void setTitle( Row titleRow){
        //创建该行下的每一列，并写入标题数据
        Cell cell = titleRow.createCell(0);
        cell.setCellValue("项目名称");
        cell = titleRow.createCell(1);
        cell.setCellValue("车运应付");
        cell = titleRow.createCell(2);
        cell.setCellValue("渣土应付");
        cell = titleRow.createCell(3);
        cell.setCellValue("利润");
        cell = titleRow.createCell(4);
        cell.setCellValue("发票数量");
        cell = titleRow.createCell(5);
        cell.setCellValue("车运数量");
        cell = titleRow.createCell(6);
        cell.setCellValue("渣场数量");
    }

    /*
    设置浏览器下载响应头
 */
    private static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
