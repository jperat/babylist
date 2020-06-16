package com.jperat.babylist.controller;

import com.jperat.babylist.entity.User;
import com.jperat.babylist.exception.UserAllreadyExistException;
import com.jperat.babylist.model.Pagination;
import com.jperat.babylist.repository.RoleRepository;
import com.jperat.babylist.repository.UserRepository;
import com.jperat.babylist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@Secured("ROLE_ADMIN")
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(path="/user", name = "user_index", method = RequestMethod.GET)
    public String index(@RequestParam(name="page", required=false, defaultValue="1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<User> users = userRepository.findAll(pageable);
        model.addAttribute("users", users);
        model.addAttribute("pagination", new Pagination(users));
        return "user/index";
    }


    @RequestMapping(path={"/user/{user}"}, name = "user_show", method = RequestMethod.GET)
    public String show(@ModelAttribute User user) {
        return "user/show";
    }

    @RequestMapping(path={"/user/{user}/edit", "/user/add"}, name = "user_edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute User user, Model model) {
        model.addAttribute("allRoles", roleRepository.findAll());
        return "user/edit";
    }

    @RequestMapping(path={"/user/{user}/edit", "/user/add"}, name = "user_edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (bindingResult.hasErrors()) {
            flashMessages.add(new String[] {"danger","flash_message.error.an_error_happened"});
            redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
            model.addAttribute("allRoles", roleRepository.findAll());
            return "user/edit";
        }
        if (user.getId() == null) {
            try {
                userService.createNewUser(user);
            } catch (UserAllreadyExistException e) {
                flashMessages.add(new String[] {"error","flash_message.error.user_allready_exist"});
                return "user/edit";
            }
        } else {
            userRepository.save(user);
        }
        flashMessages.add(new String[] {"success","flash_message.success.saved"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/user";
    }

    @RequestMapping(path={"/user/{user}/delete"}, name = "user_delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (user.getUserItems().isEmpty()) {
            userRepository.delete(user);
            flashMessages.add(new String[] {"success","flash_message.success.deleted"});
        } else {
            flashMessages.add(new String[] {"danger","flash_message.error.you_can_not_delete"});
        }
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/user";
    }
}
