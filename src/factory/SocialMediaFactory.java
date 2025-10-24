package factory;

import adapter.*;
import interfaces.GerenciadorMidiaSocial;
import strategy.*;

/**
 * Factory Method para criar instâncias de gerenciadores de mídia social
 * Implementa o padrão Factory Method
 */
public class SocialMediaFactory {
    
    /**
     * Cria um gerenciador de mídia social baseado na plataforma
     * @param plataforma Nome da plataforma
     * @param estrategiaAuth Estratégia de autenticação
     * @param estrategiaPublicacao Estratégia de publicação
     * @return Gerenciador de mídia social
     */
    public static GerenciadorMidiaSocial criarGerenciador(
            String plataforma,
            EstrategiaAutenticacao estrategiaAuth,
            EstrategiaPublicacao estrategiaPublicacao) {
        
        System.out.println("\nFACTORY - Criando gerenciador para: " + plataforma);
        
        switch (plataforma.toUpperCase()) {
            case "TWITTER":
            case "X":
                return new TwitterAdapter(estrategiaAuth, estrategiaPublicacao);
            
            case "INSTAGRAM":
            case "IG":
                return new InstagramAdapter(estrategiaAuth, estrategiaPublicacao);
            
            case "LINKEDIN":
            case "LI":
                return new LinkedInAdapter(estrategiaAuth, estrategiaPublicacao);
            
            case "TIKTOK":
            case "TT":
                return new TikTokAdapter(estrategiaAuth, estrategiaPublicacao);
            
            default:
                throw new IllegalArgumentException("Plataforma não suportada: " + plataforma);
        }
    }
    
    /**
     * Cria um gerenciador com estratégias padrão
     * @param plataforma Nome da plataforma
     * @return Gerenciador de mídia social
     */
    public static GerenciadorMidiaSocial criarGerenciadorPadrao(String plataforma) {
        EstrategiaAutenticacao authStrategy = obterEstrategiaAuthPadrao(plataforma);
        EstrategiaPublicacao pubStrategy = new PublicacaoImediata();
        
        return criarGerenciador(plataforma, authStrategy, pubStrategy);
    }
    
    /**
     * Obtém a estratégia de autenticação padrão para cada plataforma
     */
    private static EstrategiaAutenticacao obterEstrategiaAuthPadrao(String plataforma) {
        switch (plataforma.toUpperCase()) {
            case "TWITTER":
            case "X":
                return new AutenticacaoOAuth2();
            
            case "INSTAGRAM":
                return new AutenticacaoApiKey();
            
            case "LINKEDIN":
                return new AutenticacaoOAuth2();
            
            case "TIKTOK":
                return new AutenticacaoJWT();
            
            default:
                return new AutenticacaoOAuth2();
        }
    }
    
    /**
     * Cria um gerenciador com base em configuração
     * @param config Configuração da plataforma
     * @return Gerenciador de mídia social
     */
    public static GerenciadorMidiaSocial criarDaConfiguracao(ConfiguracaoPlataforma config) {
        System.out.println("\nFACTORY - Criando da configuração: " + config.getNomePlataforma());
        
        EstrategiaAutenticacao authStrategy = criarEstrategiaAuth(config.getTipoAutenticacao());
        EstrategiaPublicacao pubStrategy = criarEstrategiaPublicacao(config.getTipoPublicacao());
        
        return criarGerenciador(config.getNomePlataforma(), authStrategy, pubStrategy);
    }
    
    /**
     * Cria estratégia de autenticação baseada no tipo
     */
    private static EstrategiaAutenticacao criarEstrategiaAuth(String tipo) {
        switch (tipo.toUpperCase()) {
            case "OAUTH2":
                return new AutenticacaoOAuth2();
            case "APIKEY":
                return new AutenticacaoApiKey();
            case "JWT":
                return new AutenticacaoJWT();
            default:
                return new AutenticacaoOAuth2();
        }
    }
    
    /**
     * Cria estratégia de publicação baseada no tipo
     */
    private static EstrategiaPublicacao criarEstrategiaPublicacao(String tipo) {
        switch (tipo.toUpperCase()) {
            case "IMEDIATA":
                return new PublicacaoImediata();
            case "AGENDADA":
                return new PublicacaoAgendada();
            default:
                return new PublicacaoImediata();
        }
    }
}
