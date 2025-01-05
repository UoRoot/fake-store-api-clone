# FakeStoreAPI Clone

Este proyecto es un clon de [FakeStoreAPI](https://fakestoreapi.com), una API REST gratuita que proporciona datos ficticios para proyectos de desarrollo web, especialmente útiles para prácticas y proyectos front-end. Este clon respeta completamente la interfaz original para garantizar que los proyectos que ya dependen de FakeStoreAPI puedan migrar sin problemas a esta implementación.

## Objetivo

El objetivo principal de este proyecto es:
- Aprender sobre el desarrollo y diseño de APIs con **Spring Boot**.
- Experimentar con la migración de proyectos de tamaño e impacto moderado.
- Sustituir la base de datos MongoDB utilizada en la API original por **MySQL**.

## Recursos Disponibles

La API incluye los mismos recursos y rutas que FakeStoreAPI, diseñados para cubrir las necesidades típicas de un prototipo de tienda en línea:

1. **Productos**: `/products`
2. **Carritos**: `/carts`
3. **Usuarios**: `/users`
4. **Autenticación**: `/auth/login`

Además, se incluye la funcionalidad de calificaciones (`rating`) en cada producto, consistente con la API original.

## Características

- Total compatibilidad con FakeStoreAPI.
- Implementada en Java utilizando el ecosistema de **Spring Boot**.
- Persistencia de datos en **MySQL**.
- Compatible con métodos de consumo populares como `fetch`, `Axios`, y otros.

## Cómo Usar

A continuación, se presentan ejemplos básicos para consumir esta API:

### Obtener Todos los Productos

```javascript
fetch("http://tu-dominio.com/api/products")
  .then(res => res.json())
  .then(json => console.log(json));
```

### Crear un Nuevo Producto

```javascript
fetch("http://tu-dominio.com/api/products", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({
    title: "Producto de prueba",
    price: 19.99,
    description: "Descripción del producto",
    image: "https://url-de-imagen.com",
    category: "categoría"
  })
})
  .then(res => res.json())
  .then(json => console.log(json));
```

### Consultar un Carrito de Usuario

```javascript
fetch("http://tu-dominio.com/api/carts/user/1")
  .then(res => res.json())
  .then(json => console.log(json));
```

## Estructura del Proyecto

- **Backend**: Implementado en **Java** con **Spring Boot**.
- **Base de datos**: Persistencia en **MySQL**.
- **Documentación**: Basada en el estándar de rutas de FakeStoreAPI.

## Migración desde FakeStoreAPI

Este clon respeta todas las rutas y formatos de datos de FakeStoreAPI, lo que permite:
- Migrar proyectos existentes sin necesidad de modificar el código front-end.
- Usar datos reales almacenados en MySQL.

