package com.zl.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.zl.cobranca.model.StatusTitulo;
import com.zl.cobranca.model.Titulo;
import com.zl.cobranca.repository.Titulos;
import com.zl.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo){
		try{
			titulos.save(titulo);
		}catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data invalido");
		}
	}
	
	public void deletar(Long codigo){
		titulos.delete(codigo);
	}

	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.PAGO);
		titulos.save(titulo);
		
		return StatusTitulo.PAGO.getDescricao().toUpperCase();
	}
	
	public List<Titulo> pesquisaTitulos(TituloFilter filtro){
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		List<Titulo> todosTitulos = titulos.findByDescricaoContaining(descricao);
		return todosTitulos;
	}
}
