package com.jeecms.cms.action;

import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.jeecms.cms.entity.ChnlModel;
import com.jeecms.cms.entity.ChnlModelItem;
import com.jeecms.cms.manager.ChnlModelItemMng;
import com.jeecms.cms.manager.ChnlModelMng;
import com.jeecms.common.hibernate3.Updater;

@SuppressWarnings("serial")
@Scope("prototype")
@Controller("cms.chnlModelAct")
public class ChnlModelAct extends com.jeecms.cms.CmsSysAction {
	private static final Logger log = LoggerFactory
			.getLogger(ChnlModelAct.class);

	public String list() {
		this.list = chnlModelMng.getModels(getRootWeb().getId(), true);
		return LIST;
	}

	public String add() {
		return ADD;
	}

	public String save() {
		// 拷贝模型
		if (modelId != null) {
			ChnlModel origModel = chnlModelMng.findById(modelId);
			Set<ChnlModelItem> origItems = origModel.getItems();
			ChnlModelItem item;
			for (ChnlModelItem copyItem : origItems) {
				item = new ChnlModelItem();
				try {
					PropertyUtils.copyProperties(item, copyItem);
				} catch (Exception e) {
					log.error("拷贝模型失败", e);
				}
				item.setId(null);
				bean.addModelItem(item);
			}
		}
		chnlModelMng.save(bean);
		log.info("新增 模型 成功:{}", bean.getName());
		return list();
	}

	public String edit() {
		this.bean = chnlModelMng.findById(id);
		return EDIT;
	}

	public String copy() {
		isCopy = true;
		this.bean = chnlModelMng.findById(id);
		return ADD;
	}

	public String update() {
		// update时，没有新增modelItem，只有修改。先更新所有模型项，再更新模型。
		chnlModelMng.updateModel(bean, modelItems);
		log.info("修改 模型 成功:{}", bean.getName());
		return list();
	}

	public String delete() {
		try {
			if (id != null) {
				bean = chnlModelMng.deleteById(id);
				log.info("删除 模型 成功:{}", bean.getName());
			} else {
				for (ChnlModel b : chnlModelMng.deleteById(ids)) {
					log.info("删除 模型 成功:{}", b.getName());
				}
			}
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除");
			return SHOW_ERROR;
		}
		return list();
	}

	public String priorityUpdate() {
		for (int i = 0; i < wids.length; i++) {
			ChnlModel f = chnlModelMng.findById(wids[i]);
			f.setPriority(prioritys[i]);
			chnlModelMng.update(f);
		}
		return list();
	}

	public String addItem() {
		return "addItem";
	}

	public String saveItem() {
		// modelId必须存在。
		ChnlModel model = chnlModelMng.findById(modelId);
		String checkResult = checkModelItem(model);
		if (checkResult != null) {
			return checkResult;
		}
		modelItem.setModel(model);
		chnlModelItemMng.save(modelItem);
		model.getItems().add(modelItem);
		log.info("添加 模型项 成功:{}", modelItem.getName());
		id = modelId;
		return edit();
	}

	public String editItem() {
		modelItem = chnlModelItemMng.findById(id);
		return "editItem";
	}

	public String updateItem() {
		// modelId必须存在。
		ChnlModel model = chnlModelMng.findById(modelId);
		String checkResult = checkModelItem(model);
		if (checkResult != null) {
			return checkResult;
		}
		modelItem.setModel(model);
		// 防止整型无法别置空
		Updater updater = Updater.create(modelItem);
		updater.include(ChnlModelItem.PROP_INPUT_SIZE);
		updater.include(ChnlModelItem.PROP_INPUT_WIDTH);
		updater.include(ChnlModelItem.PROP_TEXTAREA_COLS);
		updater.include(ChnlModelItem.PROP_TEXTAREA_ROWS);
		chnlModelItemMng.updateByUpdater(updater);
		log.info("修改 模型项 成功:{}", modelItem.getName());
		id = modelId;
		return edit();
	}

	// 判断modelItem是否在权限范围之内，以免跨站点更新。判断方法：检查model的website。
	private String checkModelItem(ChnlModel model) {
		if (!model.getWebsite().getId().equals(getRootWebId())) {
			addActionError("不能跨站点更新模型项");
			log.warn("跨站点更新模型项website：{}；admin：{}。", getRootWeb().getDomain(),
					getAdmin().getLoginName());
			return SHOW_ERROR;
		} else {
			return null;
		}
	}

	public boolean validateSave() {
		if (hasErrors()) {
			return true;
		}
		bean.setWebsite(getRootWeb());
		return false;
	}

	public boolean validateEdit() {
		if (hasErrors()) {
			return true;
		}
		if (vldExist(id)) {
			return true;
		}
		if (vldWebsite(id, null)) {
			return true;
		}
		return false;
	}

	public boolean validateUpdate() {
		if (hasErrors()) {
			return true;
		}
		if (vldExist(bean.getId())) {
			return true;
		}
		if (vldWebsite(bean.getId(), null)) {
			return true;
		}
		bean.setWebsite(getRootWeb());
		return false;
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			return true;
		}
		ChnlModel po;
		if (id == null && (ids == null || ids.length <= 0)) {
			addActionError("ID不能为空");
			return true;
		} else {
			if (id != null) {
				ids = new Long[] { id };
			}
			for (Long id : ids) {
				po = chnlModelMng.findById(id);
				if (!po.getWebsite().getId().equals(getRootWebId())) {
					addActionError("不能删除其他站点数据");
					return true;
				}
			}
		}
		return false;
	}

	private boolean vldExist(Long id) {
		ChnlModel entity = chnlModelMng.findById(id);
		if (entity == null) {
			addActionError("模型不存在：" + id);
			return true;
		}
		return false;
	}

	private boolean vldWebsite(Long id, ChnlModel bean) {
		ChnlModel entity = chnlModelMng.findById(id);
		if (!entity.getWebsite().getId().equals(getRootWebId())) {
			addActionError("只能修改本站点数据：" + id);
			return true;
		}
		if (bean != null) {
			bean.setWebsite(getRootWeb());
		}
		return false;
	}

	@Autowired
	protected ChnlModelMng chnlModelMng;
	private ChnlModel bean;
	private List<ChnlModelItem> modelItems;
	private boolean isCopy = false;

	@Autowired
	private ChnlModelItemMng chnlModelItemMng;
	private ChnlModelItem modelItem;
	private Long modelId;
	private Long itemType;

	private Long[] wids;
	private int[] prioritys;

	public ChnlModel getBean() {
		return bean;
	}

	public void setBean(ChnlModel bean) {
		this.bean = bean;
	}

	public List<ChnlModelItem> getModelItems() {
		return modelItems;
	}

	public void setModelItems(List<ChnlModelItem> modelItems) {
		this.modelItems = modelItems;
	}

	public boolean isCopy() {
		return isCopy;
	}

	public void setCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}

	public ChnlModelItem getModelItem() {
		return modelItem;
	}

	public void setModelItem(ChnlModelItem modelItem) {
		this.modelItem = modelItem;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getItemType() {
		return itemType;
	}

	public void setItemType(Long itemType) {
		this.itemType = itemType;
	}

	public Long[] getWids() {
		return wids;
	}

	public void setWids(Long[] wids) {
		this.wids = wids;
	}

	public int[] getPrioritys() {
		return prioritys;
	}

	public void setPrioritys(int[] prioritys) {
		this.prioritys = prioritys;
	}
}
