package com.mycompany.myapp.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.ObjectUtils;

import com.mycompany.myapp.bean.契約Bean;
import com.mycompany.myapp.bean.契約検索Bean;
import com.mycompany.myapp.service.文件db;
import com.mycompany.myapp.service.親Service;

/**
 * 完成后对【契约】信息的增删改查处理
 *
 * by 张大宝，陈老师，朱老师，唐老师 2019-2-16
 */
public class 契約Service extends 親Service{

	/**
	 * fileName完全参照式样书的数据结构中的项目
	 */
	// "契约ID" 必须放在0号位，否则全件检索时会出问题
	String[] fileName = {
			"契约ID", "单价", "开始日期", "契约期", "契约期单位",
			"契约种别", "结算币种", "契约实际终了日",
			"契约CD", "含交通费", "备考说明", "单价単位",
			"甲方契约者ID", "乙方契约者ID" };

	/**
	 * 在Service中需要【文件db】提供服务
	 *
	 * 注：
	 * 文件db是公用的，已经写好了，不需要你去写了。
	 * 你所要做的工作就到此为止了。
	 */
	文件db file_db = new 文件db("契约");

	/**
	 * 完成检索任务
	 * 具体可以参考其它页面。
	 * @param 検索bean 画面的条件信息
	 * @return List<契约Bean>
	 */
	public List<契約Bean> 検索契約_by検索Bean(契約検索Bean 検索bean) {
		file_db.情報読み込み(fileName);

		Map<String,List<String>> 中間結果IDList = get中間結果_by検索契約Bean(検索bean);

		List<String> 最終結果IDList = get最終結果_by中間結果(中間結果IDList);

		return 取得検索結果_by最終結果(最終結果IDList);
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	private Map<String, List<String>> get中間結果_by検索契約Bean(契約検索Bean bean) {

		Map<String, List<String>> 中間結果list = new LinkedHashMap();

		//		String 契约ID; //唯一标识，无法替代，只做存根
		if (StringUtils.isNotEmpty(bean.get契约ID())) {
			/*IDList_name = getIDList_byNameandValue("姓名", "Aさん");*/
			中間結果list.put("契约ID", Arrays.asList(bean.get契约ID()));
		}
		//		String 单价From;
		if (StringUtils.isNotEmpty(bean.get单价From())) {
			中間結果list.put("单价", getIDList_by小Map名andValue("单价", bean.get单价From(), ">="));
		}
		//		String 单价To;
		if (StringUtils.isNotEmpty(bean.get单价To())) {
			中間結果list.put("单价", getIDList_by小Map名andValue("单价", bean.get单价To(), "<="));
		}
		//		String 单价円;
		if (StringUtils.isNotEmpty(bean.get单价単位())) {
			中間結果list.put("单价単位", getIDList_by小Map名andValue("单价単位", bean.get单价単位(), "=="));
		}
		//		String 结算币种;
		if (StringUtils.isNotEmpty(bean.get结算币种())) {
			中間結果list.put("结算币种", getIDList_by小Map名andValue("结算币种", bean.get结算币种(), "=="));
		}
		//		String 含交通费;
		if (StringUtils.isNotEmpty(bean.get含交通费())) {
			中間結果list.put("含交通费", getIDList_by小Map名andValue("含交通费", bean.get含交通费(), "=="));
		}
		//		String 开始日期From;
		if (StringUtils.isNotEmpty(bean.get开始日期From())) {
			中間結果list.put("开始日期", getIDList_by小Map名andValue("开始日期", bean.get开始日期From(), ">="));
		}
		//		String 开始日期To;
		if (StringUtils.isNotEmpty(bean.get开始日期To())) {
			中間結果list.put("开始日期", getIDList_by小Map名andValue("开始日期", bean.get开始日期To(), "<="));
		}
		//		String 契约期From;
		if (StringUtils.isNotEmpty(bean.get契约期From())) {
			中間結果list.put("契约期", getIDList_by小Map名andValue("契约期", bean.get契约期From(), ">="));
		}
		//		String 契约期To;
		if (StringUtils.isNotEmpty(bean.get契约期To())) {
			中間結果list.put("契约期", getIDList_by小Map名andValue("契约期", bean.get契约期To(), "<="));
		}
		//		String 契约期Year;
		if (StringUtils.isNotEmpty(bean.get契约期単位())) {
			中間結果list.put("契约期単位", getIDList_by小Map名andValue("契约期単位", bean.get契约期単位(), "=="));
		}
		//		String 契约实际终了日From;
		if (StringUtils.isNotEmpty(bean.get契约实际终了日From())) {
			中間結果list.put("契约实际终了日", getIDList_by小Map名andValue("契约实际终了日", bean.get契约实际终了日From(), ">="));
		}
		//		String 契约实际终了日To;
		if (StringUtils.isNotEmpty(bean.get契约实际终了日To())) {
			中間結果list.put("契约实际终了日", getIDList_by小Map名andValue("契约实际终了日", bean.get契约实际终了日To(), "<="));
		}
		//		String 契约种别;
		if (StringUtils.isNotEmpty(bean.get契约种别())) {
			中間結果list.put("契约种别", getIDList_by小Map名andValue("契约种别", bean.get契约种别(), "=="));
		}
		//		String 甲方名称;
		if (StringUtils.isNotEmpty(bean.get甲方契约者ID())) {
			中間結果list.put("甲方契约者ID", getIDList_by小Map名andValue("甲方契约者ID", bean.get甲方契约者ID(), "=="));
		}
		//		String 乙方名称;
		if (StringUtils.isNotEmpty(bean.get乙方契约者ID())) {
			中間結果list.put("乙方契约者ID", getIDList_by小Map名andValue("乙方名称", bean.get乙方契约者ID(), "=="));
		}

		// 无条件检索时
		if (allfieldIsNUll(bean)) {
			//中間結果list.put("名称",file_db.取得全ID_by項目名("名称"));
			中間結果list.put("ID", file_db.取得全部ID(fileName[0]));
		}

		// 削除時の対応
		中間結果list.put("削除年月日", getIDList_by小Map名andValue("削除年月日", "", "!="));

		return 中間結果list;

	}

//	private List<String> get最終結果_by中間結果(Map<String, List<String>> 中間結果idList) {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}

	private List<契約Bean> 取得検索結果_by最終結果(List<String> 最終結果idList) {

		List<契約Bean> 契約BeanList = new ArrayList();

		for (String 契約id : 最終結果idList) {

			契約BeanList.add(取得契约情報_by契约id(契約id));
		}

		return 契約BeanList;
	}

	private 契約Bean 取得契约情報_by契约id(String 契約id) {
		Map<String, Map> 大Map = file_db.getMap_data();

		契約Bean 契約bean = new 契約Bean();

		契約bean.set契约ID(契約id);

		for (Map.Entry<String, Map> entry : 大Map.entrySet()) {

			if (entry == null) {

				continue;

			}

			Map<String, String> 小Map;

			String 小Map名 = entry.getKey();

			switch (小Map名) {

			case "单价":
				小Map = entry.getValue();
				契約bean.set单价(小Map.get(契約id));
				break;
			case "单价単位":
				小Map = entry.getValue();
				契約bean.set单价単位(小Map.get(契約id));
				break;
			case "开始日期":
				小Map = entry.getValue();
				契約bean.set开始日期(小Map.get(契約id));
				break;
			case "契约期":
				小Map = entry.getValue();
				契約bean.set契约期(小Map.get(契約id));
				break;
			case "契约期单位":
				小Map = entry.getValue();
				契約bean.set契约期单位(小Map.get(契約id));
				break;
			case "结算币种":
				小Map = entry.getValue();
				契約bean.set结算币种(小Map.get(契約id));
				break;
			case "契约实际终了日":
				小Map = entry.getValue();
				契約bean.set契约实际终了日(小Map.get(契約id));
				break;
			case "契约CD":
				小Map = entry.getValue();
				契約bean.set契约CD(小Map.get(契約id));
				break;
			case "含交通费":
				小Map = entry.getValue();
				契約bean.set含交通费(小Map.get(契約id));
				break;
			case "备考说明":
				小Map = entry.getValue();
				契約bean.set备考说明(小Map.get(契約id));
				break;
			case "契约种别":
				小Map = entry.getValue();
				契約bean.set契约种别(小Map.get(契約id));
				break;
			case "甲方契约者ID":
				小Map = entry.getValue();
				契約bean.set甲方契约者ID(小Map.get(契約id));
				break;
			case "乙方契约者ID":
				小Map = entry.getValue();
				契約bean.set乙方契约者ID(小Map.get(契約id));
				break;
			}
		}
		return 契約bean;
	}

	private List<String> getIDList_by小Map名andValue(String 小Map名, String value, String s計算方法) {

		List<String> IDList = new ArrayList();

		Map<String, Map> 大Map = file_db.getMap_data();

		if(大Map == null) return null;

		Map<String, String> 小map = 大Map.get(小Map名);

		if(小map == null) return null;

		for (Map.Entry<String, String> entry : 小map.entrySet()) {

			String str1 = entry.getValue().replace("\\", "");
			String str2 = value.replace("\\", "");

			switch (s計算方法) {

			case ">=":
				if (str1.equals(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

			case ">":

				if (NumberUtils.toInt(str1) > NumberUtils.toInt(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

				break;

			case "<=":
				if (str1.equals(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

			case "<":

				if (NumberUtils.toInt(str1) < NumberUtils.toInt(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}
				break;

			case "==":

				if (str1.equals(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

				break;

			case "!=":

				if (!str1.equals(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

				break;

			case "like":

				if (value_like(entry.getValue(), value)) {

					String id = entry.getKey();

					IDList.add(id);
				}
			}

		}

		return IDList;
	}

	public static boolean allfieldIsNUll(Object o) {
		try {
			for (Field field : o.getClass().getDeclaredFields()) {
				field.setAccessible(true);//把私有属性公有化
				Object object = field.get(o);
				if (object == null) {
					continue;
				}
				if (object instanceof CharSequence) {
					if (!StringUtils.isEmpty((String) object)) {
						return false;
					}
				} else {
					if (!ObjectUtils.isEmpty((Object[]) object)) {
						return false;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private boolean value_like(String str1, String str2) {
		// java 两个字符串取交集
		HashSet<String> result = new HashSet<String>();
		int length1 = str1.length();
		int length2 = str2.length();
		for (int i = 0; i < length1; i++) {
			for (int j = 0; j < length2; j++) {
				String char1 = str1.charAt(i) + "";
				String char2 = str2.charAt(j) + "";
				if (char1.equals(char2)) {
					result.add(char1);
				}
			}
		}

		return result.isEmpty() ? false : true;
	}

	public String 追加契約_by契約Bean(契約Bean bean) {

		文件db file_db = new 文件db("契約");

		// ①チェック入力
		file_db.情報読み込み(fileName);

		// ②追加処理
		追加契約_byFile_db_契約Bean(file_db, bean);

		return "";
	}

	private void 追加契約_byFile_db_契約Bean(文件db file_db2, 契約Bean bean) {

		String path;
		String ID = null;
		try {
			// ①採番
			ID = file_db.採番(file_db.getSPath() + "契约ID" + ".txt") + 1 + "";

		} catch (IOException e) {

			e.printStackTrace();
		}

		for (String s文件名 : fileName) {

			path = file_db.getSPath() + s文件名 + ".txt";

			switch (s文件名) {

			case "契约ID":

				if (!StringUtils.isEmpty(ID)) {
					file_db.文件書込(path, ID + "," + ID);
				}
				break;

			case "单价":

				if (!StringUtils.isEmpty(bean.get单价())) {
					file_db.文件書込(path, ID + "," + bean.get单价());
				}
				break;

			case "开始日期":

				if (!StringUtils.isEmpty(bean.get开始日期())) {
					file_db.文件書込(path, ID + "," + bean.get开始日期());
				}
				break;

			case "契约期":

				if (!StringUtils.isEmpty(bean.get契约期())) {
					file_db.文件書込(path, ID + "," + bean.get契约期());
				}
				break;

			case "契约期单位":

				if (!StringUtils.isEmpty(bean.get契约期单位())) {
					file_db.文件書込(path, ID + "," + bean.get契约期单位());
				}
				break;

			case "契约种别":

				if (!StringUtils.isEmpty(bean.get契约种别())) {
					file_db.文件書込(path, ID + "," + bean.get契约种别());
				}
				break;

			case "结算币种":

				if (!StringUtils.isEmpty(bean.get结算币种())) {
					file_db.文件書込(path, ID + "," + bean.get结算币种());
				}
				break;

			case "契约实际终了日":

				if (!StringUtils.isEmpty(bean.get契约实际终了日())) {
					file_db.文件書込(path, ID + "," + bean.get契约实际终了日());
				}
				break;

			case "契约CD":

				if (!StringUtils.isEmpty(bean.get契约CD())) {
					file_db.文件書込(path, ID + "," + bean.get契约CD());
				}
				break;

			case "含交通费":

				if (!StringUtils.isEmpty(bean.get含交通费())) {
					file_db.文件書込(path, ID + "," + bean.get含交通费());
				}
				break;
			case "备考说明":

				if (!StringUtils.isEmpty(bean.get备考说明())) {
					file_db.文件書込(path, ID + "," + bean.get备考说明());
				}
				break;
			case "甲方契约者ID":

				if (!StringUtils.isEmpty(bean.get甲方契约者ID())) {
					file_db.文件書込(path, ID + "," + bean.get甲方契约者ID());
				}
				break;
			case "乙方契约者ID":

				if (!StringUtils.isEmpty(bean.get乙方契约者ID())) {
					file_db.文件書込(path, ID + "," + bean.get乙方契约者ID());
				}
				break;
			}

		}
	}

	public void 更新契約_by契約Bean(契約Bean bean) {
		文件db file_db = new 文件db("契约");

		file_db.情報読み込み(fileName);
		String ID = bean.get契约ID();

		if(StringUtils.isEmpty(ID)){
			System.out.printf("更新契約_by契約Bean 时 ID为空。");
			return;
		}
		if (! StringUtils.equals(bean.getOld_单价(), bean.get单价())) {
			String path = file_db.getSPath() + "单价.txt";
			file_db.文件書込(path, ID + "," + bean.get单价());
		}
		if (! StringUtils.equals(bean.getOld_单价単位(), bean.get单价単位())) {
			String path = file_db.getSPath() + "单价単位.txt";
			file_db.文件書込(path, ID + "," + bean.get单价単位());
		}
		if (! StringUtils.equals(bean.getOld_开始日期(), bean.get开始日期())) {
			String path = file_db.getSPath() + "开始日期.txt";
			file_db.文件書込(path, ID + "," + bean.get开始日期());
		}
		if (! StringUtils.equals(bean.getOld_契约期(), bean.get契约期())) {
			String path = file_db.getSPath() + "契约期.txt";
			file_db.文件書込(path, ID + "," + bean.get契约期());
		}
		if (! StringUtils.equals(bean.getOld_契约期单位(), bean.get契约期单位())) {
			String path = file_db.getSPath() + "契约期单位.txt";
			file_db.文件書込(path, ID + "," + bean.get契约期单位());
		}
		if (! StringUtils.equals(bean.getOld_契约种别(), bean.get契约种别())) {
			String path = file_db.getSPath() + "契约种别.txt";
			file_db.文件書込(path, ID + "," + bean.get契约种别());
		}
		if (! StringUtils.equals(bean.getOld_结算币种(), bean.get结算币种())) {
			String path = file_db.getSPath() + "结算币种.txt";
			file_db.文件書込(path, ID + "," + bean.get结算币种());
		}
		if (! StringUtils.equals(bean.getOld_契约实际终了日(), bean.get契约实际终了日())) {
			String path = file_db.getSPath() + "契约实际终了日.txt";
			file_db.文件書込(path, ID + "," + bean.get契约实际终了日());
		}
		if (! StringUtils.equals(bean.getOld_契约CD(), bean.get契约CD())) {
			String path = file_db.getSPath() + "契约CD.txt";
			file_db.文件書込(path, ID + "," + bean.get契约CD());
		}
		if (! StringUtils.equals(bean.getOld_含交通费(), bean.get含交通费())) {
			String path = file_db.getSPath() + "含交通费.txt";
			file_db.文件書込(path, ID + "," + bean.get含交通费());
		}
		if (! StringUtils.equals(bean.getOld_备考说明(), bean.get备考说明())) {
			String path = file_db.getSPath() + "备考说明.txt";
			file_db.文件書込(path, ID + "," + bean.get备考说明());
		}
		if (! StringUtils.equals(bean.getOld_甲方契约者ID(), bean.get甲方契约者ID())) {
			String path = file_db.getSPath() + "甲方契约者ID.txt";
			file_db.文件書込(path, ID + "," + bean.get甲方契约者ID());
		}
		if (! StringUtils.equals(bean.getOld_乙方契约者ID(), bean.get乙方契约者ID())) {
			String path = file_db.getSPath() + "乙方契约者ID.txt";
			file_db.文件書込(path, ID + "," + bean.get乙方契约者ID());
		}
	}
}
