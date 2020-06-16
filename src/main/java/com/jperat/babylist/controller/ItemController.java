package com.jperat.babylist.controller;

import com.jperat.babylist.entity.*;
import com.jperat.babylist.model.Pagination;
import com.jperat.babylist.repository.CategoryRepository;
import com.jperat.babylist.repository.ItemRepository;
import com.jperat.babylist.repository.UserItemRepository;
import com.jperat.babylist.repository.UserRepository;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserItemRepository userItemRepository;

    @RequestMapping(path={"/item"}, name = "item_index", method= RequestMethod.GET)
    public String index(@RequestParam(name="page", required=false, defaultValue="1") int page, Model model, Principal principal, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Item> items;
        Iterable<Category> categories = categoryRepository.findAll();
        HttpSession session = request.getSession();
        if (request.getParameter("category") != null) {
            try {
                Integer id = Integer.parseInt((String) request.getParameter("category"));
                session.setAttribute("item_index_filter_category", id);
            } catch (NumberFormatException e) {
                session.setAttribute("item_index_filter_category", null);
            }
        }
        if (session.getAttribute("item_index_filter_category") == null) {
            items = itemRepository.findAll(pageable);
        } else {
            items = itemRepository.findAllByCategoryId(pageable, (Integer) session.getAttribute("item_index_filter_category"));
        }
        model.addAttribute("items", items);
        model.addAttribute("pagination", new Pagination(items));
        model.addAttribute("userEmail", principal.getName());
        model.addAttribute("categories", categories);
        return "item/index";
    }

    @RequestMapping(path="/item/my-items", name = "item_my_item", method= RequestMethod.GET)
    public String myItems(@RequestParam(name="page", required=false, defaultValue="1") int page, Model model, Principal principal) {
        User user = userRepository.findOneByEmail(principal.getName());
        Page<Item> items = itemRepository.findAllByUser(PageRequest.of(page - 1, 10), user);
        model.addAttribute("items", items);
        model.addAttribute("pagination", new Pagination(items));
        return "item/my_items";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path="/item/selected", name = "item_selected", method= RequestMethod.GET)
    public String selected(@RequestParam(name="page", required=false, defaultValue="1") int page, Model model) {
        Page<UserItem> userItems = userItemRepository.findAll(PageRequest.of(page - 1, 10));
        model.addAttribute("userItems", userItems);
        model.addAttribute("pagination", new Pagination(userItems));
        return "item/selected";
    }

    @RequestMapping(path={"/item/my-items/{item}/delete"}, name = "item_my_item_delete", method = RequestMethod.GET)
    public String myItemsDelete(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Principal principal) {
        ArrayList flashMessages = new ArrayList<String[]>();
        User user = userRepository.findOneByEmail(principal.getName());
        UserItem userItem = userItemRepository.findOneByUserAndItem(user, item);
        if (userItem != null) {
            userItemRepository.delete(userItem);
            flashMessages.add(new String[] {"success","flash_message.success.deleted"});
        } else {
            flashMessages.add(new String[] {"danger","flash_message.error.you_can_not_delete"});
        }
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/item/my-items";
    }

    @RequestMapping(path = "/item/{item}", name = "item_show", method = RequestMethod.GET)
    public String show(@ModelAttribute Item item, Model model, Principal principal) {
        model.addAttribute("userEmail", principal.getName());
        return "item/show";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path={"/item/{item}/edit", "/item/add"}, name = "item_edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute Item item, Model model) {
        model.addAttribute("allCategories", categoryRepository.findAll());
        return "item/edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path={"/item/{item}/edit", "/item/add"}, name = "item_edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (bindingResult.hasErrors()) {
            flashMessages.add(new String[] {"danger","flash_message.error.an_error_happened"});
            redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
            return "item/edit";
        }
        itemRepository.save(item);
        flashMessages.add(new String[] {"success","flash_message.success.saved"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/item";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path={"/item/{item}/delete"}, name = "item_delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (item.getAvailabilities().isEmpty()) {
            itemRepository.delete(item);
            flashMessages.add(new String[] {"success","flash_message.success.deleted"});
        } else {
            flashMessages.add(new String[] {"danger","flash_message.error.you_can_not_delete"});
        }
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/item";
    }
}
