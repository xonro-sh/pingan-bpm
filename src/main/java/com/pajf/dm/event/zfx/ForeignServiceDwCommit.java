package com.pajf.dm.event.zfx;

import com.actionsoft.bpms.bo.engine.BO;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListener;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.sdk.local.SDK;

/**
 *
 * @author louie
 * @date created in 2019-10-5 18:29
 */
public class ForeignServiceDwCommit extends ValueListener {

	@Override
	public String execute(ProcessExecutionContext context) throws Exception {
		BO foreignBo = context.getBO("BO_DM_ZFX_WSFW");

		// 创建外事服务流程并初始化数据
		ProcessInstance foreignServiceInstance = SDK.getProcessAPI().createProcessInstance("", context.getUserContext().getUID(), "外事服务流程");
		SDK.getBOAPI().create("", foreignBo, foreignServiceInstance, context.getUserContext());
		SDK.getProcessAPI().start(foreignServiceInstance);

		return null;
	}
}
