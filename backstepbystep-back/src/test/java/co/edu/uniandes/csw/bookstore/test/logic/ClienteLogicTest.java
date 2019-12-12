/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.test.logic;

import co.edu.uniandes.csw.bookstore.ejb.ClienteLogic;
import co.edu.uniandes.csw.bookstore.entities.ClienteEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.ClientePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Sofia Vargas
 */

@RunWith(Arquillian.class)
public class ClienteLogicTest 
{
    private final PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ClienteLogic clienteLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDevelopment()
    {
         return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                 .addPackage(ClientePersistence.class.getPackage()) 
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
     /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() 
    {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
     /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() 
    {
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
        
    @Test
    public void createClienteTest() throws BusinessLogicException
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clienteLogic.crearCliente(newEntity);
        Assert.assertNotNull(result);
        
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId()); 
        Assert.assertEquals(entity.getNombre(), result.getNombre()); 
        Assert.assertEquals(entity.getUserName(), result.getUserName());
        Assert.assertEquals(entity.getContrasenia(), result.getContrasenia());
   Assert.assertEquals(entity.getCorreo(), result.getCorreo());
    Assert.assertEquals(entity.getMonedero(), result.getMonedero());
        
         
    }
    /**
     * Prueba para crear un cliente con un nombre vacio
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteNombreNullTest() throws BusinessLogicException
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setNombre(null);
        ClienteEntity result = clienteLogic.crearCliente(newEntity);
    }
    
      /**
     * Prueba para crear un cliente con contraseña vacia.
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteContraseniaNullTest() throws BusinessLogicException
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setContrasenia(null);
        ClienteEntity result = clienteLogic.crearCliente(newEntity);
    }
    
      /**
     * Prueba para crear un cliente con un usuario vacio
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteUsuarioNullTest() throws BusinessLogicException
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setUserName(null);
        ClienteEntity result = clienteLogic.crearCliente(newEntity);
    }
   
       /**
     * Prueba para crear un cliente con un usuario vacio
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteCorreoNullTest() throws BusinessLogicException
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreo(null);
        ClienteEntity result = clienteLogic.crearCliente(newEntity);
    }
    
       /**
     * Prueba para crear un cliente con un usuario vacio
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteMonederoNegTest() throws BusinessLogicException
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setMonedero(-20.0);
        ClienteEntity result = clienteLogic.crearCliente(newEntity);
    }
    
    
//       /**
//     * Prueba para crear un cliente con un usuario vacio
//     * @throws BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void createClienteCorreoInvalidoTest() throws BusinessLogicException
//    {
//        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
//        newEntity.setCorreo("ashask");
//        ClienteEntity result = clienteLogic.crearCliente(newEntity);
//    }
 
     /**
     * Prueba para consultar la lista de Clientes.
     */
    @Test
    public void getClientesTest() 
    {
        List<ClienteEntity> list = clienteLogic.getClientes();
        
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity entity : list) {
            boolean found = false;
            for (ClienteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     /**
     * Prueba para consultar un Cliente.
     */
    @Test
    public void getClienteTest()
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity result = clienteLogic.getCliente(entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(entity.getId(), result.getId()); 
        Assert.assertEquals(entity.getNombre(), result.getNombre()); 
        Assert.assertEquals(entity.getUserName(), result.getUserName());
        Assert.assertEquals(entity.getContrasenia(), result.getContrasenia());
         Assert.assertEquals(entity.getCorreo(), result.getCorreo());
        Assert.assertEquals(entity.getMonedero(), result.getMonedero());
  
    }
    
    
    /**
     * Prueba para actualizar un Cliente
     *
     * @throws BusinessLogicException
     */
    @Test
    public void updateClienteTest() throws BusinessLogicException 
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity pojoEntity = factory.manufacturePojo(ClienteEntity.class);
        
       pojoEntity.setId(entity.getId());
         
        clienteLogic.updateCliente(pojoEntity.getId(), pojoEntity);
        ClienteEntity result = em.find(ClienteEntity.class, entity.getId());
        
       Assert.assertEquals(pojoEntity.getId(), result.getId()); 
       Assert.assertEquals(pojoEntity.getNombre(), result.getNombre()); 
        Assert.assertEquals(pojoEntity.getUserName(), result.getUserName());
        Assert.assertEquals(pojoEntity.getContrasenia(), result.getContrasenia());
         Assert.assertEquals(pojoEntity.getCorreo(), result.getCorreo());
        Assert.assertEquals(pojoEntity.getMonedero(), result.getMonedero());
   }
    
     /**
     * Prueba para actualizar un cliente con un nombre vacio
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClienteNombreNullTest() throws BusinessLogicException
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setNombre(null);
        newEntity.setId(entity.getId());
        clienteLogic.updateCliente(newEntity.getId(), newEntity);
    }
  
      /**
     * Prueba para actualizar un cliente con contraseña vacia.
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClienteContraseniaNullTest() throws BusinessLogicException
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setContrasenia(null);
        newEntity.setId(entity.getId());
        clienteLogic.updateCliente(newEntity.getId(), newEntity);
    }
    
      /**
     * Prueba para actualizar un cliente con un usuario vacio
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClienteUsuarioNullTest() throws BusinessLogicException
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setUserName(null);
         newEntity.setId(entity.getId());
        clienteLogic.updateCliente(newEntity.getId(), newEntity);
    }
    
       /**
     * Prueba para actualizar un cliente con un usuario vacio
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClienteCorreoNullTest() throws BusinessLogicException
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreo(null);
         newEntity.setId(entity.getId());
        clienteLogic.updateCliente(newEntity.getId(), newEntity);
    }
    
//       /**
//     * Prueba para actualizar un cliente con un usuario vacio
//     * @throws BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void updateClienteCorreoInvalidoTest() throws BusinessLogicException
//    {
//        ClienteEntity entity = data.get(0);
//        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
//        newEntity.setCorreo("hjshals");
//         newEntity.setId(entity.getId());
//        clienteLogic.updateCliente(newEntity.getId(), newEntity);
//    }
//    
    
       /**
     * Prueba para actualizar un cliente con un usuario vacio
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClienteMonederoNegTest() throws BusinessLogicException
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setMonedero(-20.0);
         newEntity.setId(entity.getId());
        clienteLogic.updateCliente(newEntity.getId(), newEntity);
    }

}
    