package com.batch.marcas.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

import com.batch.marcas.model.MarcasEmpleado;

public class MarcasEmpleadoItemReaderListener implements ItemReadListener<MarcasEmpleado>{

	private static final Logger LOGGER = LoggerFactory.getLogger(MarcasEmpleadoItemReaderListener.class);

    @Override
    public void beforeRead() {
        LOGGER.info("beforeRead");
    }

    @Override
    public void afterRead(MarcasEmpleado creditCard) {
        LOGGER.info("afterRead: " + creditCard.toString());
    }

    @Override
    public void onReadError(Exception e) {
        LOGGER.info("onReadError");
    }
}
