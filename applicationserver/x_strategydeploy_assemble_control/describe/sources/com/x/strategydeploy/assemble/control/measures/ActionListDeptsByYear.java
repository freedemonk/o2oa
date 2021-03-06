package com.x.strategydeploy.assemble.control.measures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.jaxrs.WrapStringList;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.base.core.project.tools.ListTools;
import com.x.organization.core.entity.Unit;
import com.x.strategydeploy.assemble.control.Business;
import com.x.strategydeploy.core.entity.MeasuresInfo;
import com.x.strategydeploy.core.entity.MeasuresInfo_;

public class ActionListDeptsByYear extends BaseAction {
	private static  Logger logger = LoggerFactory.getLogger(ActionListDeptsByYear.class);

	public static class Wo extends WrapStringList {

	}

	
	public Wo execute(HttpServletRequest request, EffectivePerson effectivePerson, String year) throws Exception {

		try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
			Business business = new Business(emc);
			EntityManager em = business.entityManagerContainer().get(MeasuresInfo.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<MeasuresInfo> cq = cb.createQuery(MeasuresInfo.class);
			Root<MeasuresInfo> root = cq.from(MeasuresInfo.class);
			Predicate p = cb.isNotEmpty(root.get(MeasuresInfo_.deptlist));
			List<MeasuresInfo> os = em.createQuery(cq.select(root).where(p)).getResultList();
			List<String> list = new ArrayList<>();
			for (MeasuresInfo measuresInfo : os) {
				if (ListTools.isNotEmpty(measuresInfo.getDeptlist())) {
					list.addAll(measuresInfo.getDeptlist());
				}
			}
			//logger.info("DeptList:" + list.toString());
			list = list.stream().filter(o -> !StringUtils.isEmpty(o)).distinct().sorted().collect(Collectors.toList());
			Wo wo = new Wo();
			wo.getValueList().addAll(list);
			return wo;
		} catch (Exception e) {
			throw e;
		}

	}
}
