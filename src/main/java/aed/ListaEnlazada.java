package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private int longitud = 0;
    private Nodo primero;
    private Nodo ultimo;
    
    private class Nodo {
       T valor;
       Nodo sig;
       Nodo ant;

        Nodo(T valor){
            this.valor = valor;
            this.sig = null;
            this.ant = null;
        }
    }

    public ListaEnlazada() {
        longitud = 0;
        primero = ultimo = null;
    }

    public int longitud() {
        return longitud;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevoNodo = new Nodo(elem);
        if (longitud == 0) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            nuevoNodo.sig = primero;
            primero.ant = nuevoNodo;
            primero = nuevoNodo;
        }
        longitud++;
    }

    public void agregarAtras(T elem) {
        Nodo nuevoNodo = new Nodo(elem);
        if (longitud == 0) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.sig = nuevoNodo;
            nuevoNodo.ant = ultimo;
            ultimo = nuevoNodo;
        }
        longitud++;
    }

    public T obtener(int i) {
        if (longitud == 0) {
            return primero.valor;
        }
        Nodo actual = primero;
        for(int j=0; j < i; j++){
            if (actual.sig != null) {
                actual = actual.sig;
            }
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        if (i < 0 || i >= longitud) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        Nodo actual = primero;

        // Avanzar hasta el nodo que se quiere eliminar
        for (int j = 0; j < i; j++) {
            actual = actual.sig;
        }

        if (actual == primero) {
            primero = actual.sig;
            if (primero != null) {
                primero.ant = null;
            }
        } else if (actual == ultimo) {
            ultimo = actual.ant;
            if (ultimo != null) {
                ultimo.sig = null;
            }
        } else {
            Nodo nodoAnterior = actual.ant;
            Nodo nodoSiguiente = actual.sig;
            if (nodoAnterior != null) {
                nodoAnterior.sig = nodoSiguiente;
            }
            if (nodoSiguiente != null) {
                nodoSiguiente.ant = nodoAnterior;
            }
        }

        longitud--;
    }
    

    public void modificarPosicion(int indice, T elem) {
        if (indice == 0) {
            primero.valor = elem;
        } else if (indice == longitud - 1) {
            ultimo.valor = elem;
        } else {
            Nodo actual = primero;
            for(int j=0; j < indice; j++){
                if (actual.sig != null) {
                    actual = actual.sig;
                }
            }
            actual.valor = elem;
        }
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> copia = new ListaEnlazada<>();

        for(int i=0; i < longitud; i++){
            copia.agregarAtras(obtener(i));
        }
        return copia;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> copia = new ListaEnlazada<>();
        Nodo actNodo = lista.primero;
        while (actNodo != null) {
            copia.agregarAtras(actNodo.valor);
            actNodo = actNodo.sig;
        }
        primero = copia.primero;
        ultimo = copia.ultimo;
        longitud = copia.longitud;
    }
    
    @Override
    public String toString() {
        StringBuilder secuencia = new StringBuilder();
        secuencia.append("[");
        Nodo actual = primero;

        while (actual != null) {
            secuencia.append(actual.valor);
            actual = actual.sig;
            if (actual != null) {
                secuencia.append(", ");
            }
        }
        secuencia.append("]");
        return secuencia.toString();
    }

    private class ListaIterador implements Iterador<T> {
    	private Nodo actual = primero;
        private Nodo anterior = null;
        
        public boolean haySiguiente() {
	        return actual != null;
        }
        
        public boolean hayAnterior() {
            return anterior != null;
        }

        public T siguiente() {
	        if (this.haySiguiente()) {
                T elemT = actual.valor;
                actual = actual.sig;
                if (anterior != null) {
                    anterior = anterior.sig;
                } else {
                    anterior = primero;
                }
                return elemT;
            } else {
                return null;
                }
        }
        
        public T anterior() {
	        if (this.hayAnterior()) {
                T elemT = anterior.valor;
                if (actual != null) {
                    anterior = anterior.ant;
                    actual = actual.ant;
                } else {
                    anterior = anterior.ant;
                    actual = ultimo;
                }
                return elemT;
            } else {
                return null;
                }
            }
        }
    

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }

}

