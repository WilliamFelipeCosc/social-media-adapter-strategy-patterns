package adapter;

import api.TikTokAPI;
import interfaces.GerenciadorMidiaSocial;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
import strategy.EstrategiaAutenticacao;
import strategy.EstrategiaPublicacao;

/**
 * Adapter para a API do TikTok
 * Adapta a interface específica do TikTok para a interface unificada
 */
public class TikTokAdapter implements GerenciadorMidiaSocial {
    
    private final TikTokAPI tikTokAPI;
    private final EstrategiaAutenticacao estrategiaAuth;
    private final EstrategiaPublicacao estrategiaPublicacao;
    
    public TikTokAdapter(EstrategiaAutenticacao estrategiaAuth,
                        EstrategiaPublicacao estrategiaPublicacao) {
        this.tikTokAPI = new TikTokAPI();
        this.estrategiaAuth = estrategiaAuth;
        this.estrategiaPublicacao = estrategiaPublicacao;
    }
    
    @Override
    public boolean autenticar(Credenciais credenciais) {
        System.out.println("\n=== TIKTOK ADAPTER - Autenticação ===");
        
        // Usa a estratégia de autenticação
        boolean authSuccess = estrategiaAuth.autenticar(credenciais);
        
        if (authSuccess) {
            // Adapta para a API específica do TikTok
            return tikTokAPI.fazerLogin(
                credenciais.getUsuario(),
                credenciais.getAccessToken()
            );
        }
        return false;
    }
    
    @Override
    public Publicacao publicar(Conteudo conteudo) {
        System.out.println("\n=== TIKTOK ADAPTER - Publicação ===");
        
        if (!tikTokAPI.estaLogado()) {
            throw new IllegalStateException("Não logado no TikTok");
        }
        
        // Usa a estratégia de publicação
        if (!estrategiaPublicacao.validar(conteudo)) {
            throw new IllegalArgumentException("Conteúdo inválido para publicação");
        }
        
        Conteudo processado = estrategiaPublicacao.processarConteudo(conteudo);
        
        // Adapta o conteúdo unificado para o formato do TikTok
        String videoUrl = processado.getMidias() != null && !processado.getMidias().isEmpty() 
            ? processado.getMidias().get(0) : "default_video.mp4";
        
        TikTokAPI.VideoData video = tikTokAPI.postarVideo(
            processado.getTexto(),
            videoUrl,
            processado.getHashtags(),
            "trending_sound.mp3"
        );
        
        // Converte o resultado do TikTok para o formato unificado
        return new Publicacao(
            video.getId(),
            "TikTok",
            conteudo,
            video.getDataUpload(),
            StatusPublicacao.PUBLICADA,
            "https://tiktok.com/@user/video/" + video.getId()
        );
    }
    
    @Override
    public Publicacao agendar(Conteudo conteudo) {
        System.out.println("\n=== TIKTOK ADAPTER - Agendamento ===");
        System.out.println("Agendando vídeo para: " + conteudo.getDataAgendamento());
        
        // Simula agendamento
        return new Publicacao(
            "scheduled_tt_" + System.currentTimeMillis(),
            "TikTok",
            conteudo,
            conteudo.getDataAgendamento(),
            StatusPublicacao.AGENDADA,
            "https://tiktok.com/scheduled"
        );
    }
    
    @Override
    public boolean remover(String idPublicacao) {
        System.out.println("\n=== TIKTOK ADAPTER - Remoção ===");
        return tikTokAPI.deletarVideo(idPublicacao);
    }
    
    @Override
    public Estatisticas obterEstatisticas(String idPublicacao) {
        System.out.println("\n=== TIKTOK ADAPTER - Estatísticas ===");
        
        // Adapta as stats do TikTok para o formato unificado
        TikTokAPI.VideoStats stats = tikTokAPI.obterStats(idPublicacao);
        
        return new Estatisticas(
            stats.getVideoId(),
            stats.getViews(),
            stats.getLikes(),
            stats.getShares(),
            stats.getComments()
        );
    }
    
    @Override
    public List<Publicacao> listarPublicacoes() {
        System.out.println("\n=== TIKTOK ADAPTER - Listagem ===");
        
        List<Publicacao> publicacoes = new ArrayList<>();
        List<TikTokAPI.VideoData> videos = tikTokAPI.listarVideos();
        
        for (TikTokAPI.VideoData video : videos) {
            List<String> midias = new ArrayList<>();
            if (video.getVideoUrl() != null) {
                midias.add(video.getVideoUrl());
            }
            
            Conteudo conteudo = new Conteudo(
                video.getDescricao(),
                midias,
                video.getHashtags(),
                video.getDataUpload(),
                TipoConteudo.VIDEO
            );
            
            publicacoes.add(new Publicacao(
                video.getId(),
                "TikTok",
                conteudo,
                video.getDataUpload(),
                StatusPublicacao.PUBLICADA,
                "https://tiktok.com/@user/video/" + video.getId()
            ));
        }
        
        return publicacoes;
    }
    
    @Override
    public String getNomePlataforma() {
        return "TikTok";
    }
    
    @Override
    public boolean validarConteudo(Conteudo conteudo) {
        // TikTok requer vídeo
        if (conteudo.getMidias() == null || conteudo.getMidias().isEmpty()) {
            System.out.println("TikTok requer um vídeo");
            return false;
        }
        
        if (conteudo.getTipo() != TipoConteudo.VIDEO && conteudo.getTipo() != TipoConteudo.REELS) {
            System.out.println("TikTok aceita apenas vídeos");
            return false;
        }
        
        // Descrição pode ter até 2200 caracteres
        if (conteudo.getTexto() != null && conteudo.getTexto().length() > 2200) {
            System.out.println("Descrição muito longa (máx 2200 caracteres)");
            return false;
        }
        
        System.out.println("Conteúdo válido para TikTok");
        return true;
    }
}
