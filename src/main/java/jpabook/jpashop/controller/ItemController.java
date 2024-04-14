package jpabook.jpashop.controller;

import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        BookForm bookForm = new BookForm();

        model.addAttribute("form", bookForm);

        return "items/createItemForm";

    }

    @PostMapping(value = "/items/new")
    public String create(BookForm bookForm) {

        Book book = new Book();

        book.setId(bookForm.getId());
        book.setName(bookForm.getName());
        book.setAuthor(bookForm.getAuthor());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";

    }

    /**
     * 상품 목록
     */
    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 상품 수정폼
     */
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Book book = (Book) itemService.findOne(itemId);
        BookForm bookForm = new BookForm();

        bookForm.setId(book.getId());
        bookForm.setName(book.getName());
        bookForm.setPrice(book.getPrice());
        bookForm.setStockQuantity(book.getStockQuantity());
        bookForm.setAuthor(book.getAuthor());
        bookForm.setIsbn(book.getIsbn());

        model.addAttribute("form", bookForm);

        return "items/updateItemForm";

    }

    /**
     * 상품 수정
     */
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form) {
        Book book = new Book();

        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";
    }

}
