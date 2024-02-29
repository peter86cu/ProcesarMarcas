package com.batch.marcas.read;

import java.time.LocalDate;
import java.util.Iterator;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.marcas.model.MarcasEmpleado;
import com.batch.marcas.repositorio.AsistenciaJpa;

public class MarcasEmpleadosItemReader implements ItemReader<MarcasEmpleado> {

	@Autowired
	private AsistenciaJpa respository;

	private Iterator<MarcasEmpleado> marcasIterator;

	@BeforeStep
	public void before(StepExecution stepExecution) {
		LocalDate fechaActual = LocalDate.now();
        int mes = fechaActual.getMonthValue()-1;
        int anio= fechaActual.getYear();
		marcasIterator = respository.findMarcasAProcesar(2,anio,"SIN PROCESAR").iterator();
	}

	@Override
	public MarcasEmpleado read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (marcasIterator != null && marcasIterator.hasNext()) {
			MarcasEmpleado marca = marcasIterator.next();
			if(marca.getMarcaentrada()==null || marca.getMarcasalida()==null) {
				marca.setEstado("INCONSISTENTE");
				marca.setProceso("PROCESADA");
				marca.setAccion("PENDIENTE");
				//respository.save(null)
			}else {
				marca.setEstado("CORRECTA");
				marca.setProceso("PROCESADA");
				marca.setAccion(null);
			}
			respository.save(marca);

			return marca;
		} else {
			return null;
		}
	}

}
