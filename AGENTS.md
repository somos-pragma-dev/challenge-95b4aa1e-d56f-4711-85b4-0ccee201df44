# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de un microservicio en un sistema distribuido**.

| | |
|---|---|
| Tema | microservicios |
| Nivel | junior-l2 |
| Chapter | Generico |
| Especialidad | Inferido del contexto |
| Stack | Kotlin 1.9 / Ktor 2.3 |
| Patron arquitectonico | microservicio reactivo con patrón hexagonal |
| Tiempo estimado | 20 horas |

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `el comando de build o arranque canonico del stack elegido` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `el comando de build o arranque canonico del stack elegido` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Definición del microservicio**: Documento que describe el microservicio, sus responsabilidades y las reglas de negocio.
- **Fase 2 — Implementación de la idempotencia**: Mecanismo de idempotencia implementado y documentado.
- **Fase 3 — Manejo de la consistencia eventual**: Estrategias de consistencia eventual implementadas y documentadas.
- **Fase 4 — Implementación de observabilidad**: Sistema de observabilidad implementado y documentado.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Boilerplate del stack (3)

Sin esto el proyecto no compila ni arranca. **Es tu trabajo crearlo**, y no toca nada de lo pedagogico: es andamiaje del stack.

- [ ] **Manifiesto de dependencias del stack elegido** — Sin un manifiesto de dependencias reconocible, ninguna herramienta de build sabe que instalar y el proyecto no arranca.
- [ ] **Punto de entrada del stack elegido** — Sin un punto de entrada reconocible, el runtime no tiene por donde arrancar la aplicacion.
- [ ] **Capa de interfaz (controller/handler)** — Sin una capa de interfaz explicita, no hay forma de invocar la logica de negocio desde afuera del proceso.

### Presentes (15)

- `build.gradle.kts`
- `src/main/kotlin/com/banco/transferencia/Application.kt`
- `src/main/kotlin/com/banco/transferencia/domain/Transferencia.kt`
- `src/main/kotlin/com/banco/transferencia/domain/TransferenciaRepository.kt`
- `src/main/kotlin/com/banco/transferencia/application/TransferenciaService.kt`
- `src/main/kotlin/com/banco/transferencia/infrastructure/clients/CuentasClient.kt`
- `src/main/kotlin/com/banco/transferencia/infrastructure/clients/NotificacionClient.kt`
- `src/main/kotlin/com/banco/transferencia/infrastructure/persistence/TransferenciaRepositoryImpl.kt`
- `src/main/kotlin/com/banco/transferencia/infrastructure/config/ResilienceConfig.kt`
- `src/main/kotlin/com/banco/transferencia/infrastructure/observability/Metrics.kt`
- `src/main/kotlin/com/banco/transferencia/infrastructure/observability/Traces.kt`
- `src/main/kotlin/com/banco/transferencia/infrastructure/observability/Logs.kt`
- `src/main/kotlin/com/banco/transferencia/infrastructure/TransferenciaController.kt`
- `src/test/kotlin/com/banco/transferencia/application/TransferenciaServiceTest.kt`
- `src/test/kotlin/com/banco/transferencia/infrastructure/clients/CuentasClientTest.kt`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/kotlin/com/banco/transferencia`
- `src/main/kotlin/com/banco/transferencia/application`
- `src/main/kotlin/com/banco/transferencia/domain`
- `src/main/kotlin/com/banco/transferencia/infrastructure`
- `src/main/kotlin/com/banco/transferencia/infrastructure/clients`
- `src/main/kotlin/com/banco/transferencia/infrastructure/config`
- `src/main/kotlin/com/banco/transferencia/infrastructure/persistence`
- `src/main/kotlin/com/banco/transferencia/infrastructure/observability`
- `src/test/kotlin/com/banco/transferencia`

## Verificacion

```bash
el comando de build o arranque canonico del stack elegido
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **microservicio reactivo con patrón hexagonal**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Brecha que el reto ataca: Sistema distribuido con Kotlin, Ktor, circuit breakers y observabilidad

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
