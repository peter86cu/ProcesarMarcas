package com.batch.marcas.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.batch.marcas.model.MarcasEmpleado;
import com.batch.marcas.model.MarcasProcesadas;


@Component
public class MarcaProcesadaItemWriterListener implements ItemProcessListener<MarcasEmpleado, MarcasProcesadas> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarcaProcesadaItemWriterListener.class);


	

	   @Override
	    public void beforeProcess(MarcasEmpleado creditCard) {
	        LOGGER.info("beforeProcess");
	    }

	    @Override
	    public void afterProcess(MarcasEmpleado creditCard, MarcasProcesadas creditCardRisk) {
	        LOGGER.info("afterProcess: " + creditCard + " ---> " + creditCardRisk);
	    }

	    @Override
	    public void onProcessError(MarcasEmpleado creditCard, Exception e) {
	        LOGGER.info("onProcessError");
	    }
}
