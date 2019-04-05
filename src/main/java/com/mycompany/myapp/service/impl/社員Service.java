package com.mycompany.myapp.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.ObjectUtils;

import com.mycompany.myapp.bean.社員Bean;
import com.mycompany.myapp.bean.社員検索Bean;
import com.mycompany.myapp.service.文件db;
import com.mycompany.myapp.service.親Service;

public class 社員Service extends 親Service{

	// "番号" 必须放在0号位，否则全件检索时会出问题
	String[] fileName = { "番号", "姓名", "入社年月日", "生年月日", "性別", "契約種類", "削除年月日", "電話番号" };

	文件db file_db = new 文件db("社员");

	public List<社員Bean> 検索社員_by検索Bean(社員検索Bean bean) {

		file_db.情報読み込み(fileName);

		Map<String,List<String>> 中間結果IDList = get中間結果_by社員検索Bean(bean);

		List<String> 最終結果IDList = get最終結果_by中間結果(中間結果IDList);

		return 取得検索結果_by最終結果(最終結果IDList);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<社員Bean> 取得検索結果_by最終結果(List<String> 最終結果idList) {

		List<社員Bean> 社員BeanList = new ArrayList();

		for (String 社員id : 最終結果idList) {

			社員BeanList.add(取得社員情報_by社員id(社員id));
		}

		return 社員BeanList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private 社員Bean 取得社員情報_by社員id(String 社員id) {

		Map<String, Map> 大Map = file_db.getMap_data();

		社員Bean 社員bean = new 社員Bean();

		for (Map.Entry<String, Map> entry : 大Map.entrySet()) {

			if (entry == null) {

				continue;

			}

			Map<String, String> 小Map;

			String 小Map名 = entry.getKey();

			社員bean.setS_ID(社員id);

			switch (小Map名) {

			case "番号":
				小Map = entry.getValue();
				社員bean.set番号(小Map.get(社員id));
				break;
			case "姓名":
				小Map = entry.getValue();
				社員bean.set姓名(小Map.get(社員id));
				break;
			case "性別":
				小Map = entry.getValue();
				社員bean.set性別(小Map.get(社員id));
				break;
			case "生年月日":
				小Map = entry.getValue();
				社員bean.set生年月日(小Map.get(社員id));
				break;
			case "入社年月日":
				小Map = entry.getValue();
				社員bean.set入社年月日(小Map.get(社員id));
				break;
			case "契約種類":
				小Map = entry.getValue();
				社員bean.set契約種類(小Map.get(社員id));
				break;
			case "電話番号":
				小Map = entry.getValue();
				社員bean.set電話番号(小Map.get(社員id));
				break;
			}
		}
		return 社員bean;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String,List<String>> get中間結果_by社員検索Bean(社員検索Bean bean) {

		Map<String,List<String>> 中間結果list = new LinkedHashMap();
		if (StringUtils.isNotEmpty(bean.getS_ID())) {

			/*IDList_name = getIDList_byNameandValue("姓名", "Aさん");*/
			中間結果list.put("ID",Arrays.asList(bean.getS_ID()));
		}

		if (StringUtils.isNotEmpty(bean.get番号())) {

			/*IDList_name = getIDList_byNameandValue("姓名", "Aさん");*/
			中間結果list.put("番号",getIDList_by小Map名andValue("番号", bean.get番号(), "=="));

		}
		/*Object getIDList_byNameandValue;*/
		if (StringUtils.isNotEmpty(bean.get姓名())) {

			/*IDList_name = getIDList_byNameandValue("姓名", "Aさん");*/
			中間結果list.put("姓名",getIDList_by小Map名andValue("姓名", bean.get姓名(), "like"));

		}

		if (StringUtils.isNotEmpty(bean.get性別())) {

			中間結果list.put("性別",getIDList_by小Map名andValue("性別", bean.get性別(), "=="));

		}

		if (StringUtils.isNotEmpty(bean.get生年月日開始())) {

			中間結果list.put("開始生年月日",getIDList_by小Map名andValue("生年月日", bean.get生年月日開始(), ""));
		}


		if (StringUtils.isNotEmpty(bean.get生年月日終了())) {

			中間結果list.put("終了生年月日",getIDList_by小Map名andValue("生年月日", bean.get生年月日終了(), ""));
		}


		if (StringUtils.isNotEmpty(bean.get入社年月日開始())) {

			中間結果list.put("開始入社月日",getIDList_by小Map名andValue("入社年月日", bean.get入社年月日開始(), ""));
		}

		if (StringUtils.isNotEmpty(bean.get入社年月日終了())) {

			中間結果list.put("終了入社月日",getIDList_by小Map名andValue("入社年月日", bean.get入社年月日終了(), ""));
		}

		if (StringUtils.isNotEmpty(bean.get契約種類())) {

			中間結果list.put("契約種類",getIDList_by小Map名andValue("契約種類", bean.get契約種類(), "=="));
		}
		if (StringUtils.isNotEmpty(bean.get電話番号())) {

			中間結果list.put("電話番号",getIDList_by小Map名andValue("電話番号", bean.get電話番号(), "=="));
		}


		// 无条件检索时
		if(allfieldIsNUll(bean)) {

			//中間結果list.put("ID",file_db.取得全部ID("番号"));
			中間結果list.put("ID",file_db.取得全部ID(fileName[0]));
		}

		// 削除時の対応
		中間結果list.put("削除年月日",getIDList_by小Map名andValue("削除年月日", "", "!="));

		return 中間結果list;

	}

	private List<String> getIDList_by小Map名andValue(String 小Map名, String value, String s計算方法) {

		List<String> IDList = new ArrayList();

		Map<String, Map> 大Map = file_db.getMap_data();

		Map<String, String> 小map = 大Map.get(小Map名);

		for (Map.Entry<String, String> entry : 小map.entrySet()) {

			String str1= entry.getValue().replace("\\", "");
			String str2= value.replace("\\", "");

			switch(s計算方法) {

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

				if ( !str1.equals(str2)) {

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

	private boolean value_like(String str1, String str2) {
		// java 两个字符串取交集
        HashSet<String> result = new HashSet<String>();
        int length1 = str1.length();
        int length2 = str2.length();
        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++) {
                String char1 = str1.charAt(i) + "";
                String char2 = str2.charAt(j) + "";
                if (char1.equals(char2))
                {
                    result.add(char1);
                }
            }
        }

        return result.isEmpty()? false:true;
    }

	public String 追加社員_by社員Bean(社員Bean bean) {

		文件db file_db = new 文件db("社员");

		// ①チェック入力
		file_db.情報読み込み(fileName);
		if (!StringUtils.isEmpty(file_db.取得指定情报_by項目名_ID("番号", bean.getS_ID()))) {
			return "社員番号はすでに登録されている。";
		}

		// ②追加処理
		追加社員_byFile_db_社員Bean(file_db, bean);


		return "";
	}

	private void 追加社員_byFile_db_社員Bean(文件db file_db2, 社員Bean bean) {

		String path;
		String ID = null;
		try {
			// ①採番
			ID = file_db.採番(file_db.getSPath() +  "番号" + ".txt") + 1 +"";

		} catch (IOException e) {

			e.printStackTrace();
		}

		for(String s文件名 : fileName) {

			path = file_db.getSPath() +  s文件名 + ".txt";

			switch(s文件名) {

			case "番号":

				if(!StringUtils.isEmpty(bean.get番号())) {
					file_db.文件書込(path, ID + "," + bean.get番号());
				}
				break;

			case "姓名":

				if(!StringUtils.isEmpty(bean.get姓名())) {
					file_db.文件書込(path, ID + "," + bean.get姓名());
				}
				break;

			case "入社年月日":

				if(!StringUtils.isEmpty(bean.get入社年月日())) {
					file_db.文件書込(path, ID + "," + bean.get入社年月日());
				}
				break;

			case "契約種類":

				if(!StringUtils.isEmpty(bean.get契約種類())) {
					file_db.文件書込(path, ID + "," + bean.get契約種類());
				}
				break;

			case "性別":

				if(!StringUtils.isEmpty(bean.get性別())) {
					file_db.文件書込(path, ID + "," + bean.get性別());
				}
				break;

			case "生年月日":

				if(!StringUtils.isEmpty(bean.get生年月日())) {
					file_db.文件書込(path, ID + "," + bean.get生年月日());
				}
				break;

			case "電話番号":

				if(!StringUtils.isEmpty(bean.get電話番号())) {
					file_db.文件書込(path, ID + "," + bean.get電話番号());
				}
				break;

			}


		}
	}

	public void 更新社員_by社員Bean(社員Bean bean) {
		// 更新时
		// 将最新信息追加到指定文件
		文件db file_db = new 文件db("社员");
		//String ID = bean.getOld_番号();
		file_db.情報読み込み(fileName);
		String ID = bean.getS_ID();

		if(!StringUtils.equals(bean.getOld_入社年月日(), bean.get入社年月日())) {
			String path = file_db.getSPath() +  "入社年月日.txt";
			file_db.文件書込(path, ID + "," + bean.get入社年月日());
		}
		if(!StringUtils.equals(bean.getOld_契約種類(), bean.get契約種類())) {
			String path = file_db.getSPath() +  "契約種類.txt";
			file_db.文件書込(path, ID + "," + bean.get契約種類());

		}
		if(!StringUtils.equals(bean.getOld_姓名(), bean.get姓名())) {
			String path = file_db.getSPath() +  "姓名.txt";
			file_db.文件書込(path, ID + "," + bean.get姓名());

		}
		if(!StringUtils.equals(bean.getOld_電話番号(), bean.get電話番号())) {
			String path = file_db.getSPath() +  "電話番号.txt";
			file_db.文件書込(path, ID + "," + bean.get電話番号());

		}
		if(!StringUtils.equals(bean.getOld_性別(), bean.get性別())) {
			String path = file_db.getSPath() +  "性別.txt";
			file_db.文件書込(path, ID + "," + bean.get性別());

		}
		if(!StringUtils.equals(bean.getOld_生年月日(), bean.get生年月日())) {
			String path = file_db.getSPath() +  "生年月日.txt";
			file_db.文件書込(path, ID + "," + bean.get生年月日());
		}

	}

	public void 削除社員_by社員Bean(社員Bean bean) {
		String path = file_db.getSPath() +  "削除年月日.txt";
		//String ID = bean.getOld_番号();

		file_db.情報読み込み(fileName);
		String ID = bean.getS_ID();

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
		String sDate = df.format(new Date()).toString();// new Date()为获取当前系统时间
		file_db.文件書込(path, ID + "," + sDate);

		// 1、削除时（采取逻辑删除。就是并不是真的删除了。而是做上删除的标记）
		//    追加一个叫削除时间的文件
		//    -----------内容如下-------------
		//    【采番】，【削除时间】
		//    【采番】，【削除时间】
		//    --------------------------------

		// 2、検索時 ，自动附加【无删除记录】的条件）
		//    凡是上面有记录的。
		//    都不会取得信息
	}

	public static boolean allfieldIsNUll(Object o){
	    try{
	        for(Field field:o.getClass().getDeclaredFields()){
	            field.setAccessible(true);//把私有属性公有化
	            Object object = field.get(o);
	            if(object == null){
	            	continue;
	            }
	            if(object instanceof CharSequence){
	                if(!StringUtils.isEmpty((String)object)){
	                    return false;
	                }
	            }else{
	                if(!ObjectUtils.isEmpty((Object[]) object)){
	                    return false;
	                }
	            }

	        }
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	    return true;
	}
}

