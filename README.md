# Sistema de Gerenciamento de Redes Sociais

## 📋 Descrição do Projeto

Sistema unificado de gerenciamento de múltiplas redes sociais utilizando os padrões de design **Adapter**, **Strategy** e **Factory Method**. O projeto demonstra competência em design de software, tratamento de complexidades de integração e criação de arquiteturas flexíveis para APIs heterogêneas.

## 🎯 Objetivos

- Criar uma interface unificada para gerenciar múltiplas plataformas de mídia social
- Implementar adapters para diferentes APIs (Twitter, Instagram, LinkedIn, TikTok)
- Utilizar estratégias diferentes para autenticação e publicação
- Implementar factory method para criação dinâmica de gerenciadores
- Configuração por ambiente (Desenvolvimento, Homologação, Produção)

## 🏗️ Arquitetura

### Padrões de Design Implementados

#### 1. **Adapter Pattern**
Adapta as diferentes APIs de redes sociais (Twitter, Instagram, LinkedIn, TikTok) para uma interface unificada.

```
GerenciadorMidiaSocial (Target Interface)
    ↓
TwitterAdapter, InstagramAdapter, LinkedInAdapter, TikTokAdapter
    ↓
TwitterAPI, InstagramAPI, LinkedInAPI, TikTokAPI (Adaptees)
```

#### 2. **Strategy Pattern**
Permite trocar algoritmos de autenticação e publicação em tempo de execução.

**Estratégias de Autenticação:**
- `AutenticacaoOAuth2` - OAuth 2.0
- `AutenticacaoApiKey` - API Key
- `AutenticacaoJWT` - JWT Token

**Estratégias de Publicação:**
- `PublicacaoImediata` - Publica imediatamente
- `PublicacaoAgendada` - Agenda publicação futura

#### 3. **Factory Method Pattern**
Cria instâncias de gerenciadores de mídia social de forma centralizada e configurável.

```java
SocialMediaFactory.criarGerenciador(plataforma, estrategiaAuth, estrategiaPublicacao)
SocialMediaFactory.criarGerenciadorPadrao(plataforma)
SocialMediaFactory.criarDaConfiguracao(config)
```

## 📁 Estrutura do Projeto

```
src/
├── adapter/
│   ├── TwitterAdapter.java
│   ├── InstagramAdapter.java
│   ├── LinkedInAdapter.java
│   └── TikTokAdapter.java
├── api/
│   ├── TwitterAPI.java
│   ├── InstagramAPI.java
│   ├── LinkedInAPI.java
│   └── TikTokAPI.java
├── config/
│   └── GerenciadorConfiguracao.java
├── factory/
│   ├── SocialMediaFactory.java
│   └── ConfiguracaoPlataforma.java
├── interfaces/
│   └── GerenciadorMidiaSocial.java
├── modelo/
│   ├── Conteudo.java
│   ├── Publicacao.java
│   ├── Estatisticas.java
│   ├── Credenciais.java
│   ├── TipoConteudo.java
│   └── StatusPublicacao.java
├── strategy/
│   ├── EstrategiaAutenticacao.java
│   ├── AutenticacaoOAuth2.java
│   ├── AutenticacaoApiKey.java
│   ├── AutenticacaoJWT.java
│   ├── EstrategiaPublicacao.java
│   ├── PublicacaoImediata.java
│   └── PublicacaoAgendada.java
└── Main.java
```

## 🚀 Como Executar

### Compilar o projeto

```bash
cd /home/williamf/estudos/java/atividade_adapter_strategy/src
javac Main.java
```

### Executar o programa

```bash
java Main
```

## 🎓 Conceitos Demonstrados

### Adapter Pattern
- Conversão de interfaces incompatíveis
- Adaptação de múltiplas APIs heterogêneas
- Encapsulamento de complexidade

### Strategy Pattern
- Troca de algoritmos em runtime
- Separação de comportamento de implementação
- Flexibilidade na escolha de estratégias

### Factory Method
- Criação centralizada de objetos
- Configuração dinâmica
- Desacoplamento de código cliente

### Boas Práticas
- SOLID Principles
- Interface segregation
- Dependency Inversion
- Open/Closed Principle
- Single Responsibility

## 📝 Observações

- Este é um projeto educacional que **simula** as APIs reais
- As autenticações e publicações são simuladas para fins de demonstração
- Em produção, seria necessário implementar as chamadas reais às APIs
- As estatísticas são geradas aleatoriamente para demonstração

## 🤝 Contribuições

Este projeto foi desenvolvido como atividade acadêmica para demonstrar competência em:
- Design Patterns
- Arquitetura de Software
- Integração de APIs
- Programação Orientada a Objetos em Java

## 📄 Licença

Projeto educacional - Livre para uso acadêmico

---

**Desenvolvido por:** William Felipe Coscodai.  
**Data:** Outubro 2025  
**Disciplina:** Padrões de Software
