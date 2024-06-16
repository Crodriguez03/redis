Redis es un proveedor de cache que usa la memoria ram para almacenar la información. Con ello se consigue una velocidad de acceso muy alta. Se puede configurar para trabajar en cluster, con lo que se puede tener varios nodos trabjando en un mismo entorno y poder dividir la carga distribuyendo las peticiones que le llegan a los diferentes nodos.

Datos importantes:
Cuando se guarda un objeto en cache hay que indicarle el tiempo de vida que va a durar dicho objeto antes de que expire.
Los objetos se guardan serializados, por lo que hay que implementar la interfaz Serializable para poder guardarlo.
Se pueden consultar varios ids a la vez en una única consulta. Para ello redis devolvería una lista ordenada por esos ids. Si está en caché un id de la lista vendrá el objeto. En caso de no encontrar en caché devolverá un null en la posición de la lista de su id.
Si se va a cachear un objeto y no queda espacio en redis se porducirán evictions que consiste en descachear objetos en memoria antes de que haya concluido su tiempo de expiración y así poder hacer hueco para las nuevos inserts. Redis tiene su propia política para decidir que elementos borrar en caso de falta de memoria.


Ejemplo de uso:
