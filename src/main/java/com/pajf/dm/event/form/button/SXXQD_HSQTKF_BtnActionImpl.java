package com.pajf.dm.event.form.button;

import java.util.HashMap;
import java.util.Map;
import com.actionsoft.apps.AppsConst;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ListenerConst;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListener;
import com.actionsoft.bpms.commons.htmlframework.HtmlPageTemplate;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.util.DBSql;

/**
 * @.description 综合客服-寿险新渠道-指令传输-后送至其他客服按钮处理类
 * @version 1.0
 * @author wangz
 * @.update 2019年10月5日 下午3:32:21 
 */

public class SXXQD_HSQTKF_BtnActionImpl extends ValueListener {

	@Override
	public String execute(ProcessExecutionContext pec) throws Exception {
		// 参数获取
		// 记录ID
		String boId = pec.getParameterOfString(ListenerConst.FORM_EVENT_PARAM_BOID);
		// 表单ID
		String formId = pec.getParameterOfString(ListenerConst.FORM_EVENT_PARAM_FORMID);
		// BO表名
		String boName = pec.getParameterOfString(ListenerConst.FORM_EVENT_PARAM_BONAME);
		
		String bindid=DBSql.getString("SELECT BINDID FROM "+boName+" WHERE ID = '"+boId+"'", "BINDID");
		
		// Ajax方式
		ResponseObject ro = ResponseObject.newOkResponse();
		boolean r = true;// 针对业务进行处理
		
		
		
		// 处理业务逻辑成功时
		if (r) {
			ro.msg("成功后送至其他客服");// 返回给服务器的消息
			ro.put("bindid", bindid);// 放入前端需要的参数
			return ro.toString();
		} else {
			// 错误时
			ro = ResponseObject.newErrResponse();
			ro.msg("错误");
			return ro.toString();
		}
	}
}