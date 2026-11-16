#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
int main()
{
    int pipefd[2]; // Arreglo para almacenar los descriptores de la tubería
    char mensaje[] = "Hola, proceso hijo!";
    // Crear la tubería
    if (pipe(pipefd) == -1)
    {
        perror("pipe");
        exit(EXIT_FAILURE);
    }
    // Crear un proceso hijo
    pid_t pid = fork();
    if (pid == -1)
    {
        perror("fork");
        exit(EXIT_FAILURE);
    }
    if (pid == 0)
    {
        // Proceso hijo
        close(pipefd[1]); // Cerramos el extremo de escritura de la tubería
        char buffer[100];
        ssize_t bytes_leidos = read(pipefd[0], buffer, sizeof(buffer));
        if (bytes_leidos == -1)
        {
            perror("read");
            exit(EXIT_FAILURE);
        }
        buffer[bytes_leidos] = '\0'; // Agregar el carácter nulo al final
        printf("Proceso hijo recibió: %s\n", buffer);
        close(pipefd[0]); // Cerramos el extremo de lectura de la tubería en el proceso hijo
    }
    else
    {
        // Proceso padre
        close(pipefd[0]); // Cerramos el extremo de lectura de la tubería
        ssize_t bytes_escritos = write(pipefd[1], mensaje, strlen(mensaje) + 1);
        if (bytes_escritos == -1)
        {
            perror("write");
            exit(EXIT_FAILURE);
        }
        close(pipefd[1]); // Cerramos el extremo de escritura de la tubería en el proceso padre
    }
    return 0;
}
