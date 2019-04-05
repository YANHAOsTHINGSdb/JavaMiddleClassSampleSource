package com.mycompany.myapp.bean;

import java.util.List;

import lombok.Data;

@Data
public class 技術者Bean {

	String 技術者ID;           // 唯一标示
	List<String> 技術者_技術D;  // 对应技术信息，一名技术者通常会有多项技能，属于1对N的关系
	List<String> 経験_案件ID;   // 对应案件信息，一名技术者通常会有多项案件経験，属于1对N的关系
	String 技術者_社員CD;      // 对应社員信息

	String 姓名;         // 技术者简历表示名，一般是简写
	String 性别;         // 与社員信息重复
	String 出生年月日;    // 与社員信息重复
	String 毕业院校;
	String 最终学历;
	String 专业技能;
}
