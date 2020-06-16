package com.jperat.babylist.controller;

import com.jperat.babylist.entity.Card;
import com.jperat.babylist.model.Pagination;
import com.jperat.babylist.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class IndexController {

    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(path="/", method = RequestMethod.GET)
    public String index(@RequestParam(name="page", required=false, defaultValue="1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Locale locale = LocaleContextHolder.getLocale();
        Page<Card> cards = cardRepository.findAllByLocale(pageable, locale.getLanguage());
        model.addAttribute("cards", cards);
        model.addAttribute("pagination", new Pagination(cards));
        return "index/index";
    }
}
