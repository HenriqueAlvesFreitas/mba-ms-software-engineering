# Microsserviço – Documentação de Design Patterns

Este microsserviço foi desenvolvido aplicando princípios **SOLID**, **GRASP** e alguns **Design Patterns clássicos**, com foco em baixo acoplamento, alta coesão e facilidade de manutenção.  
Abaixo está o mapeamento dos patterns utilizados, com exemplos claros de **onde e como** eles aparecem no código.

---

## SOLID

### Single Responsibility Principle (SRP)
Cada classe possui **uma única responsabilidade bem definida**.

**Onde foi aplicado:**
- `UserService`: orquestra regras de negócio de usuário
- `UserRepository`: responsável apenas por persistência
- `UserSearchStrategy`: responsável apenas pela lógica de busca

```java
@Service
public class UserService {
    public Page<User> getAll(UserSearchFilter filter, Pageable pageable) {
        UserSearchStrategy strategy = strategyResolver.resolve(filter);
        return strategy.search(filter, pageable);
    }
}
```

---

### Open/Closed Principle (OCP)
Novas regras de busca podem ser adicionadas **sem alterar código existente**, apenas criando novas estratégias.

**Onde foi aplicado:**
- Hierarquia de estratégias de busca de usuário

```java
public interface UserSearchStrategy {
    Page<User> search(UserSearchFilter filter, Pageable pageable);
}
```

---

### Dependency Inversion Principle (DIP)
Camadas de alto nível dependem de **abstrações**, não de implementações concretas.

**Onde foi aplicado:**
- `UserService` depende de `UserSearchStrategy`
- Repositórios expostos como interfaces

```java
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserSearchStrategyResolver strategyResolver;
}
```

---

## GRASP

### Controller
Responsabilidade de receber requisições e delegar para a camada correta.

**Onde foi aplicado:**
- `UserController`

```java
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public ResponseEntity<?> getAll(...) {
        return userService.getAll(...);
    }
}
```

---

### Information Expert
A responsabilidade fica com quem **possui a informação necessária**.

**Onde foi aplicado:**
- `UserRepository` conhece os detalhes de persistência
- `User` encapsula regras do próprio domínio

---

### Low Coupling & High Cohesion
Cada módulo conhece o mínimo necessário sobre os outros.

**Onde foi aplicado:**
- Separação clara entre `controller`, `service`, `strategy`, `repository` e `domain`

---

## Design Patterns

### Strategy Pattern
Usado para encapsular diferentes formas de busca de usuários (por nome, email, telefone ou combinação).

**Onde foi aplicado:**
- `UserSearchStrategy`
- Implementações como:
  - `SearchByNameStrategy`
  - `SearchByEmailStrategy`
  - `SearchMixedStrategy`

```java
public class SearchByEmailStrategy implements UserSearchStrategy {
    public Page<User> search(UserSearchFilter filter, Pageable pageable) {
        return repository.findByEmailContaining(filter.getEmail(), pageable);
    }
}
```

---

### Specification Pattern
Permite construir filtros dinâmicos sem criar múltiplas queries fixas.

**Onde foi aplicado:**
- `UserSpecification`
- Uso do `JpaSpecificationExecutor`

```java
public static Specification<User> hasName(String name) {
    return (root, query, cb) ->
        name == null ? null :
        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
}
```

---

### Data Transfer Object (DTO)
Reduz acoplamento e evita exposição direta da entidade.

**Onde foi aplicado:**
- `UserResponseDTO`
- Mapeamento no service ou controller

---

## Conclusão

O projeto aplica conscientemente princípios **SOLID**, **GRASP** e os patterns **Strategy**, **Specification** e **DTO**, garantindo:
- Facilidade de extensão
- Código legível e testável
- Arquitetura alinhada a boas práticas de microsserviços

Toda a documentação foi escrita em **Markdown**, conforme solicitado.
