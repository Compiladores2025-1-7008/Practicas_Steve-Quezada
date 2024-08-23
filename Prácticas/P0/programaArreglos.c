/*
 * Este programa muestra el uso de directivas del preprocesador en C.
 * Se define un arreglo, se llena con valores naturales consecutivos  
 * y se multiplican por un valor determinado.
 * Finalmente, se imprimen los valores originales y los multiplicados.
 */

#include <stdio.h>

#define TAMAÑO 5

#ifndef MÍNIMO
#define MÍNIMO 1
#endif

#if TAMAÑO > MÍNIMO
#define MULTIPLICADOR 2
#else
#define MULTIPLICADOR 1
#endif

#if MULTIPLICADOR > 1
#warning "El valor de MULTIPLICADOR es mayor a 1, lo cual puede afectar el resultado."
#endif

#if TAMAÑO <= 0
#error "El tamaño del arreglo debe ser mayor que 0!"
#endif

#pragma GCC optimize ("O2")

int main()
{
    int numeros[TAMAÑO];

    for (int i = 0; i < TAMAÑO; i++)
    {
        numeros[i] = i + MÍNIMO;
    }

    printf("Valores originales y valores multiplicados:\n");
    for (int i = 0; i < TAMAÑO; i++)
    {
        printf("Original: %d, Multiplicado: %d\n", numeros[i], numeros[i] * MULTIPLICADOR);
    }

    return 0;
}