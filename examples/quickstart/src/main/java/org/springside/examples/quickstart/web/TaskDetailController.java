package org.springside.examples.quickstart.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.examples.quickstart.entity.Task;
import org.springside.examples.quickstart.service.TaskManager;

/**
 * 使用@ModelAttribute, 实现Struts2 Preparable二次绑定的效果,先根据url中的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
 * 因为@ModelAttribute被默认执行, 而其他的action url中并没有${id}，所以需要独立出一个Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/task/")
public class TaskDetailController {

	@Autowired
	private TaskManager taskManager;

	@RequestMapping(value = "update/{id}")
	public String updateForm(Model model) {
		return "taskForm";
	}

	@RequestMapping(value = "save/{id}")
	public String save(@ModelAttribute("task") Task task, RedirectAttributes redirectAttributes) {
		taskManager.saveTask(task);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/task/";
	}

	@ModelAttribute("task")
	public Task getTask(@PathVariable("id") Long id) {
		return taskManager.getTask(id);
	}
}
