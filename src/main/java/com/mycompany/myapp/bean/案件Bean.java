package com.mycompany.myapp.bean;

import lombok.Data;

@Data

public class 案件Bean {
	String s_ID;

//	String 名称;  // 案件名称　例。XXX银行案件
//	String 概要;  // 案件概要　例，新商品追加に従っう画面追加対応
//	String 場所;  // 案件場所　例，茅場町
//	String 時期;  // 案件時期　例，３ヶ月
//	String 人数;  // 案件人数　例，３人
//	String 职种;  // 案件职种　例，PGPT
//	String 工程;  // 案件工程　例，開発・IT・ST・保守
//	String 开始时间;  // 案件开始时间　例，20190501
//	String old_名称;
//	String old_概要;
//	String old_場所;
//	String old_時期;
//	String old_人数;
//	String old_职种;
//	String old_工程;
//	String old_开始时间;

	String 案件名称;
	String 案件概要;
	String 案件場所;
	String 職種;
	String 工程;
	String 案件開始日;
	String 予定終了日;
	String 実際終了日;
	String 人数;

	String old_案件名称;
	String old_案件概要;
	String old_案件場所;
	String old_職種;
	String old_工程;
	String old_案件開始日;
	String old_予定終了日;
	String old_実際終了日;
	String old_人数;




}