package com.jperat.babylist.controller;

import com.jperat.babylist.entity.Availability;
import com.jperat.babylist.entity.Item;
import com.jperat.babylist.repository.AvailabilityRepository;
import com.jperat.babylist.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@Secured("ROLE_ADMIN")
@Controller
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private StoreRepository storeRepository;

    @RequestMapping(path = {"/availability/{availability}/edit", "/availability/{item}/add"}, method = RequestMethod.GET)
    public String edit(@ModelAttribute Item item, @ModelAttribute Availability availability, Model model) {
        model.addAttribute("allStores", storeRepository.findAll());
        if (availability.getItem() == null) {
            availability.setItem(item);
        }
        return "availability/edit";
    }

    @RequestMapping(path = {"/availability/{availability}/edit", "/availability/add"}, method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute Availability availability, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (bindingResult.hasErrors()) {
            model.addAttribute("allStores", storeRepository.findAll());
            return "availability/edit";
        }
        availabilityRepository.save(availability);
        flashMessages.add(new String[] {"success","flash_message.success.saved"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/item/"+ availability.getItem().getId();
    }

    @RequestMapping(path={"/availability/{availability}/delete"}, name = "availability_delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute Availability availability, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        Integer id = availability.getId();
        availabilityRepository.delete(availability);
        flashMessages.add(new String[] {"success","flash_message.success.deleted"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/item/"+id;
    }
}
