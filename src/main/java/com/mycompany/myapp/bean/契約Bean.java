package com.mycompany.myapp.bean;

import lombok.Data;

@Data
public class 契約Bean {

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
	String 契约ID;
	String 单价;
	String 单价単位;
	String 开始日期;
	String 契约期;
	String 契约期单位;
	String 契约种别;
	String 结算币种;
	String 契约实际终了日;
	String 契约CD;
	String 含交通费;
	String 备考说明;
	String 甲方契约者ID;
	String 乙方契约者ID;

	String old_单价;
	String old_单价単位;
	String old_开始日期;
	String old_契约期;
	String old_契约期单位;
	String old_契约种别;
	String old_结算币种;
	String old_契约实际终了日;
	String old_契约CD;
	String old_含交通费;
	String old_备考说明;
	String old_甲方契约者ID;
	String old_乙方契约者ID;

}
