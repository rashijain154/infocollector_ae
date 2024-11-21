package com.avirantenterprises.infocollector.controller;
import com.avirantenterprises.infocollector.model.Form;
import com.avirantenterprises.infocollector.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormBuilderController {
    @Autowired
    private FormRepository formRepository;
    @GetMapping("/formBuilder")
    public String formBuilder(Model model){
        model.addAttribute("form", new Form());
        return "formBuilder";
    }
    @PostMapping("/saveForm")
    public String saveForm(@ModelAttribute("form") Form form){
        formRepository.save(form);
        return "redirect:/forms";
    }
    @GetMapping("/forms")
    public String showForms(Model model){
        model.addAttribute("forms", formRepository.findAll());
        return "forms";
    }

    @GetMapping("/editForm/{id}")
    public String updateData(Model model, @PathVariable long id)
    {
        Form form =formRepository.findById(id).orElse(null);
        if(form!=null)
        {
            model.addAttribute("form",form);
            return "redirect:/formBuilder?id=" + form.getId();
        }
        return "redirect:/forms";
    }

    @GetMapping("/deleteForm/{id}")
        public String deleteForm(@PathVariable long id)
        {
            formRepository.deleteById(id);
            return "redirect:/forms";
        }
}
