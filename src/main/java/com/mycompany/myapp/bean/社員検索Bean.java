package com.mycompany.myapp.bean;

import lombok.Data;

@Data
public class 社員検索Bean {
	String s_ID;
	String 番号; //ID，CD 都无法自动转，不知道为啥。错误信息=Jackson with JSON: Unrecognized field, not marked as ignorable
	String 姓名;
	String 性別;
	String 生年月日開始;
	String 生年月日終了;
	String 入社年月日開始;
	String 入社年月日終了;
	String 契約種類;
	String 削除年月日;
	String 電話番号;
}
