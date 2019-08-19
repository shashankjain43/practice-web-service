package com.snapdeal.web.controller;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.snapdeal.ums.admin.startup.IAdminStartupService;
import com.snapdeal.ums.services.accesscontrol.IAccessControlService;

@Controller
public class AdminController {


    @Autowired
    private IAccessControlService        accessControlService;
    @Autowired
    private IAdminStartupService adminstartupService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // default value is 256, in case of more values it will throw ArrayOutOfBound Exception. so setting the default value to 1024
        // setAutoGrowCollectionLimit should always be the first line in init binder
        binder.setAutoGrowCollectionLimit(1024);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/admin")
    public String adminDasboard(ModelMap modelMap) {
        return "/admin/adminReport";
    }
    
    @RequestMapping("/admin/addAccessControl")
    public String accessController(ModelMap modelMap) {
        return "/admin/addAccessControl";
    }
  
    @RequestMapping("/admin/addAccessControl/addRoles")
    public String addRoles(@RequestParam("pattern") String pattern, @RequestParam("roles") String roles, ModelMap map) {
        accessControlService.addControl(pattern, roles);
        adminstartupService.loadAccessControls();
        map.addAttribute("test", pattern);
        map.addAttribute("roles", roles);
        return "/admin/addAccessControl";
    }

    @RequestMapping("/admin/addAccessControl/fetchRoles")
    public String fetchRoles(@RequestParam("pattern") String pattern, ModelMap map) {
        String allRoles = accessControlService.getUserRoleByPattern(pattern);
        map.addAttribute("roles", allRoles);
        map.addAttribute("test", pattern);
        return "/admin/addAccessControl";
    }

}
