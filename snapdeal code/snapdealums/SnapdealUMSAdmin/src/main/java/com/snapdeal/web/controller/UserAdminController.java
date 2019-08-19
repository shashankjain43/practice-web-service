/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 27-Aug-2010
 *  @author Karan Sachdeva
 */
package com.snapdeal.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.snapdeal.ums.admin.startup.IAdminStartupService;
import com.snapdeal.ums.cache.services.IUserCacheService;
import com.snapdeal.ums.core.entity.Role;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.server.services.IUserRoleService;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.UserRoleServiceImpl;
import com.snapdeal.web.controller.forms.UserAdminForm;
import com.snapdeal.web.utils.WebContextUtils;

@Controller
@RequestMapping("/admin")
public class UserAdminController {

	@Autowired
	private IUserServiceInternal userService;
	@Autowired
	private IUserRoleService roleService;

	@Autowired
	private IAdminStartupService umsAdminStartupService;

	@Autowired
	private IUserCacheService userCacheService;
	
	private final String GRANT="grant";
	private final String REVOKE="revoke";

	@RequestMapping("/userAdmin/fetchUserRole")
	public String fetchUserRole(@RequestParam("user.email") String email,
			ModelMap modelMap) {
		UserAdminForm userAdminForm;

		User user = userService.getUserByEmail(email);

		if (user != null) {
			userAdminForm = new UserAdminForm(user);
		} else {
			userAdminForm = new UserAdminForm();
			modelMap.addAttribute("message", "Invalid Email ID");

		}

		modelMap.addAttribute("userAdminForm", userAdminForm);

		return "admin/user/userAdmin";
	}

	@RequestMapping("/userAdmin")
	public String homeUserAdmin(ModelMap modelMap) {

		UserAdminForm userAdminForm = new UserAdminForm();
		modelMap.addAttribute("userAdminForm", userAdminForm);
		modelMap.addAttribute("userRoles", userAdminForm.getuserRoles());
		return "admin/user/userAdmin";

	}

	@RequestMapping("/roleAdmin")
	public String homeRoleAdmin(ModelMap modelMap) {

		UserAdminForm userAdminForm = new UserAdminForm();
		modelMap.addAttribute("userAdminForm", userAdminForm);
		modelMap.addAttribute("userRoles", userAdminForm.getuserRoles());
		return "admin/user/roleAdmin";

	}

	@RequestMapping("/roleAdmin/addRole")
	public String addNewRole(@RequestParam("code") String code,
			@RequestParam("description") String description) {
		Role dbVersion = roleService.getRoleByCode(code);
		if (dbVersion == null)
			roleService.addRole(code, description);
		umsAdminStartupService.loadRoles();
		return "redirect:/admin/roleAdmin";
	}

	@RequestMapping("/userAdmin/deleteUserRole")
	public String deleteUserRole(@RequestParam("id") int userRoleID,
			@RequestParam("email") String email, ModelMap modelMap) {

		String userRoleGiver = WebContextUtils.getCurrentUserEmail();
		String roleCode=userService.getUserRoleById(userRoleID).getRole().getCode();
		userService.addUserRoleAudit(roleCode, userRoleGiver, email.trim()
				.toLowerCase(), REVOKE);
		userService.deleteUserRoleById(userRoleID);
		// Evict userSRO from cache
		userCacheService.deleteUserSROByEmail(email.trim().toLowerCase());

		modelMap.addAttribute("message", "User Role deleted successfully");
		return "redirect:/admin/userAdmin/fetchUserRole?user.email=" + email;

	}

	@RequestMapping("/userAdmin/addData/addRole")
	public String addRoleZoneMap(HttpServletRequest request,
			@RequestParam("userRoleEmail") String email, ModelMap model) {

		String userRole = request.getParameter("drpdwnUserRole");

		User user = userService.getUserByEmail(email);

		if (user == null) {
			model.addAttribute("message", "No user with this Email ID exists");
			return "/admin/userAdmin";
		}

		UserRole role = user.getUserRole(userRole);

		if (role == null) {
			role = new UserRole(user, roleService.getRoleByCode(userRole));
			role = userService.createUserRole(role);
			if(role == null){
				model.addAttribute("message", "Not authorised for requested role!");
				return "redirect:/admin/userAdmin/fetchUserRole?user.email=" + email;
			}
			String userRoleGiver = WebContextUtils.getCurrentUserEmail();
			userService.addUserRoleAudit(role.getRole().getCode(), userRoleGiver, email.trim()
					.toLowerCase(), GRANT);
		}

		// Evict userSRO from cache
		userCacheService.deleteUserSROByEmail(email.trim().toLowerCase());

		return "redirect:/admin/userAdmin/fetchUserRole?user.email=" + email;
	}

}
