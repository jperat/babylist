package com.jperat.babylist.controller;

import com.jperat.babylist.entity.Store;
import com.jperat.babylist.model.Pagination;
import com.jperat.babylist.repository.StoreRepository;
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
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @RequestMapping(path="/store", name = "category_index", method = RequestMethod.GET)
    public String index(@RequestParam(name="page", required=false, defaultValue="1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Store> stores = storeRepository.findAll(pageable);
        model.addAttribute("stores", stores);
        model.addAttribute("pagination", new Pagination(stores));
        return "store/index";
    }

    @RequestMapping(path={"/store/{store}/edit", "/store/add"}, name = "store_edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute Store store) {
        return "store/edit";
    }

    @RequestMapping(path={"/store/{store}/edit", "/store/add"}, name = "store_edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute Store store, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (bindingResult.hasErrors()) {
            flashMessages.add(new String[] {"danger","flash_message.error.an_error_happened"});
            redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
            return "store/edit";
        }
        storeRepository.save(store);
        flashMessages.add(new String[] {"success","flash_message.success.saved"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/store";
    }

    @RequestMapping(path={"/store/{store}/delete"}, name = "store_delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute Store store, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (store.getAvailabilities().isEmpty()) {
            storeRepository.delete(store);
            flashMessages.add(new String[] {"success","flash_message.success.deleted"});
        } else {
            flashMessages.add(new String[] {"danger","flash_message.error.you_can_not_delete"});
        }
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/store";
    }

}
