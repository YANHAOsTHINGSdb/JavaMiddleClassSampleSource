package com.mycompany.myapp.bean;

import lombok.Data;

@Data
public class 案件検索Bean {
	String s_ID;
//	String 名称;
//	String 概要;
//	String 場所;
//	String 時期終了;
//	String 時期開始;
//	String 最少人数;
//	String 最大人数;

	String 案件名称;
	String 案件概要;
	String 案件場所;
	String 職種;
	String 工程;
	String 案件開始日_開始;
	String 案件開始日_終了;
	String 予定終了日_開始;
	String 予定終了日_終了;
	String 実際終了日_開始;
	String 実際終了日_終了;
	String 人数_開始;
	String 人数_終了;
}