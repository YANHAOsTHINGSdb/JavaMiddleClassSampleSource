package com.mycompany.myapp.bean;

import lombok.Data;

@Data

public class 社員Bean {
	String 番号;
	String 姓名;
	String 性別;
	String 生年月日;
	String 入社年月日;
	String 契約種類;

	String old_番号;
	String old_姓名;
	String old_性別;
	String old_生年月日;
	String old_入社年月日;
	String old_契約種類;
}
