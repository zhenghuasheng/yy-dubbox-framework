package com.etong.sms.service.impl;

import com.etong.pt.utility.SmsHelper;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/1/20.
 */
public class MessageServerImplTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInit() throws Exception {

        SmsHelper smsHelper = new SmsHelper();
        smsHelper.sendMessageRequest("18874757525", "测试内容", "10001", null);
//        Workbook book=Workbook.getWorkbook(new File("d:/phone.xls"));
//        Sheet sheet=book.getSheet(0);
//        int rows= sheet.getRows();
//
//        List<String> phones =new ArrayList<String>();
//        List<String> contents =new ArrayList<String>();
//
//
//        for (int i=0;i<rows;i++){
//            Cell cell=sheet.getCell(1,i);
//            phones.add(cell.getContents());
//            contents.add("各位司机朋友：您好！由于银联系统升级，本周第六期刷卡千元大奬结束后，周活动暂停，百元月活动照旧进行，春节过后精彩继续。机不可失，时不再来！赶紧抓住春节前最后一次活动机会，刷卡夺千元，抱奖过大年。最新活动详情请见电召屏，并关注微信号“天下易达”或致电热线：0731-84115555");
//        }
//
//        smsHelper.sendMessageListRequest(phones,contents,null,"10001");


    }
}