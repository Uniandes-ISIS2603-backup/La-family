/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.resources;

import co.edu.uniandes.csw.bookstore.dtos.ClienteDTO;
import co.edu.uniandes.csw.bookstore.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.bookstore.ejb.ClienteLogic;
import co.edu.uniandes.csw.bookstore.entities.ClienteEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *Clase que implementa el recurso "clientes".
 * @author Sofia Vargas 
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource 
{

    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());

    @Inject
    private ClienteLogic clienteLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un nuevo Cliente con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param cliente {@link ClienteDTO} - EL cliente que se desea guardar.
     * @return JSON {@link ClienteDTO} - El cliente guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el cliente o la informacion es invalida
     */
    @POST
    public ClienteDTO createCliente(ClienteDTO cliente) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ClienteResource createCliente: input: {0}", cliente);
        ClienteDTO nuevoClienteDTO = new ClienteDTO(clienteLogic.crearCliente(cliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource createCliente: output: {0}", nuevoClienteDTO);
        return nuevoClienteDTO;
    }

    /**
     * Busca y devuelve todos los clientes que existen en la aplicacion.
     *
     * @return JSONArray {@link ClienteDetailDTO} - Los clientes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClienteDetailDTO> getClientes()
    {
        LOGGER.info("ClienteResource getClientes: input: void");
        List<ClienteDetailDTO> listaClientes = listEntity2DetailDTO(clienteLogic.getClientes());
        LOGGER.log(Level.INFO, "ClienteResource getClientes: output: {0}", listaClientes);
        return listaClientes;
    }

    /**
     * Busca el cliente con el id asociado recibido en la URL y lo devuelve.
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     */
    @GET
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "ClienteResource getCliente: input: {0}", clientesId);
        ClienteEntity clienteEntity = clienteLogic.getCliente(clientesId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        }
       ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(clienteEntity);
      LOGGER.log(Level.INFO, "ClienteResource getCliente: output: {0}", clienteDetailDTO);
        return clienteDetailDTO;
    }
    
    /**
     * Actualiza el cliente con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param clientesId Identificador del cliente que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param cliente {@link ClienteDTO} El cliente que se desea guardar.
     * @return JSON {@link ClienteDetailDTO} - El cliente guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el cliente.
     */
    @PUT
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("clientesId") Long clientesId, ClienteDetailDTO cliente) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: input: id: {0} , cliente: {1}", new Object[]{clientesId, cliente});
        cliente.setId(clientesId);
        if (clienteLogic.getCliente(clientesId) == null)
        {
            throw new WebApplicationException("No se puede actualizar ya que el recurso no existe", 404);
        }
        ClienteDetailDTO detailDTO = null;
        detailDTO = new ClienteDetailDTO(clienteLogic.updateCliente(clientesId, cliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: output: {0}", detailDTO);
        return detailDTO;
    }

//    /**
//     * Borra el cliente con el id asociado recibido en la URL.
//     *
//     * @param clientesId Identificador del cliente que se desea borrar. Este debe ser
//     * una cadena de dígitos.
//     * @throws co.edu.uniandes.csw.clientestore.exceptions.BusinessLogicException
//     * cuando el cliente tiene autores asociados.
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra el cliente.
//     */
//    @DELETE
//    @Path("{clientesId: \\d+}")
//    public void deleteCliente(@PathParam("clientesId") Long clientesId) throws BusinessLogicException 
//    {
//        LOGGER.log(Level.INFO, "ClienteResource deleteCliente: input: {0}", clientesId);
//        ClienteEntity entity = clienteLogic.getCliente(clientesId);
//        if (entity == null)
//        {
//            throw new WebApplicationException("No se puede eliminar el cliente ya que no existe", 404);
//        }
//        //clienteEditorialLogic.removeEditorial(clientesId);
//        clienteLogic.deleteCliente(clientesId);
//        LOGGER.info("ClienteResource deleteCliente: output: void");
//    }
//    
  
    
    
//    @Path("{clienteId: \\d+}/books")
//    public Class<ClienteBooksResource > getClienteBooksResource (@PathParam("clienteId") Long clienteId) {
//        if (clienteLogic.getCliente(clienteId)==null) {
//            throw new WebApplicationException("No se puede encontrar los libros ya que el cliente no existe", 404);
//        }
//        return ClienteBooksResource.class;
//    }
//    
    
/**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ClienteEntity a una lista de
     * objetos ClienteDetailDTO (json)
     *
     * @param entityList corresponde a la lista de clientes de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de clientes en forma DTO (json)
     */
    private List<ClienteDetailDTO> listEntity2DetailDTO(List<ClienteEntity> entityList) {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }
    
}
