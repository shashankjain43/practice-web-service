/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Mar 1, 2011
 *  @author DESKTOP
 */
package com.snapdeal.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.snapdeal.ums.admin.task.ITaskService;
import com.snapdeal.ums.core.entity.TaskParameter;
import com.snapdeal.ums.core.entity.UmsTask;

@Controller
public class RecurrentTaskController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private static final Logger LOG = LoggerFactory.getLogger(RecurrentTaskController.class);

    @Autowired
    private ITaskService        tasksService;

    @RequestMapping("/admin/taskAdmin")
    public String taskadmin(ModelMap modelMap) {
        List<UmsTask> tasks = tasksService.getAllTasks();
        modelMap.addAttribute("tasks", tasks);
        return "admin/task/taskAdmin";
    }

    @RequestMapping("admin/runTask/{taskId}")
    public String runTask(@PathVariable Integer taskId) {
        if (taskId != null) {
            tasksService.runRecurrentTask(taskId);
        }

        return "redirect:/admin/taskAdmin";
    }

    @RequestMapping("admin/editTask/{taskId}")
    public String editTask(@PathVariable Integer taskId, ModelMap modelMap) {
        UmsTask task1 = tasksService.getRecurrentTaskById(taskId);
        modelMap.addAttribute("task1", task1);
        return "admin/task/editTask";
    }

    @RequestMapping("/admin/updateTask")
    public String updateTask(@ModelAttribute("task1") @Valid UmsTask task1, BindingResult result, ModelMap modelMap) throws Exception {
        if (result.hasErrors()) {
            modelMap.addAttribute("task", task1);
            return "admin/task/editTask";
        }
        
        for (TaskParameter taskParameter : task1.getumsTaskParameterList()) {
            taskParameter.setUmsTask(task1);
        }

        task1.getTaskParameters().addAll(task1.getumsTaskParameterList());
        LOG.info("Size : " + task1.getTaskParameters().size());
        tasksService.updateTask(task1);
        return "redirect:/admin/taskAdmin";
        
    }

}
