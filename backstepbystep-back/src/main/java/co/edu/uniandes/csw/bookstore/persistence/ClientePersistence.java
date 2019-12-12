/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.persistence;

import co.edu.uniandes.csw.bookstore.entities.ClienteEntity;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sofia Vargas 
 */
@Stateless
public class ClientePersistence
{
    
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());
    
    @PersistenceContext (unitName = "bookstorePU")
    protected EntityManager em;
     
    public ClienteEntity create(ClienteEntity usuarioEntity)
    {
        em.persist(usuarioEntity);
        return usuarioEntity;
    }
    
    public ClienteEntity find(Long usuarioId)
    {
         LOGGER.log(Level.INFO, "Consultando cliente con id={0}", usuarioId);
        return em.find(ClienteEntity.class, usuarioId);
    }
    
    public List<ClienteEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los clientes.");
        // Se crea un query para buscar todas los clientes en la base de datos.
        TypedQuery<ClienteEntity> query = em.createQuery("select u from ClienteEntity u", ClienteEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de clientes.
        return query.getResultList();
    }
    
    public ClienteEntity update(ClienteEntity usuario)
    {
        //Es equivalente a un comando de SQL que permite actualizar la info
        return em.merge(usuario);
    }
      
    public ClienteEntity findByUserName(String user) 
    {
        LOGGER.log(Level.INFO, "Consultando cliente por userName {0}", user);
        // Se crea un query para buscar clientes con el user que recibe el método como argumento. ":user" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.userName = :user", ClienteEntity.class);
        // Se remplaza el placeholder ":user" con el valor del argumento 
        query = query.setParameter("user", user);
        // Se invoca el query se obtiene la lista resultado
        List<ClienteEntity> sameUser = query.getResultList();
        ClienteEntity result;
        if (sameUser == null)
        {
            result = null;
        } else if (sameUser.isEmpty()) 
        {
            result = null;
        } else 
        {
            result = sameUser.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar clientes por userName {0}", user);
        return result; 
    }
    
    
     
   
        
        public ClienteEntity findByNombre(String nombre) 
    {
        LOGGER.log(Level.INFO, "Consultando cliente por nombreName {0}", nombre);
        // Se crea un query para buscar clientes con el nombre que recibe el método como argumento. ":nombre" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.nombre = :nombre", ClienteEntity.class);
        // Se remplaza el placeholder ":nombre" con el valor del argumento 
        query = query.setParameter("nombre", nombre);
        // Se invoca el query se obtiene la lista resultado
        List<ClienteEntity> sameNombre = query.getResultList();
        ClienteEntity result;
        if (sameNombre == null)
        {
            result = null;
        } else if (sameNombre.isEmpty()) 
        {
            result = null;
        } else 
        {
            result = sameNombre.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar clientes por nombre {0}", nombre);
        return result; 
    }
           
    
    
      /**
     * Borra una cliente de la base de datos recibiendo como argumento el id del cliente
     *
     * @param clientesId: id correspondiente al cliente a borrar.
     */
    public void delete(Long clientesId)
    {

        LOGGER.log(Level.INFO, "Borrando el cliente con id={0}", clientesId);
        // Se hace uso de mismo método que esta explicado en public ClienteEntity find(Long id) para obtener la cliente a borrar.
        ClienteEntity clienteEntity = em.find(ClienteEntity.class, clientesId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from ClienteEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(clienteEntity);
    }
    
    
}
