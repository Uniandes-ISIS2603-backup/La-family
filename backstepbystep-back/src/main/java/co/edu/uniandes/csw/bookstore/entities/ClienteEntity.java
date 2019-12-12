/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author Sofia Vargas
 */

@Entity 
public class ClienteEntity extends BaseEntity implements Serializable
{

   

 private String nombre;
 private String correo;
 @PodamDoubleValue(minValue = 0.0)
 private Double monedero;
 private String userName;
 private String contrasenia;
 
  @PodamExclude
  @ManyToMany(mappedBy = "clientes")
   private List<BookEntity> books = new ArrayList<>();
 

 public ClienteEntity(){
    
}
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

 
    /**
     * @return the monedero
     */
    public Double getMonedero() {
        return monedero;
    }

    /**
     * @param monedero the monedero to set
     */
    public void setMonedero(Double monedero) {
        this.monedero = monedero;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
   
    
     /**
     * @return the books
     */
    public List<BookEntity> getBooks() {
        return books;
    }

    /**
     * @param books the books to set
     */
    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }
}
