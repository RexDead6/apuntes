# Puntua 🏆

**Puntua** es una aplicación Android sencilla e intuitiva diseñada para llevar la puntuación de dos equipos en distintos tipos de juegos. Funciona exclusivamente como un marcador digital, permitiendo un control claro y dinámico del progreso de cada equipo durante la partida.

## 📱 Descripción

La aplicación permite asignar un nombre personalizado a cada equipo y definir una puntuación máxima para determinar al ganador. Durante el juego, el usuario puede sumar puntos a cualquiera de los equipos y corregir errores eliminando puntuaciones previamente registradas.

Cuando uno de los equipos alcanza la puntuación máxima configurada, la aplicación declara automáticamente al ganador y reinicia el ciclo para comenzar una nueva partida.

## ✨ Características principales

* Marcador para **dos equipos**
* Asignación de **nombres personalizados** a cada equipo
* Configuración de **puntuación máxima** para ganar
* Suma de puntos en tiempo real
* Eliminación de puntuaciones registradas previamente
* Detección automática del equipo ganador
* Reinicio automático del juego al finalizar una partida
* Interfaz simple, clara y fácil de usar

## 🏗️ Arquitectura del Proyecto

Puntua está desarrollada utilizando **Kotlin** y **Jetpack Compose**, siguiendo principios de arquitectura moderna recomendados para aplicaciones Android.

### 📐 Enfoque arquitectónico

La aplicación utiliza una arquitectura basada en **MVVM (Model–View–ViewModel)**, adaptada al paradigma declarativo de Jetpack Compose.

#### View (UI)

* Implementada completamente con **Jetpack Compose**
* Composables responsables únicamente de mostrar el estado del juego
* La interfaz se recompone automáticamente ante cambios de estado
* No contiene lógica de negocio

#### ViewModel

* Centraliza la lógica principal de la aplicación:

  * Manejo de la puntuación de ambos equipos
  * Validación de la puntuación máxima
  * Detección del ganador
  * Reinicio del estado del juego
* Expone el estado mediante `State` / `MutableState`
* Independiente de la UI, facilitando el mantenimiento y pruebas

#### Model

* Define las entidades básicas del dominio, como:

  * Equipos
  * Puntuaciones
  * Configuración del juego
* Clases simples, desacopladas del framework de UI

### 🔄 Manejo de estado

* El estado del juego se gestiona desde el `ViewModel`
* La UI observa el estado y se actualiza de forma reactiva
* No se utilizan layouts XML ni vistas tradicionales
* Separación clara entre presentación y lógica de negocio

### 🧩 Beneficios de la arquitectura

* Código limpio y organizado
* Fácil mantenimiento y escalabilidad
* UI declarativa y reactiva
* Alineada con las mejores prácticas actuales de Android
* Base sólida para futuras mejoras o nuevas funcionalidades

## ⚙️ Tecnologías utilizadas

* Kotlin
* Jetpack Compose
* Android SDK
* Material Design

## 🚀 Uso de la aplicación

1. Introduce el nombre de ambos equipos.
2. Define la puntuación máxima para ganar.
3. Inicia el juego y suma puntos a cada equipo.
4. Corrige puntuaciones si es necesario.
5. Al alcanzarse el puntaje máximo, se declara el ganador y el marcador se reinicia.

## 📸 Capturas de pantalla

*(Pendiente de agregar)*

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Puedes abrir issues o enviar pull requests para mejoras, correcciones o nuevas funcionalidades.

## 📄 Licencia

Este proyecto se distribuye bajo una licencia de código abierto y puede ser usado y modificado libremente.
