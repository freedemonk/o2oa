package com.x.cms.assemble.control.jaxrs.viewfieldconfig.exception;

import com.x.base.core.project.exception.PromptException;

public class ExceptionViewQueryByIdEmpty extends PromptException {

	private static final long serialVersionUID = 1859164370743532895L;

	public ExceptionViewQueryByIdEmpty( Throwable e, String id ) {
		super( "系统根据视图ID获取视图时发生异常。ID：" + id, e );
	}
}
