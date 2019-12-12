package co.edu.uniandes.csw.bookstore.ejb;

import co.edu.uniandes.csw.bookstore.entities.ClienteEntity;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.ClientePersistence;
import co.edu.uniandes.csw.bookstore.persistence.BookPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Cliente y Book.
 *
 * @author ISIS2603
 */
@Stateless
public class ClienteBooksLogic {

    private static final Logger LOGGER = Logger.getLogger(ClienteBooksLogic.class.getName());

    @Inject
    private BookPersistence bookPersistence;

    @Inject
    private ClientePersistence authorPersistence;

    /**
     * Asocia un Book existente a un Cliente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param booksId Identificador de la instancia de Book
     * @return Instancia de BookEntity que fue asociada a Cliente
     */
    public BookEntity addBook(Long clienteId, Long booksId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un libro al autor con id = {0}", clienteId);
        ClienteEntity authorEntity = authorPersistence.find(clienteId);
        BookEntity bookEntity = bookPersistence.find(booksId);
        bookEntity.getClientes().add(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un libro al autor con id = {0}", clienteId);
        return bookPersistence.find(booksId);
    }

    /**
     * Obtiene una colección de instancias de BookEntity asociadas a una
     * instancia de Cliente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @return Colección de instancias de BookEntity asociadas a la instancia de
     * Cliente
     */
    public List<BookEntity> getBooks(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros del autor con id = {0}", clienteId);
        return authorPersistence.find(clienteId).getBooks();
    }

    /**
     * Obtiene una instancia de BookEntity asociada a una instancia de Cliente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param booksId Identificador de la instancia de Book
     * @return La entidadd de Libro del autor
     * @throws BusinessLogicException Si el libro no está asociado al autor
     */
    public BookEntity getBook(Long clienteId, Long booksId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0} del autor con id = " + clienteId, booksId);
        List<BookEntity> books = authorPersistence.find(clienteId).getBooks();
        BookEntity bookEntity = bookPersistence.find(booksId);
        int index = books.indexOf(bookEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0} del autor con id = " + clienteId, booksId);
        if (index >= 0) {
            return books.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado al autor");
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Cliente
     *
     * @param authorId Identificador de la instancia de Cliente
     * @param books Colección de instancias de BookEntity a asociar a instancia
     * de Cliente
     * @return Nueva colección de BookEntity asociada a la instancia de Cliente
     */
    public List<BookEntity> replaceBooks(Long authorId, List<BookEntity> books) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los libros asocidos al author con id = {0}", authorId);
        ClienteEntity authorEntity = authorPersistence.find(authorId);
        List<BookEntity> bookList = bookPersistence.findAll();
        for (BookEntity book : bookList) {
            if (books.contains(book)) {
                if (!book.getClientes().contains(authorEntity)) {
                    book.getClientes().add(authorEntity);
                }
            } else {
                book.getClientes().remove(authorEntity);
            }
        }
        authorEntity.setBooks(books);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los libros asocidos al author con id = {0}", authorId);
        return authorEntity.getBooks();
    }

    /**
     * Desasocia un Book existente de un Cliente existente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param booksId Identificador de la instancia de Book
     */
    public void removeBook(Long clienteId, Long booksId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un libro del author con id = {0}", clienteId);
        ClienteEntity authorEntity = authorPersistence.find(clienteId);
        BookEntity bookEntity = bookPersistence.find(booksId);
        bookEntity.getClientes().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un libro del author con id = {0}", clienteId);
    }
}
