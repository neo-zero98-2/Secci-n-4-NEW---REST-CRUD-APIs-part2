package com.tescorporations.crud.demo.dao;

import com.tescorporations.crud.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
* EntityManager se necesita una interface y su implementación,
* pero con jpa solo se declara la interface que hereda de JpaRepository para hacer un crud sencillo.
* Con JPA no es necesario usar le anotación @Transactional en la capa de servicio ya que JPA la proporciona
*
* */

/*
* @RepositoryRestResource solo aplica con la libreria spring-boot-starter-data-rest
* al consumir el endpoint con RepositoryRestResource sería de la manera http://localhost:8080/magic-api/members
* al consumir el enpoint sin el RepositoryRestResource sería de la siguiente manera http://localhost:8080/magic-api/employees
* */
@RepositoryRestResource(path = "members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
