package com.x.collaboration.assemble.websocket.jaxrs.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonElement;
import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.http.HttpMediaType;
import com.x.base.core.project.jaxrs.ResponseFactory;
import com.x.base.core.project.jaxrs.StandardJaxrsAction;
import com.x.collaboration.assemble.websocket.Business;

@Path("dialog")
public class DialogAction extends StandardJaxrsAction {

	// private static Logger logger =
	// LoggerFactory.getLogger(MessageAction.class);

	// @HttpMethodDescribe(value = "列示当前人员的聊天对话.", response = WrapOutString.class)
	@GET
	@Path("list/talk/person/{person}")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listTalk(@Context HttpServletRequest request, @PathParam("person") String person) {
		ActionResult<List<JsonElement>> result = new ActionResult<>();
		List<JsonElement> wraps = new ArrayList<>();
		try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
			Business business = new Business(emc);
			EffectivePerson effectivePerson = this.effectivePerson(request);
			wraps = new ActionListTalk().execute(business, effectivePerson, person);
			result.setData(wraps);
		} catch (Throwable th) {
			th.printStackTrace();
			result.error(th);
		}
		return ResponseFactory.getDefaultActionResultResponse(result);
	}

}