package com.batch.marcas.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.marcas.model.CompositeMarcaEmpleado;
import com.batch.marcas.model.MarcaCalendario;
import com.batch.marcas.model.MarcasProcesadas;
import com.batch.marcas.repositorio.MarcaCalendarioJPA;
import com.batch.marcas.repositorio.ProcesarMarcaJpa;

public class MarcaProcesadaItemWriter implements ItemWriter<MarcasProcesadas>{
	
	@Autowired
    private ProcesarMarcaJpa respository;
	
	@Autowired
	private MarcaCalendarioJPA daoMarcaCalendario;

	@Override
	public void write(List<? extends MarcasProcesadas> items) throws Exception {
		if(items.get(0).getCompositeId()!=null) {			
			respository.saveAll(items);	
		}
		
		
	}
	
	

}
