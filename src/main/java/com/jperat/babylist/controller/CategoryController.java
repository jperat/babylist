package com.jperat.babylist.controller;

import com.jperat.babylist.entity.Category;
import com.jperat.babylist.model.Pagination;
import com.jperat.babylist.repository.CategoryRepository;
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
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(path="/category", name = "category_index", method = RequestMethod.GET)
    public String index(@RequestParam(name="page", required=false, defaultValue="1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Category> categories = categoryRepository.findAll(pageable);
        model.addAttribute("categories", categories);
        model.addAttribute("pagination", new Pagination(categories));
        return "category/index";
    }

    @RequestMapping(path={"/category/edit/{category}", "/category/add"}, name = "category_edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute Category category) {
        return "category/edit";
    }

    @RequestMapping(path={"/category/edit/{category}", "/category/add"}, name = "category_edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (bindingResult.hasErrors()) {
            flashMessages.add(new String[] {"danger","flash_message.error.an_error_happened"});
            redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
            return "category/edit";
        }
        categoryRepository.save(category);
        flashMessages.add(new String[] {"success","flash_message.success.saved"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/category";
    }

    @RequestMapping(path={"/category/delete/{category}"}, name = "category_delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (category.getItems().isEmpty()) {
            categoryRepository.delete(category);
            flashMessages.add(new String[] {"success","flash_message.success.deleted"});
        } else {
            flashMessages.add(new String[] {"danger","flash_message.error.you_can_not_delete"});
        }
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/category";
    }

}
