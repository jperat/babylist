package com.jperat.babylist.controller;

import com.jperat.babylist.entity.User;
import com.jperat.babylist.exception.ResourceNotFoundException;
import com.jperat.babylist.form.ForgotPasswordForm;
import com.jperat.babylist.form.ResetPasswordForm;
import com.jperat.babylist.repository.UserRepository;
import com.jperat.babylist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping(value = "/login", name = "login_")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create-password", method = RequestMethod.GET, name="create_password")
    public String createPassword(@RequestParam(name="token", required = true) String token, Model model) {
        User user = userRepository.findOneByToken(token);
        if (user == null || user.getTokenExpiryDate().before(new Date())) {
            return "redirect:/";
        }
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setToken(token);
        model.addAttribute("user", user);
        model.addAttribute("resetPasswordForm", resetPasswordForm);
        return "login/create_password";
    }

    @RequestMapping(value = "/create-password", method = RequestMethod.POST, name="create_password")
    public String createPassword(@Valid ResetPasswordForm resetPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/create_password";
        }
        User user = userRepository.findOneByEmail(resetPasswordForm.getEmail());
        user.setEnable(true);
        userService.updatePassword(user, resetPasswordForm.getPassword());
        return "redirect:/";
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.GET, name="reset_password")
    public String resetPassword(
            @RequestParam(name="token", required = true) String token,
            Model model,
            HttpServletRequest request
    ) {
        User user = userRepository.findOneByToken(token);
        if (user == null || user.getTokenExpiryDate().before(new Date())) {
            return "redirect:/";
        }
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setToken(token);
        model.addAttribute("user", user);
        model.addAttribute("resetPasswordForm", resetPasswordForm);
        return "login/reset_password";
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST, name="create_password")
    public String resetPassword(@Valid ResetPasswordForm resetPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/reset_password";
        }
        User user = userRepository.findOneByEmail(resetPasswordForm.getEmail());
        user.setEnable(true);
        userService.updatePassword(user, resetPasswordForm.getPassword());
        return "redirect:/";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String forgotPassword(Model model) {
        model.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
        return "login/forgot_password";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String forgotPassword(@Valid ForgotPasswordForm forgotPasswordForm, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            model.addAttribute("success", "security.login.forgot_password.success");
            userService.forgotPassword(forgotPasswordForm.getEmail());
        }
        return "login/forgot_password";
    }

}
