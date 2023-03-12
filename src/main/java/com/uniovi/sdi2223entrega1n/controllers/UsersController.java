package com.uniovi.sdi2223entrega1n.controllers;

import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.services.RolesService;
import com.uniovi.sdi2223entrega1n.services.SecurityService;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import com.uniovi.sdi2223entrega1n.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SignUpFormValidator signUpFormValidator;
//    @Autowired
//    private EditUserFormValidator editUserFormValidator;
    @Autowired
    private RolesService rolesService;
    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("usersList", usersService.getUsers() );
        return "user/list :: tableUsers";
    }
    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        return "user/add";
    }
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }
    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }
    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @PostMapping("/users/deleteSelected")
    public String deleteUsers(@RequestParam(value = "userIds", required = false) Long[] userIds, Principal principal) {
        String currentEmail=principal.getName();
        User currentUser=usersService.getUserByEmail(currentEmail);
        Long id=currentUser.getId();
        if (userIds != null && userIds.length > 0) {
            for(int i=0; i<userIds.length; i++){
                if(userIds[i]!=id)
                    usersService.deleteUser(userIds[i]);
            }

        }
        return "redirect:/user/list";
    }
    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        if(!model.containsAttribute("user")){
            User user = usersService.getUser(id);
            model.addAttribute("user", user);
        }
        return "user/edit";
    }
//    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
//    public String setEdit(@PathVariable Long id, @Validated User user, BindingResult result, Model model) {
//        User originalUser = usersService.getUser(id);
//
//        editUserFormValidator.validate(user,result);
//        if(result.hasErrors()){
//            model.addAttribute("user", user);
//            return "user/edit";
//        }
//
//        originalUser.setDni(user.getDni());
//        originalUser.setName(user.getName());
//        originalUser.setLastName(user.getLastName());
//        usersService.addUser(originalUser);
//        return "redirect:/user/details/" + id;
//    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user,result);
        if(result.hasErrors())
            return "signup";
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByEmail(dni);
        //model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
}
