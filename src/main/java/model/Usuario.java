package model;

public class Usuario {
    /* Atributos */
    private Integer id;
    private String nombre;
    private Long edad;

    /* Constructor para insertar usuarios */
    public Usuario(String nombre, Long edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    /* Constructor para Listar usuarios */
    public Usuario(Integer id, String nombre, Long edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    /* Getters */
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Long getEdad() {
        return edad;
    }
}
