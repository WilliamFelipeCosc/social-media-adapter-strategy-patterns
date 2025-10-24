package adapter;

import api.InstagramAPI;
import interfaces.GerenciadorMidiaSocial;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
import strategy.EstrategiaAutenticacao;
import strategy.EstrategiaPublicacao;

/**
 * Adapter para a API do Instagram
 * Adapta a interface específica do Instagram para a interface unificada
 */
public class InstagramAdapter implements GerenciadorMidiaSocial {
    
    private final InstagramAPI instagramAPI;
    private final EstrategiaAutenticacao estrategiaAuth;
    private final EstrategiaPublicacao estrategiaPublicacao;
    
    public InstagramAdapter(EstrategiaAutenticacao estrategiaAuth,
                           EstrategiaPublicacao estrategiaPublicacao) {
        this.instagramAPI = new InstagramAPI();
        this.estrategiaAuth = estrategiaAuth;
        this.estrategiaPublicacao = estrategiaPublicacao;
    }
    
    @Override
    public boolean autenticar(Credenciais credenciais) {
        System.out.println("\n=== INSTAGRAM ADAPTER - Autenticação ===");
        
        // Usa a estratégia de autenticação
        boolean authSuccess = estrategiaAuth.autenticar(credenciais);
        
        if (authSuccess) {
            // Adapta para a API específica do Instagram
            return instagramAPI.autenticar(
                credenciais.getUsuario(), 
                credenciais.getAccessToken()
            );
        }
        return false;
    }
    
    @Override
    public Publicacao publicar(Conteudo conteudo) {
        System.out.println("\n=== INSTAGRAM ADAPTER - Publicação ===");
        
        if (!instagramAPI.estaAutenticado()) {
            throw new IllegalStateException("Não autenticado no Instagram");
        }
        
        // Usa a estratégia de publicação
        if (!estrategiaPublicacao.validar(conteudo)) {
            throw new IllegalArgumentException("Conteúdo inválido para publicação");
        }
        
        Conteudo processado = estrategiaPublicacao.processarConteudo(conteudo);
        
        // Adapta o conteúdo unificado para o formato do Instagram
        InstagramAPI.PostData post;
        
        if (conteudo.getTipo() == TipoConteudo.STORY) {
            // Publica como Story
            String mediaUrl = processado.getMidias() != null && !processado.getMidias().isEmpty() 
                ? processado.getMidias().get(0) : "default_story.jpg";
            post = instagramAPI.publicarStory(mediaUrl, "");
        } else {
            // Publica como post normal
            String mediaUrl = processado.getMidias() != null && !processado.getMidias().isEmpty() 
                ? processado.getMidias().get(0) : "default_post.jpg";
            post = instagramAPI.publicarMidia(
                processado.getTexto(),
                mediaUrl,
                processado.getHashtags()
            );
        }
        
        // Converte o resultado do Instagram para o formato unificado
        return new Publicacao(
            post.getId(),
            "Instagram",
            conteudo,
            post.getTimestamp(),
            StatusPublicacao.PUBLICADA,
            "https://instagram.com/p/" + post.getId()
        );
    }
    
    @Override
    public Publicacao agendar(Conteudo conteudo) {
        System.out.println("\n=== INSTAGRAM ADAPTER - Agendamento ===");
        System.out.println("Agendando post para: " + conteudo.getDataAgendamento());
        
        // Simula agendamento
        return new Publicacao(
            "scheduled_ig_" + System.currentTimeMillis(),
            "Instagram",
            conteudo,
            conteudo.getDataAgendamento(),
            StatusPublicacao.AGENDADA,
            "https://instagram.com/scheduled"
        );
    }
    
    @Override
    public boolean remover(String idPublicacao) {
        System.out.println("\n=== INSTAGRAM ADAPTER - Remoção ===");
        return instagramAPI.deletarPost(idPublicacao);
    }
    
    @Override
    public Estatisticas obterEstatisticas(String idPublicacao) {
        System.out.println("\n=== INSTAGRAM ADAPTER - Estatísticas ===");
        
        // Adapta os insights do Instagram para o formato unificado
        InstagramAPI.PostInsights insights = instagramAPI.obterInsights(idPublicacao);
        
        return new Estatisticas(
            insights.getPostId(),
            insights.getReach(),
            insights.getLikes(),
            insights.getSaves(),
            insights.getComments()
        );
    }
    
    @Override
    public List<Publicacao> listarPublicacoes() {
        System.out.println("\n=== INSTAGRAM ADAPTER - Listagem ===");
        
        List<Publicacao> publicacoes = new ArrayList<>();
        List<InstagramAPI.PostData> posts = instagramAPI.listarPosts();
        
        for (InstagramAPI.PostData post : posts) {
            List<String> midias = new ArrayList<>();
            if (post.getMediaUrl() != null) {
                midias.add(post.getMediaUrl());
            }
            
            TipoConteudo tipo = post.getTipo().equals("STORY") 
                ? TipoConteudo.STORY : TipoConteudo.IMAGEM;
            
            Conteudo conteudo = new Conteudo(
                post.getCaption(),
                midias,
                post.getTags(),
                post.getTimestamp(),
                tipo
            );
            
            publicacoes.add(new Publicacao(
                post.getId(),
                "Instagram",
                conteudo,
                post.getTimestamp(),
                StatusPublicacao.PUBLICADA,
                "https://instagram.com/p/" + post.getId()
            ));
        }
        
        return publicacoes;
    }
    
    @Override
    public String getNomePlataforma() {
        return "Instagram";
    }
    
    @Override
    public boolean validarConteudo(Conteudo conteudo) {
        // Instagram requer pelo menos uma imagem ou vídeo
        if (conteudo.getMidias() == null || conteudo.getMidias().isEmpty()) {
            System.out.println("Instagram requer pelo menos uma mídia");
            return false;
        }
        
        // Caption pode ter até 2200 caracteres
        if (conteudo.getTexto() != null && conteudo.getTexto().length() > 2200) {
            System.out.println("Caption muito longa (máx 2200 caracteres)");
            return false;
        }
        
        // Máximo de 30 hashtags
        if (conteudo.getHashtags() != null && conteudo.getHashtags().size() > 30) {
            System.out.println("Máximo de 30 hashtags no Instagram");
            return false;
        }
        
        System.out.println("Conteúdo válido para Instagram");
        return true;
    }
}
