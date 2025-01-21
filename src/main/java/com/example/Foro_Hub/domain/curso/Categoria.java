package com.example.Foro_Hub.domain.curso;

public enum Categoria {

    FULL_STACK("Full Stack"),
    FRONT_END("Front End"),
    BACK_END("Back End"),
    DEVOPS("DevOps"),
    DATA_SCIENCE("Data Science"),
    MOBILE("Mobile"),
    QA_TESTING("QA Testing");

    private String categoria;


    Categoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public static Categoria fromString(String text) {
        return java.util.Arrays.stream(Categoria.values())
                .filter(categoria -> categoria.categoria.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ninguna categoría encontrada: " + text));
    }
}