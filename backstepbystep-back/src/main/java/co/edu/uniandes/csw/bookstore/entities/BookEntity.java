/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.bookstore.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un libro en la persistencia y permite su serialización
 *
 * @author ISIS2603
 */
@Entity
public class BookEntity extends BaseEntity implements Serializable {

    private String name;
    private String isbn;
    private String image;
    @Temporal(TemporalType.DATE)
    private Date publishDate;
    private String description;
    private Boolean novedad;
    
    private Boolean esBestseller;
    
    private String categoria;
    
     @PodamDoubleValue(minValue = 1.0)
    private Double precio;

    @PodamExclude
    @ManyToOne
    private EditorialEntity editorial;
    
  

    @PodamExclude
    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();

    @PodamExclude
    @ManyToMany
    private List<AuthorEntity> authors = new ArrayList<AuthorEntity>();
    
    @PodamExclude
    @ManyToMany
    private List<ClienteEntity> clientes = new ArrayList<ClienteEntity>();

    /**
     * Devuelve el nombre del libro.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica el nombre del libro.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve el ISBN del libro.
     *
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Modifica el ISBN del libro.
     *
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Devuelve la imagen del libro.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Modifica la imagen del libro.
     *
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Devuelve la fecha de publicación del libro.
     *
     * @return the publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * Modifica la fecha de publicación del libro.
     *
     * @param publishDate the publishDate to set
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * Devuelve la descrupción del libro.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifica la descripción del libro.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Devuelve la editorial a la que pertenece el libro.
     *
     * @return Una entidad de editorial.
     */
    public EditorialEntity getEditorial() {
        return editorial;
    }

    /**
     * Modifica la editorial a la que pertenece el libro.
     *
     * @param editorialEntity La nueva editorial.
     */
    public void setEditorial(EditorialEntity editorialEntity) {
        this.editorial = editorialEntity;
    }

    /**
     * Devuelve las reseñas del libro.
     *
     * @return Lista de entidades de tipo Reseña
     */
    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    /**
     * Modifica las reseñas de un libro.
     *
     * @param reviews Las nuevas reseñas.
     */
    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    /**
     * Devuelve los autores de un libro
     *
     * @return the authors
     */
    public List<AuthorEntity> getAuthors() {
        return authors;
    }

    /**
     * Modifica los autores de un libro
     *
     * @param authors the authors to set
     */
    public void setAuthors(List<AuthorEntity> authors) {
        this.authors = authors;
    }

    /**
     * @return the esBestseller
     */
    public Boolean getEsBestseller() {
        return esBestseller;
    }

    /**
     * @param esBestseller the esBestseller to set
     */
    public void setEsBestseller(Boolean esBestseller) {
        this.esBestseller = esBestseller;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * @return the clientes
     */
    public List<ClienteEntity> getClientes() {
        return clientes;
    }

    /**
     * @param clientes the clientes to set
     */
    public void setClientes(List<ClienteEntity> clientes) {
        this.clientes = clientes;
    }

    /**
     * @return the novedad
     */
    public Boolean getNovedad() {
        return novedad;
    }

    /**
     * @param novedad the novedad to set
     */
    public void setNovedad(Boolean novedad) {
        this.novedad = novedad;
    }
}
