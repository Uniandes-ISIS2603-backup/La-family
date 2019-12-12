/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.test.persistence;

import co.edu.uniandes.csw.bookstore.entities.ClienteEntity;
import co.edu.uniandes.csw.bookstore.persistence.ClientePersistence;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
import javax.transaction.UserTransaction;
import java.util.ArrayList;

/**
 *
 * @author Sofía Vargas
 */
@RunWith(Arquillian.class)
public class ClientePersistanceTest 
{
    @Inject
    ClientePersistence up;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ClienteEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    @Before
    public void configTest() 
    {
        try {
            utx.begin();
            em.joinTransaction();
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
    
    private void clearData() 
    {
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
    
     /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }    
    
     @Test
    public void createClienteTest()
    {
       PodamFactory factory = new PodamFactoryImpl();
       ClienteEntity usuario = factory.manufacturePojo(ClienteEntity.class);
       ClienteEntity result = up.create(usuario);
       Assert.assertNotNull(result);
       
       ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
       Assert.assertEquals(usuario.getNombre(),entity.getNombre());
    }
    
    @Test
    public void getClienteListTest()
    {
        List<ClienteEntity> list = up.findAll();
        Assert.assertEquals(data.size(), list.size());
        for(ClienteEntity ent : list)
        {
            boolean  encontrada = false;
            for(ClienteEntity entity : data)
            {
                if(ent.getId().equals(entity.getId()))
                {
                    encontrada = true;
                }
            }
            Assert.assertTrue(encontrada);
        }
    }
    
  
    public void getClienteTest()
    {
        ClienteEntity usuario = data.get(0);
        ClienteEntity newEntity = up.find(usuario.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(usuario.getId(), newEntity.getId());
        Assert.assertEquals(usuario.getNombre(), newEntity.getNombre());
      
        Assert.assertEquals(usuario.getUserName(), newEntity.getUserName());
        Assert.assertEquals(usuario.getContrasenia(), newEntity.getContrasenia()); 
    }
   
    @Test
    public void updateClienteTest()
    {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setId(entity.getId());
        up.update(newEntity);
        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
    @Test
    public void deleteClienteTest()
    {
        ClienteEntity entity = data.get(0);
        up.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
     @Test
    public void findClienteByNombreTest() 
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = up.findByNombre(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = up.findByNombre(null);
        Assert.assertNull(newEntity);
    }
    
     @Test
    public void findClienteByUserNameTest() 
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = up.findByUserName(entity.getUserName());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getUserName(), newEntity.getUserName());

        newEntity = up.findByUserName(null);
        Assert.assertNull(newEntity);
    }
        
}