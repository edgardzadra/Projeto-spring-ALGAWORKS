package com.zl.cobranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.zl.cobranca.model.Titulo;
import com.zl.cobranca.repository.Titulos;

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
}
