/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.ejb;

import co.edu.uniandes.csw.bookstore.entities.ClienteEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.ClientePersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;
import java.util.logging.Level;


/**
 *
 * @cliente Sofia Vargas
 */
@Stateless
public class ClienteLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());
    @Inject 
    private ClientePersistence persistence;
    
    
    
   public ClienteEntity crearCliente(ClienteEntity cliente) throws BusinessLogicException
   {
       if(getClienteByUsername(cliente.getUserName()) != null)
           throw new BusinessLogicException("Ya existe un cliente con el mismo username");
       if(cliente.getNombre() == null)
           throw new BusinessLogicException("El nombre del cliente está vacio.");
       if(cliente.getUserName() == null)
           throw new BusinessLogicException("El userName no puede ser vacio.");
       if(cliente.getContrasenia() == null)
           throw new BusinessLogicException("La contraseña no puede ser nula.");
       if(cliente.getCorreo()== null)
           throw new BusinessLogicException("El correo no puede ser nulo.");
      // if(cliente.getCorreo().contains("@") == false || cliente.getCorreo().contains(".")==false)
        //   throw new BusinessLogicException("Correo no válido.");
       if(cliente.getMonedero() < 0.0)
           throw new BusinessLogicException("El monedero no pude ser negativo.");
       
       cliente = persistence.create(cliente);
       return cliente;
   }
   
   /**
     * Devuelve todos los clientes de la base de datos.
     *
     * @return Lista de entidades de tipo Cliente.
     */
     public List<ClienteEntity> getClientes() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los clientes.");
        List<ClienteEntity> clientes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los clientes.");
        return clientes;
    }
    
      /**
     * Busca un Cliente por el id
     *
     * @param clienteId El id del cliente a buscar
     * @return El cliente encontrado, null si no lo encuentra.
     */
    public ClienteEntity getCliente(Long clienteId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con id = {0}", clienteId);
        ClienteEntity clienteEntity = persistence.find(clienteId);
        if (clienteEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "El cliente con el id = {0} no existe", clienteId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con id = {0}",clienteId);
        return clienteEntity;
    }
    
    public ClienteEntity getClienteByUsername(String pUsername)
    {
         LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con username = {0}", pUsername);
        ClienteEntity clienteEntity = persistence.findByUserName(pUsername);
        if (clienteEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "El cliente con el username = {0} no existe", pUsername);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con username = {0}",pUsername);
        return clienteEntity;
    }
    
     /**
     * Actualizar un cliente por ID
     *
     * @param clienteId El ID del cliente a actualizar
     * @param cliente La entidad del cliente con los cambios deseados
     * @return La entidad del cliente luego de actualizarla
     * @throws BusinessLogicException Si la fecha o el precio es invalido de la actualización es inválido
     */
    public ClienteEntity updateCliente(Long clienteId, ClienteEntity cliente) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clienteId);
        
        if(cliente.getNombre() == null)
           throw new BusinessLogicException("El nombre del cliente está vacio.");
       if(cliente.getUserName() == null)
           throw new BusinessLogicException("El userName no puede ser vacio.");
       if(cliente.getContrasenia() == null)
           throw new BusinessLogicException("La contraseña no puede ser nula.");
        if(cliente.getCorreo()== null)
           throw new BusinessLogicException("El correo no puede ser nulo.");
      // if(cliente.getCorreo().contains("@") == false || cliente.getCorreo().contains(".")==false)
        //   throw new BusinessLogicException("Correo no válido.");
       if(cliente.getMonedero() < 0.0)
           throw new BusinessLogicException("El monedero no pude ser negativo.");
       
       
        ClienteEntity newEntity = persistence.update(cliente);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cliente con id = {0}", cliente.getId());
        return newEntity;
        
    }
    
//    /**
//     * Elimina una instancia de Cliente de la base de datos.
//     *
//     * @param clienteId Identificador de la instancia a eliminar.
//     * @throws BusinessLogicException si el autor tiene libros asociados.
//     */
//    public void deleteCliente(Long clienteId) throws BusinessLogicException
//    {
//        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente con id = {0}", clienteId);
////        
////        List<QuejasYReclamosEntity> quejas  = getCliente(clienteId).getQuejas();
////        if(quejas != null && !quejas.isEmpty())
////        {
////            throw new BusinessLogicException("No se puede borrar el autor con id= " + clienteId + "porque tiene quejas asociadas.");
////        }
////        List<DomicilioEntity> domicilios  = getCliente(clienteId).getDomicilios();
////        if(quejas != null && !quejas.isEmpty())
////        {
////            throw new BusinessLogicException("No se puede borrar el autor con id= " + clienteId + "porque tiene quejas asociadas.");
////        }
//       
//        persistence.delete(clienteId);
//        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", clienteId);
//    }


}
