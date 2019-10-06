package com.pajf.dm.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import com.actionsoft.bpms.commons.htmlframework.HtmlPageTemplate;
import com.actionsoft.bpms.server.RequestParams;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.server.bind.annotation.Controller;
import com.actionsoft.bpms.server.bind.annotation.Mapping;
import com.actionsoft.bpms.util.DBSql;
import com.alibaba.fastjson.JSONObject;


/**
 * 
 * @.description 扩展按钮-cmd开发类
 * @version 1.0
 * @author wangz
 * @.update 2019年10月3日 下午8:30:19
 */
@Controller
public class ExtButtonController {

	/*************************************************************************/
	// 表单页面内扩展按钮-点击“发送邮件”按钮，显示页面cmd
	@Mapping("com.awspaas.user.apps.extbutton.formpage_sendemail_showpage")
	public String formpage_sendemail_showpage(UserContext uc, RequestParams params) {
		// 表单流程实例ID
		String bindid = params.get("bindid");
		// 主表表名
		String pboname = params.get("pboname");
		String iscloseForm = params.get("iscloseForm");
		String ext1 = params.get("ext1");
		String ext2 = params.get("ext2");
		String ext3 = params.get("ext3");
		System.out.println("bindid=[" + bindid + "],iscloseForm=[" + iscloseForm + "],pboname=[" + pboname + "],ext1=[" + ext1 + "],ext2=[" + ext2 + "],ext3=[" + ext3 + "]");
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("bindid", bindid);
		result.put("pboname", pboname);
		result.put("iscloseForm", iscloseForm);
		result.put("ext1", ext1);
		result.put("ext2", ext2);
		result.put("ext3", ext3);
		result.put("sid", uc.getSessionId());
		return HtmlPageTemplate.merge("com.awspaas.user.apps.extbutton", "formpage_sendemail_showpage.htm", result);
	}
	// 点击表单页面内扩展“发送邮件”按钮，提交后处理cmd
	@Mapping("com.awspaas.user.apps.extbutton.formpage_sendemail_showpage_operation")
	public String formpage_sendemail_showpage_operation(UserContext uc, RequestParams params) {
		// bindid
		String bindid = params.get("bindid");
		String pboname = params.get("pboname");
		String emailtitle = params.get("emailtitle");
		String userids = params.get("userids");
		String ccuserids = params.get("ccuserids");
		String mailcontent = params.get("mailcontent");
		String ext1 = params.get("ext1");
		String ext2 = params.get("ext2");
		String ext3 = params.get("ext3");
		System.out.println("bindid=["+bindid+"],pboname=["+pboname+"],emailtitle=["+emailtitle+"],userids=["+userids+"],ccuserids=["+ccuserids+"],mailcontent=["+mailcontent+"]");
		System.out.println("ext1=["+ext1+"],ext2=["+ext2+"],ext3=["+ext3+"]");
		System.out.println("执行发送邮件!");
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("FLAG", "true");
		return jsonobj.toString();
	}
	/*************************************************************************/
	// 视图工具条扩展按钮-点击“批量评价”按钮，显示页面cmd
	@Mapping("com.awspaas.user.apps.extbutton.dw_evaluate_showpage")
	public String dw_evaluate_showpage(UserContext uc, RequestParams params) {
		// 选择数据集合
		String selected_data_ids = params.get("selected_data_ids");
		// 主表表名
		String pboname = params.get("pboname");
		// 主表表名
		String colname = params.get("colname");
		System.out.println("selected_data_ids=[" + selected_data_ids + "],pboname=[" + pboname + "],colname=[" + colname + "]");
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("selected_data_ids", selected_data_ids);
		result.put("pboname", pboname);
		result.put("colname", colname);
		result.put("sid", uc.getSessionId());
		return HtmlPageTemplate.merge("com.awspaas.user.apps.extbutton", "dw_evaluate_showpage.htm", result);
	}
	// 点击视图工具条扩展按钮-点击“批量评价”按钮，提交后处理cmd
	@Mapping("com.awspaas.user.apps.extbutton.dw_evaluate_showpage_operation")
	public String dw_evaluate_showpage_operation(UserContext uc, RequestParams params) {
		// evaluate
		String evaluate = params.get("evaluate");
		String selected_data_ids = params.get("selected_data_ids");
		String pboname = params.get("pboname");
		String colname = params.get("colname");

		System.out.println("evaluate=[" + evaluate + "],selected_data_ids=[" + selected_data_ids + "],pboname=[" + pboname + "],colname=[" + colname + "]");
		
		String[] selected_data_id_arr = selected_data_ids.split(",");
		for (int i = 0; i < selected_data_id_arr.length; i++) {
			String boid = selected_data_id_arr[i];
			System.err.println("update "+pboname+" set "+colname+" = '" + evaluate + "' where id = '" + boid + "'");
			DBSql.update("update "+pboname+" set "+colname+" = '" + evaluate + "' where id = '" + boid + "'");
		}
		
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("FLAG", "true");
		return jsonobj.toString();
	}
	/*************************************************************************/
	
}
