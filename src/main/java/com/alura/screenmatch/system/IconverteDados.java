package com.alura.screenmatch.system;

public interface IconverteDados {
    <T> T obterdados(String json, Class<T> classe);
}
