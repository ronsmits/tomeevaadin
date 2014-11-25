/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tomeevaadin.ui;

import com.example.tomeevaadin.application.BookService;
import com.example.tomeevaadin.entities.Book;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.ejb.EJB;

/**
 *
 * @author Ron
 */
@Title("Book UI")
@CDIUI
public class BookUI extends UI{
    
    @EJB private BookService bookService;
    
    VerticalLayout verticalLayout;
    ObjectProperty<String> stringModel = new ObjectProperty<String>("");
    BeanItemContainer<Book> bookContainer = new BeanItemContainer<Book>(Book.class);
        
    
    @Override
    protected void init(VaadinRequest request) {
        Label label = new Label("welcome");
        verticalLayout = new VerticalLayout();
        //verticalLayout.setSizeFull();
        verticalLayout.addComponent(label);
        setContent(verticalLayout);
        verticalLayout.addComponent(addInputFields());
        addInputFields();
        addTable();
    }

    private HorizontalLayout addInputFields() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(new Label("Enter a book"));
        TextField field = new TextField(stringModel);
        Button button = new Button("press this to save");
        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (bookService == null) {
                    System.out.println("uho, ejb is null");
                }
                Notification.show("button is pressed "+stringModel.getValue());
                Book book = new Book();
                book.setBookTitle(stringModel.getValue());
                bookService.addBook(book);
                updateBeanContainer(); 
                
                
            }
        });
        hl.addComponent(field);
        hl.addComponent(button);
        return hl;
    }

    private void updateBeanContainer() {
        bookContainer.removeAllItems();
            bookContainer.addAll(bookService.getAllBooks());
    }
    private void addTable() {
        Table t = new Table("alle boeken", bookContainer);
        verticalLayout.addComponent(t);
                
    }
    
}
