package com.batch.marcas.processor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.marcas.model.*;
import com.batch.marcas.repositorio.AsistenciaJpa;
import com.batch.marcas.repositorio.FeriadosJPA;
import com.batch.marcas.repositorio.HorasExtraJpa;

//Aqui proceso las marcas
public class ProcesarMarcaItemProcessor implements ItemProcessor<MarcasEmpleado, MarcasProcesadas> {


	@Autowired
	private AsistenciaJpa respository;
	

	@Autowired
	private HorasExtraJpa daoHorasExt;
	
	private Object marcasIterator;

	
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcesarMarcaItemProcessor.class);

	@Override
	public MarcasProcesadas process(MarcasEmpleado item) throws Exception {
		
		

		
		MarcasProcesadas procesada = new MarcasProcesadas();
		if(item.getMarcaentrada()!=null && item.getMarcasalida()!=null) {
			
			marcasIterator = respository.findEmpleadoHorarios(item.getCompositeId().getIdempleado());
			if(marcasIterator != null ) {
				Object[] objArray = (Object[]) marcasIterator;
				Resultado resultado = new Resultado();

				resultado.setIdempleado(objArray[0].toString());
				resultado.setHoras((int) objArray[1]);
				
				
				String fechaIni = item.getMarcaentrada();
				String fechaFin = item.getMarcasalida();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				// Convertir Date a LocalDateTime
				LocalDateTime fechaHoraIni = LocalDateTime.parse(fechaIni, formatter);
				LocalDateTime fechaHoraFin = LocalDateTime.parse(fechaFin, formatter);

				// Calcular la diferencia de tiempo entre las fechas
				Duration duracion = Duration.between(fechaHoraIni, fechaHoraFin);

				long horas = duracion.toHours();
				long minutos = duracion.toMinutes() % 60;
				long segundos = duracion.getSeconds() % 60;

				procesada.setMinutosprocesados((int) minutos);
				CompositeMarcaEmpleado ids= new CompositeMarcaEmpleado();
				ids.setIdempleado(item.getCompositeId().getIdempleado());
				ids.setFecha(item.getCompositeId().getFecha());
				ids.setIdmarca(item.getCompositeId().getIdmarca());
				
				procesada.setCompositeId(ids);
				// procesada.setEstado("OK");
				procesada.setHorasprocesadas((int) horas);
				procesada.setSegundosprocesados((int) segundos);
				EmpleadoHorasExtra horaExtra= new EmpleadoHorasExtra();
				if(horas>resultado.getHoras()) {
					procesada.setEstado("INCONSISTENTE");
					item.setAccion("NOK");
					horaExtra.setId(0);
					horaExtra.setFecha(item.getCompositeId().getFecha());
					horaExtra.setHoras((int)horas);
					horaExtra.setMinutos((int)minutos);
					horaExtra.setSegundos((int)segundos);
					horaExtra.setEstado("SIN VALIDAR");
					horaExtra.setTipoausencia(1);
					horaExtra.setIdempleado(item.getCompositeId().getIdempleado());
					daoHorasExt.save(horaExtra);
					
				}
				if(horas==resultado.getHoras() && minutos>=15) {
					procesada.setEstado("INCONSISTENTE");
					item.setAccion("NOK");
				}
				if(horas==resultado.getHoras() && minutos<=15) {
					procesada.setEstado("CORRECTA");
					item.setAccion("OK");
				}
				if(horas<resultado.getHoras() ) {
					procesada.setEstado("INCONSISTENTE");
					item.setAccion("NOK");
				}
				respository.save(item);
				
				
			}
			
			
			
			
			//LOG.info("Convirtiendo (" + item + ") a (" + procesada + ")");
		}
		

		return procesada;
	}

}
