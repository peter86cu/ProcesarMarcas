package com.batch.marcas.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

import com.batch.marcas.model.MarcasEmpleado;
import com.batch.marcas.model.MarcasProcesadas;

public class ProcesarMarcaItemProcessListener implements ItemProcessListener<MarcasEmpleado, MarcasProcesadas>{

	 private static final Logger LOGGER = LoggerFactory.getLogger(ProcesarMarcaItemProcessListener.class);

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
