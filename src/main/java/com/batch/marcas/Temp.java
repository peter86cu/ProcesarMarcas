package com.batch.marcas;

import java.time.Duration;
import java.time.LocalDateTime;

public class Temp {
	
	 public static void main(String[] args) {
	        // Crear dos instancias de LocalDateTime con las fechas y horas deseadas
	        LocalDateTime fecha1 = LocalDateTime.of(2023, 6, 13, 10, 0, 0); // 13 de junio de 2023, 10:00:00
	        LocalDateTime fecha2 = LocalDateTime.of(2023, 6, 13, 11, 30, 11); // 13 de junio de 2023, 12:30:00

	        // Calcular la diferencia de tiempo entre las fechas
	        Duration duracion = Duration.between(fecha1, fecha2);

	        long horas = duracion.toHours();
	        long minutos = duracion.toMinutes() % 60;
	        long segundos = duracion.getSeconds() % 60;
	        
	     
	        

	        System.out.println("La diferencia en horas es: " + horas);
	        System.out.println("La diferencia en minutos es: " + minutos);
	        System.out.println("La diferencia en minutos es: " + segundos);


	    }

}
