# Implementación de un microservicio en un sistema distribuido

En un sistema bancario distribuido, el equipo de desarrollo necesita implementar un nuevo microservicio que gestione las solicitudes de transferencia de fondos entre cuentas. Este microservicio debe interactuar con otros servicios internos y externos, como el servicio de autenticación, el servicio de cuentas y el servicio de notificación. El microservicio debe asegurar la idempotencia de las solicitudes, manejar la consistencia eventual entre los servicios y proporcionar observabilidad para monitoreo y trazabilidad.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | microservicios |
| **Nivel** | junior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 20 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Gradle 8+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `gradle build` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Definición del microservicio

**Objetivo:** Establecer los límites y responsabilidades del microservicio de transferencias.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Identificar los actores involucrados en el proceso de transferencia de fondos.
- Definir las reglas de negocio para las transferencias, incluyendo validaciones y restricciones.
- Establecer los umbrales numéricos del dominio, como el límite de transferencias por segundo y el tiempo máximo de procesamiento.

**Entregable:** Documento que describe el microservicio, sus responsabilidades y las reglas de negocio.

<details>
<summary>Pistas de conocimiento</summary>

- Considerar los diferentes tipos de cuentas y sus políticas de transferencia.
- Evaluar los posibles estados de una transferencia y las transiciones entre ellos.

</details>

### Fase 2: Implementación de la idempotencia

**Objetivo:** Asegurar que las solicitudes de transferencia sean idempotentes.

**Tiempo estimado:** 6 horas

**Instrucciones:**

- Diseñar un mecanismo para garantizar que las solicitudes de transferencia se procesen de manera idempotente.
- Definir la clave de idempotencia y el modo de falla en caso de solicitudes duplicadas.
- Implementar la lógica para manejar solicitudes repetidas sin crear registros duplicados.

**Entregable:** Mecanismo de idempotencia implementado y documentado.

<details>
<summary>Pistas de conocimiento</summary>

- Investigar patrones de diseño comunes para lograr idempotencia en servicios distribuidos.
- Considerar el impacto de la idempotencia en la consistencia y el rendimiento del sistema.

</details>

### Fase 3: Manejo de la consistencia eventual

**Objetivo:** Implementar estrategias para manejar la consistencia eventual entre servicios.

**Tiempo estimado:** 6 horas

**Instrucciones:**

- Identificar los puntos de falla y los servicios involucrados en el proceso de transferencia.
- Diseñar estrategias para manejar la consistencia eventual, como compensaciones y reintentos.
- Implementar la lógica para manejar los estados inconsistentes y asegurar la eventual consistencia.

**Entregable:** Estrategias de consistencia eventual implementadas y documentadas.

<details>
<summary>Pistas de conocimiento</summary>

- Investigar patrones de diseño para lograr consistencia eventual en sistemas distribuidos.
- Evaluar los pros y contras de diferentes estrategias de compensación y reintento.

</details>

### Fase 4: Implementación de observabilidad

**Objetivo:** Proveer observabilidad para el microservicio mediante métricas, trazas y logs.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Identificar las métricas clave para monitorear el rendimiento y la salud del microservicio.
- Implementar trazas distribuidas para facilitar la trazabilidad de las solicitudes.
- Configurar logs para capturar eventos significativos y errores.

**Entregable:** Sistema de observabilidad implementado y documentado.

<details>
<summary>Pistas de conocimiento</summary>

- Investigar herramientas y prácticas comunes para implementar observabilidad en microservicios.
- Evaluar los beneficios y los costos de diferentes niveles de detalle en los logs y las trazas.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es un microservicio y cuáles son sus responsabilidades en un sistema distribuido?
- **paraQueSirve**: ¿Para qué sirve la idempotencia en las solicitudes de transferencia y cómo se implementa?
- **comoSeUsa**: ¿Cómo se usan las estrategias de consistencia eventual para manejar la eventual consistencia entre servicios?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar un microservicio y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de observabilidad en un microservicio y por qué son importantes?

## Criterios de Evaluacion

- Definición clara del microservicio y sus responsabilidades.
- Implementación de la idempotencia en las solicitudes de transferencia.
- Estrategias de consistencia eventual implementadas y documentadas.
- Sistema de observabilidad implementado y documentado.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
el comando de build o arranque canonico del stack elegido
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
