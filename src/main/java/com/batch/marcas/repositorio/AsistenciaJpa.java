package com.batch.marcas.repositorio;




import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.batch.marcas.model.MarcasEmpleado;





public interface AsistenciaJpa extends JpaRepository<MarcasEmpleado, Integer>{
	
	@Query(value="SELECT * FROM empleado_asistencia  WHERE idempleado =:idEmpleado AND fecha=:fecha", nativeQuery=true)
	MarcasEmpleado findByCompositeId(String idEmpleado, Date fecha);
	
	
	@Query(value="SELECT * FROM empleado_asistencia WHERE MONTH(fecha) = :mes AND YEAR(fecha) = :anio AND proceso=:estado", nativeQuery=true)
	List<MarcasEmpleado> findMarcasAProcesar(@Param("mes") int mes,@Param("anio") int anio, @Param("estado") String estado );
	
	
	
	@Query(value="SELECT e.idempleado,c.horas FROM empleado e JOIN empleado_cargo ec ON (e.idempleado=ec.idempleado) JOIN cargos c ON (ec.idcargo=c.id) JOIN horario_trabajo ht ON (ht.id=ec.idhorario) where e.idempleado=:id", nativeQuery=true)
    Object findEmpleadoHorarios(String id);
	
	}
