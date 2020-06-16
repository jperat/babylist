package com.jperat.babylist.controller;

import com.jperat.babylist.entity.Card;
import com.jperat.babylist.model.Pagination;
import com.jperat.babylist.repository.CardRepository;
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
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(path="/card", name = "category_index", method = RequestMethod.GET)
    public String index(@RequestParam(name="page", required=false, defaultValue="1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Card> cards = cardRepository.findAll(pageable);
        model.addAttribute("cards", cards);
        model.addAttribute("pagination", new Pagination(cards));
        return "card/index";
    }

    @RequestMapping(path={"/card/{card}/edit", "/card/add"}, name = "card_edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute Card card, Model model) {
        String[] locales = {"en", "fr", "it"};
        model.addAttribute("locales", locales);
        return "card/edit";
    }

    @RequestMapping(path={"/card/{card}/edit", "/card/add"}, name = "card_edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute Card card, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        ArrayList flashMessages = new ArrayList<String[]>();
        if (bindingResult.hasErrors()) {
            flashMessages.add(new String[] {"danger","flash_message.error.an_error_happened"});
            redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
            String[] locales = {"en", "fr", "it"};
            model.addAttribute("locales", locales);
            return "card/edit";
        }
        cardRepository.save(card);
        flashMessages.add(new String[] {"success","flash_message.success.saved"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/card";
    }

    @RequestMapping(path={"/card/{card}/delete"}, name = "card_delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute Card card, RedirectAttributes redirectAttributes) {
        ArrayList flashMessages = new ArrayList<String[]>();
        cardRepository.delete(card);
        flashMessages.add(new String[] {"success","flash_message.success.deleted"});
        redirectAttributes.addFlashAttribute("flashMessages", flashMessages);
        return "redirect:/card";
    }
}
