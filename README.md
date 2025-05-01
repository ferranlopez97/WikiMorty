
# WikiMorty

Aplicación lista-detalle con MVI de los personajes de Rick y Morty usando la api de https://rickandmortyapi.com/

*He dejado algún fragmento de código comentado para comentarlo más adelante si se da el caso.




## Librerías usadas

- Hilt
- Retrofit
- Room
- Compose Navigation
- Coil 
- MockWebServer
- Junit 4 y 5
- MockK
- AssertK
- Turbine




## Estructura 
![estructura (1)](https://github.com/user-attachments/assets/1ac50b09-3c72-41bc-ae27-689fd5fc897d)

*common/di esta hecho para:
- Que domain no necesite implementar hilt y así pueda ser un módulo de kotlin puro y no Android
- Que el módulo app no tenga que importar todos los otros módulos para poder inyectar dependencias

## Posibles mejoras

### Módulo buildSrc/build-logic
Para manejar Gradle Convention Plugins y gestionar mejor las dependencias. Esto lo suelo hacer en proyectos más grandes y con módulos por feature, donde hay muchos módulos que usan las mismas dependencias. En este proyecto, al tener pocos módulos, no lo he visto imprescindible.
### Búsqueda de personajes remota
Ahora mismo el filtrado de personajes es offline (Room). Esta la estructura hecha para que se pudiera aplicar tambien a la query remota, pero no esta implementado. 

## UX

- Animación fluida al navegar entre pantallas
- SharedElementTransition con la imagen del personaje al navegar al detalle.
- Crossfade en vistas al cambiar entre estados loading y resultado
- Crossfade al cargar imagenes
- Cuando el input de búsqueda esta abierto y se hace click en el back button de Android, se cierra la búqueda automaticamente.
- Al escribir en el input de búsqueda se hace scroll arriba de la lista para mostrar los nuevos resultados.
