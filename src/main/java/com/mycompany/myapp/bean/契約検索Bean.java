package com.mycompany.myapp.bean;

import lombok.Data;

@Data
public class 契約検索Bean {

	/** 参考式样书的数据结构：
	 *
		契约信息
		    |----------	契约ID
		    |----------	单价
		    |----------	开始日期
		    |----------	契约期
		    |----------	契约种别
		    |----------	结算币种
		    |----------	契约实际终了日
		    |----------	契约CD
		    |----------	含交通费
		    |----------	备考说明
		    |----------	甲方契约者ID
		    |----------	乙方契约者ID
	 */

	/**
	 * 注意：画面上有一个入力项目，
	 *      这里就有一个变量，
	 *      包括隐藏项。
	 */
	/**
	 * 注意，这是CLASS是
	 *      详细设计中的
	 *      项目定义书的
	 *      根据。
	 */
	String 契约ID; //唯一标识，无法替代，只做存根

	String 单价From;

	String 单价To;

	String 单价単位;

	String 结算币种;

	String 含交通费;

	String 开始日期From;

	String 开始日期To;

	String 契约期From;

	String 契约期To;

	String 契约期単位;

	String 契约实际终了日From;

	String 契约实际终了日To;

	String 契约种别;

	String 甲方契约者ID;

	String 乙方契约者ID;
}
