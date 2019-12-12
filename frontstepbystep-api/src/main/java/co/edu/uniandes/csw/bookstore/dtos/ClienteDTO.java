/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.dtos;

import co.edu.uniandes.csw.bookstore.entities.ClienteEntity;
import java.io.Serializable;

/**
 *
 * @author Sofia Vargas
 */
public class ClienteDTO implements Serializable 
{

   
 private Long id;
 private String nombre;
 private String userName;
 private String contrasenia;
 private String correo;
 private Double monedero;

public ClienteDTO()
{
}


public ClienteDTO(ClienteEntity clienteEntity) 
{
        if (clienteEntity != null)
       {
            this.id = clienteEntity.getId();
            this.nombre = clienteEntity.getNombre();
            this.contrasenia = clienteEntity.getContrasenia();
            this.userName = clienteEntity.getUserName();
             this.correo = clienteEntity.getCorreo();
            this.monedero = clienteEntity.getMonedero();
             
        }
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
     * Convierte un objeto AuthorDTO a AuthorEntity.
     *
     * @return Nueva objeto AuthorEntity.
     *
     */
    public ClienteEntity toEntity() 
    {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.getId());
        clienteEntity.setNombre(this.getNombre());
        clienteEntity.setContrasenia(this.getContrasenia());
        clienteEntity.setUserName(this.getUserName());
        clienteEntity.setCorreo(this.getCorreo());
        clienteEntity.setMonedero(this.getMonedero());
        
        return clienteEntity;
    }

    /**
     * Obtiene el atributo id.
     *
     * @return atributo id.
     *
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

   
    
}
