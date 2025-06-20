# Universidad de Antioquia

**Facultad de Ingeniería**  
**Departamento de Ingeniería de Sistemas**  
**Computación Móvil - 2025-1**

## Laboratorio 2

**Workers y consumo de servicios REST**

### Objetivo general

Hacer uso de workers para generar una tarea recurrente en segundo plano que realice consumo de un servicio REST.

### Objetivos específicos

- Usar módulo de navigation de Composer.
- Usar librerías para consumo de servicios web como Retrofit o Volley.
- Uso de workers.

### Condiciones de entrega

- **Fecha de publicación:** 07 de junio 2025.
- **Entrega del APK firmado:** a más tardar el 21 de junio 2025 a las 7 am.
- El código debe estar en el repositorio asignado a la fecha de entrega.
- La entrega posterior tendrá un castigo en la nota definitiva.

---

## Actividad

1. Cree un repositorio (Github o Bitbucket) llamado:  
   **LabsCMAAAAS-GrXX**, donde:

   - AAAA = año en curso
   - S = semestre
   - XX = número del grupo (01, 02, …)  
     Ejemplo: `LabsCM20231-Gr01`

2. Cree un nuevo proyecto (new project with empty activity) con nombre:  
   **LabsAAAAS-GrXX**, y edite el nombre del paquete a:  
   `co.edu.udea.compumovil.grXX_AAAAS.lab2`

Renombre el módulo `app` a **Lab1-UI**:

- Click derecho sobre `app` → Refactor → Rename
- O use `Shift + F6` y seleccione Rename Module.

3. Agregue su proyecto al repositorio:

   - **Línea de comandos:**
     ```bash
     cd <ruta>/LabsAAAAS-GrXX/
     git add .
     git commit -m "Lab1 first commit"
     git remote add origin <url del repo>
     git push -u origin master
     ```
   - **Android Studio:**
     - VCS → Enable Version Control Integration → seleccione Git
     - Click derecho sobre el proyecto `Lab1-UI` → Git → Add
     - Presione `Ctrl + K` → mensaje: "Lab1 First commit" → Commit
     - Presione `Ctrl + Shift + K` para hacer push → defina el remote.

4. Realice el codelab Cómo obtener datos de Internet:  
   https://developer.android.com/courses/android-basics-compose/unit-5?hl=es-419

5. Realizar codelab de corrutinas:  
   https://developer.android.com/codelabs/basic-android-kotlin-compose-coroutines-android-studio?hl=es-419#0

6. Codelab worker:  
   https://developer.android.com/codelabs/basic-android-kotlin-compose-workmanager?hl=es-419#10

7. Correr los ejemplos del repositorio:  
   [Jetpack Compose Samples](https://github.com/android/compose-samples)

8. Implemente alguna funcionalidad del ejemplo seleccionado usando un worker.

9. Haga uso del consumo de un servicio web tipo REST para complementar algunos datos del ejemplo seleccionado.

---

## Porcentajes de evaluación

- **30%** Uso de `IntentService`, `Service` o `Worker`
- **40%** Implementación de al menos 2 funcionalidades del ejemplo seleccionado
- **20%** Realizar el punto 7
- **10%** Aspecto del diseño y funcionamiento

---

## Notas importantes

- La aplicación debe usar **Material Design**, brindando soporte a versiones anteriores desde Android 5.0.
- Debe estar desarrollada para **2 idiomas** de su elección.
- Agregue un `android:id` a todos los elementos definidos en XML.
- Realice commits periódicos según los avances.
- Cada integrante debe enviar sus propios commits.
- En la carpeta del laboratorio debe incluirse un archivo con:
  - URL del repositorio
  - Nombre y cuenta de GitHub de cada integrante.

---

## Material de ayuda

- [Guía de UI Android](http://developer.android.com/intl/es/guide/topics/ui/controls.html)
- [Mockable.io](https://www.mockable.io/)
- [MockAPI](https://mockapi.io/)
- [Uplabs](https://www.uplabs.com/)
