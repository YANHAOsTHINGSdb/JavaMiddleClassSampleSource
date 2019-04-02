package com.mycompany.myapp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.bean.検索Bean;
import com.mycompany.myapp.bean.社員Bean;
import com.mycompany.myapp.service.impl.社員Service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );

		return "home";
	}
	/**
	 * テストデータの配列を返却する。
	 */
	@RequestMapping(value = "getTestData", method = RequestMethod.POST)
	@ResponseBody //将返回结果转成Json
	public List<社員Bean> getTestData(@RequestBody 検索Bean 検索bean) {//@RequestBody 将Json转成Java对象

	    logger.info("call getTestData");
	    List<社員Bean> result = new ArrayList<社員Bean>();

//	    社員情報Service 社員情報service = new 社員情報ServiceImpl();
//	    社員情報service.検索(bean);
	    社員Service 社員service = new 社員Service();

	    return 社員service.検索社員_by検索Bean(検索bean);
	}

	public 社員Bean makeHomeBean(String name, String maleFemale, String birthDate, String joinDate, String 契約種類, String ID) {
		社員Bean bean = new 社員Bean();
		bean.set番号(ID);
		bean.set姓名(name);
		bean.set性別(maleFemale);
		bean.set入社年月日(birthDate);
		bean.set入社年月日(joinDate);
		bean.set契約種類(契約種類);

		return bean;
	}

	@RequestMapping(value ="edit", method=RequestMethod.GET)
	public String edit(社員Bean bean, Model model){
		model.addAttribute("titleName", "社員編集");
		model.addAttribute("モード", "編集");
		model.addAttribute("番号", bean.get番号());
		model.addAttribute("姓名", bean.get姓名());
		model.addAttribute("性別", bean.get性別());
		model.addAttribute("生年月日", bean.get生年月日());
		model.addAttribute("入社年月日", bean.get入社年月日());
		model.addAttribute("契約種類", bean.get契約種類());

	    return "addEmployee";

	}
/*    public String edit(
    		@RequestParam("番号") String 番号,
    	    @RequestParam("姓名") String 姓名,
    	    @RequestParam("性別") String 性別,
    	    @RequestParam("入社年月日") String 入社年月日,
    	    @RequestParam("生年月日") String 生年月日,
    	    @RequestParam("契約種類") String 契約種類,
    	    HttpServletRequest request){

    	社員Bean user = new 社員Bean();

	    return "addEmployee";

	}*/

    @RequestMapping(value ="add", method=RequestMethod.GET)
    public ModelAndView add(){

        ModelAndView modelAndView = new ModelAndView("addEmployee");
        modelAndView.getModel().put("titleName", "社員追加");

        return modelAndView;
	}


    @RequestMapping(value ="save", method=RequestMethod.POST)
    public String save(@ModelAttribute("fbean") 社員Bean bean, Model model){

    	社員Service 社員service = new 社員Service();

    	String sMsg = 社員service.追加社員_by社員Bean(bean);
    	if (StringUtils.isEmpty(sMsg)) {
    		return "home";
    	}else {
    		model.addAttribute("titleName", "社員追加");
    		model.addAttribute("番号", bean.get番号());
    		model.addAttribute("姓名", bean.get姓名());
    		model.addAttribute("性別", bean.get性別());
    		model.addAttribute("生年月日", bean.get生年月日());
    		model.addAttribute("入社年月日", bean.get入社年月日());
    		model.addAttribute("契約種類", bean.get契約種類());
    		model.addAttribute("errorMsg", sMsg);
    		return "addEmployee";
    	}

	}

    @RequestMapping(value ="update", method=RequestMethod.POST)
    public String update(@ModelAttribute("fbean") 社員Bean bean, HttpSession session){

    	社員Service 社員service = new 社員Service();

    	社員service.更新社員_by社員Bean(bean);

	    return "home";
	}

    @RequestMapping(value ="delete", method=RequestMethod.POST)
    public String delete(@RequestBody 社員Bean bean, HttpSession session){

    	社員Service 社員service = new 社員Service();

    	社員service.削除社員_by社員Bean(bean);

	    return "home";
	}

    @RequestMapping(value ="back", method=RequestMethod.POST)
    public String back(){

	    return "home";
	}

    @RequestMapping(value="exportexceltest")
    @ResponseBody
    public String exportExcelTest(HttpServletResponse response){
        // 获取workbook对象
        Workbook workbook = exportSheetByTemplate() ;
        // 判断数据
        if(workbook == null) {
            return "fail";
        }
        // 设置excel的文件名称
        String excelName = "测试excel" ;
        // 重置响应对象
        response.reset();
        // 当前日期，用于导出文件名称
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "["+excelName+"-"+sdf.format(new Date())+"]";
        // 指定下载的文件名--设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=" +dateStr+".xls");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 写出数据输出流到页面
        try {
            OutputStream output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 模版单sheet导出示例
     * @return
     */
    public Workbook exportSheetByTemplate(){
        // 查询数据,此处省略
        List<社員Bean> list = new ArrayList<社員Bean>();
        int count1 = 0 ;
//        社員Bean easyPOIModel11 = new 社員Bean(String.valueOf(count1++),"信科","张三","男",20) ;
//        社員Bean easyPOIModel12 = new 社員Bean(String.valueOf(count1++),"生工",new User("李四","男",17)) ;
//        社員Bean easyPOIModel13 = new 社員Bean(String.valueOf(count1++),"化工",new User("淑芬","女",34)) ;
//        社員Bean easyPOIModel14 = new 社員Bean(String.valueOf(count1++),"信科",new User("仲达","男",55)) ;

        社員Bean easyPOIModel11 = null;
        社員Bean easyPOIModel12 = null;
        社員Bean easyPOIModel13 = null;
        社員Bean easyPOIModel14 = null;

		list.add(easyPOIModel11) ;
        easyPOIModel11 = null ;

		list.add(easyPOIModel12) ;
        easyPOIModel12 = null ;

		list.add(easyPOIModel13) ;
        easyPOIModel13 = null ;

		list.add(easyPOIModel14) ;
        easyPOIModel14 = null ;
        // 设置导出配置
        // 获取导出excel指定模版
        TemplateExportParams params = new TemplateExportParams("d:/项目测试文件夹/easypoiExample.xlsx");
        // 标题开始行
        // params.setHeadingStartRow(0);
        // 标题行数
        // params.setHeadingRows(2);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        params.setSheetName("班级信息");
        Map<String,Object> map = new HashMap<String,Object>() ;
        map.put("list",list) ;
        // 导出excel
        return ExcelExportUtil.exportExcel(params, map);
    }

}
