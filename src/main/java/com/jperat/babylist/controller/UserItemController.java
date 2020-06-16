package com.jperat.babylist.controller;

import com.jperat.babylist.entity.Item;
import com.jperat.babylist.entity.User;
import com.jperat.babylist.entity.UserItem;
import com.jperat.babylist.entity.UserItemId;
import com.jperat.babylist.repository.UserItemRepository;
import com.jperat.babylist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class UserItemController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserItemRepository userItemRepository;

    @RequestMapping(path = "/user-item/get-form/{item}", method = RequestMethod.GET)
    public String getForm(@ModelAttribute Item item, Model model, Principal principal) {
        User user = userRepository.findOneByEmail(principal.getName());
        UserItem userItem = userItemRepository.findOneByUserAndItem(user, item);
        if (userItem == null) {
            userItem = new UserItem(user, item);
        }
        model.addAttribute("userItem", userItem);
        return "user_item/form";
    }

    @RequestMapping(path = "/user-item/save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute UserItem userItem, BindingResult bindingResult) {
        if (userItem.getId() == null) {
        }
        userItem.setId(new UserItemId(userItem.getUser().getId(), userItem.getItem().getId()));
        if (bindingResult.hasErrors()) {
            return "user_item/form";
        }
        userItemRepository.save(userItem);
        return "user_item/success";
    }
}
