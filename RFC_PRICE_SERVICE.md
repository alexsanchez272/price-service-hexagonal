# RFC – Servicio de Consulta de Precios de Productos

## 1. Objetivo
Desarrollar un servicio REST basado en Spring Boot que permita consultar el precio final aplicable a un producto, considerando la marca, el producto y la fecha de aplicación. 
El servicio obtiene la tarifa (price list) correcta aplicable según reglas de solapamiento de periodos y prioridad, y se apoya en una base de datos en memoria H2, 
inicializada con datos de ejemplo.

## 2. Alcance
**Dominio:** Comercio electrónico de la compañía.

### Funcionalidad Principal:
- Recibir como entrada:
    - Fecha y hora de consulta
    - Identificador del producto
    - Identificador de la marca
- Buscar en la tabla de precios la tarifa vigente (aplicando la regla de mayor prioridad en caso de solapamiento).
- Devolver los datos de la tarifa aplicada, incluyendo:
    - Identificador del producto
    - Marca
    - Tarifa aplicable
    - Periodo de validez (startDate y endDate)
    - Precio final y moneda

### Datos de Entrada:
```bash
brandId: Identificador de la marca (p.ej., 1 para ZARA)
productId: Identificador del producto (p.ej., 35455)
startDate: Fecha y hora de consulta (formato yyyy-MM-dd HH:mm:ss)
```

### Datos de Salida:
```bash
id (opcional, identificador interno)
brandId
startDate y endDate (periodo en el que la tarifa es aplicable)
priceList
productId
priority
price
currency
```

## 3. Arquitectura y Diseño
La solución se desarrolla aplicando una arquitectura Hexagonal y principios de Clean Architecture, 
asegurando la separación de la lógica de negocio, la persistencia y la exposición de servicios.

## 4. Flujo de Ejecución y Lógica de Negocio
### Manejo de la Petición:
- El `PriceController` recibe una solicitud `GET` en `/api/prices` con `brandId`, `productId` y `startDate` como parámetros.

### Llamada al Servicio:
- El controlador invoca `PriceService`, que registra el inicio del proceso y llama a `GetPriceUseCase`.

### Consulta en la Base de Datos:
- El caso de uso utiliza `PricePersistenceAdapter` para interactuar con `PriceRepository`.
- La consulta filtra por `brandId` y `productId`, asegurando que `startDate` esté dentro del periodo de validez.
- Se ordenan los resultados por `priority` en orden descendente para seleccionar la tarifa de mayor prioridad.

### Generación de Respuesta:
- Si se encuentra una tarifa, se retorna como un objeto `Price`.
- Si no se encuentra, se lanza `PriceNotFoundException`, que es manejada por `GlobalExceptionHandler`, devolviendo un error `404`.

## 5. Estrategia de Pruebas
- Pruebas Unitarias
- Pruebas de Integración
- Pruebas End-to-End (E2E)

### Casos de Prueba Requeridos:
```bash
Test 1: Consulta a las 10:00 del 14/06/2020 (Esperado: PriceList=1, PRICE=35.50)
Test 2: Consulta a las 16:00 del 14/06/2020 (Esperado: PriceList=2, PRICE=25.45)
Test 3: Consulta a las 21:00 del 14/06/2020 (Esperado: PriceList=1, PRICE=35.50)
Test 4: Consulta a las 10:00 del 15/06/2020 (Esperado: PriceList=3, PRICE=30.50)
Test 5: Consulta a las 21:00 del 16/06/2020 (Esperado: PriceList=4, PRICE=38.95)
```

## 6. Plan de Implementación
```bash
Fase 1: Diseño de modelos, casos de uso y estructura de paquetes.
Fase 2: Implementación del dominio, capa de aplicación, repositorios y controlador REST.
Fase 3: Configuración de la base de datos y documentación API.
Fase 4: Desarrollo y ejecución de pruebas.
Fase 5: Revisión de código y despliegue.
```

## 7. Conclusión
Este RFC proporciona un enfoque estructurado para la implementación de un servicio de consulta de precios **escalable, testeable y eficiente**. Con **arquitectura modular, código limpio y pruebas robustas**, se garantiza su mantenibilidad y extensibilidad a largo plazo.

