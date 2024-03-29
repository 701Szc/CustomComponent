package com.example.CustomComponentTest.module.recommand;

import com.example.CustomComponentTest.module.BaseModel;

import java.util.ArrayList;

/**
 * *******************************************************
 *
 * @文件名称：RecommandModel.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年7月9日 上午9:24:04
 * @文件描述：产品实体
 * @修改历史：2015年7月9日创建初始版本 ********************************************************
 */
public class RecommandModel extends BaseModel {
    /*
    * 分别对应我们json中的两个数据部分
    * */
    public ArrayList<RecommandBodyValue> list;
    public RecommandHeadValue head;

}
