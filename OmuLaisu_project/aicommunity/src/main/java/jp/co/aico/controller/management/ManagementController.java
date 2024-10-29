package jp.co.aico.controller.management;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.aico.bean.ManagementBean;
import jp.co.aico.entity.ManagementEntity;
import jp.co.aico.form.Managementform;
import jp.co.aico.repository.ManagementRepository;

@Controller
public class ManagementController {
	@Autowired
	ManagementRepository repository;
	/**
	 * 入力フォーム
	 * @param manaId
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping("user/update/input/{manaId}")
	public String kousin(@PathVariable Integer manaId, Model model) {
		ManagementEntity Entity = repository.getReferenceById(manaId);
		ManagementBean bean = new ManagementBean();
		BeanUtils.copyProperties(Entity, bean);
		model.addAttribute("up", bean);
		return "";

	}
	@RequestMapping()
	public String check(Managementform form) {
		
		return null;
		
}
/**
 * 更新完了処理
 * @param manaId
 * @param Managementform
 * @return
 */
	@RequestMapping("user/update/complete/{manaId}")
	public String update(@PathVariable Integer manaId ,  Managementform form) {
		 ManagementEntity Entity = repository.getReferenceById(manaId);
		BeanUtils.copyProperties(form, Entity, "manaId");
		Entity = repository.save(Entity);
		return "";
	}
}
