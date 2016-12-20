package com.zl.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zl.cobranca.model.StatusTitulo;
import com.zl.cobranca.model.Titulo;
import com.zl.cobranca.repository.Titulos;
import com.zl.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulo")
public class TituloController {
	
	private  static final String CADASTRO_VIEW = "CadastroTitulo";
	
	@Autowired
	private Titulos titulos;
	
	@Autowired
	private CadastroTituloService cadastroTituloService;

	@RequestMapping("/novo")
	public ModelAndView novo(){
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors error, RedirectAttributes attribute){
		
		if(error.hasErrors()){
			return CADASTRO_VIEW;
		}
		try{
			cadastroTituloService.salvar(titulo);
			attribute.addFlashAttribute("mensagem", "Titulo cadastrado com sucesso!");
			return "redirect:/titulo/novo";
		}catch(IllegalArgumentException e){
			error.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
			}
		}
	
	@RequestMapping
	public ModelAndView pesquisa(){
		ModelAndView mv = new ModelAndView("PesquisaTitulo");
		List<Titulo> todosTitulos = titulos.findAll();
		mv.addObject("titulos", todosTitulos);
		
		return mv;
	}
	
	@RequestMapping("/{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo){
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String exclusao(@PathVariable Long codigo, RedirectAttributes attribute){
		attribute.addFlashAttribute("mensagem", "Titulo excluído com Sucesso!");
		cadastroTituloService.deletar(codigo);
		return "redirect:/titulo";
	}
	
	
	@ModelAttribute("statusTitulo")
	public List<StatusTitulo> statusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
}
