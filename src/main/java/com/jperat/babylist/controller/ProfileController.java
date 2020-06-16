package com.jperat.babylist.controller;

import com.jperat.babylist.entity.User;
import com.jperat.babylist.form.ChangePasswordForm;
import com.jperat.babylist.form.ProfileForm;
import com.jperat.babylist.repository.UserRepository;
import com.jperat.babylist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", name = "profile_index", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {
        User user = userRepository.findOneByEmail(principal.getName());
        model.addAttribute("user", user);
        return "profile/index";
    }

    @RequestMapping(value = "/profile/edit", name = "profile_edit", method = RequestMethod.GET)
    public String edit(Model model, Principal principal) {
        User user = userRepository.findOneByEmail(principal.getName());
        model.addAttribute("form", new ProfileForm(user));
        return "profile/edit";
    }

    @RequestMapping(value = "/profile/edit", name = "profile_edit_post", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute ProfileForm form,  BindingResult bindingResult, RedirectAttributes redirectAttributes,Principal principal) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (bindingResult.hasErrors()) {
            flashMessages.add(new String[] {"danger","flash_message.error.an_error_happened"});
            redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
            return "profile/edit";
        }
        User user = userRepository.findOneByEmail(principal.getName());
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        userRepository.save(user);
        flashMessages.add(new String[] {"success","flash_message.success.saved"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/profile";
    }

    @RequestMapping(value = "/profile/change-password", name = "profile_change_password", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute("changePassword", new ChangePasswordForm());
        return "profile/change_password";
    }

    @RequestMapping(value = "/profile/change-password", name = "profile_change_password", method = RequestMethod.POST)
    public String changerPassword(@Valid @ModelAttribute ChangePasswordForm changePassword, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (bindingResult.hasErrors()) {
            flashMessages.add(new String[] {"danger","flash_message.error.an_error_happened"});
            redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
            return "profile/change_password";
        }
        User user = userRepository.findOneByEmail(principal.getName());
        userService.updatePassword(user, changePassword.getPassword());
        flashMessages.add(new String[] {"success","flash_message.success.saved"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/profile";
    }
}
