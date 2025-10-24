package api;

import java.time.LocalDateTime;
import java.util.*;

/**
 * API simulada do TikTok
 * Adaptee - possui interface própria incompatível com a interface unificada
 */
public class TikTokAPI {
    
    private String sessionToken;
    private boolean logado = false;
    private Map<String, VideoData> videos = new HashMap<>();
    
    /**
     * Faz login na API do TikTok
     */
    public boolean fazerLogin(String usuario, String sessionToken) {
        System.out.println("Fazendo login no TikTok...");
        this.sessionToken = sessionToken;
        this.logado = true;
        System.out.println("Login no TikTok realizado: @" + usuario);
        return true;
    }
    
    /**
     * Posta um vídeo
     */
    public VideoData postarVideo(String descricao, String videoUrl, 
                                 List<String> hashtags, String musica) {
        if (!logado) {
            throw new IllegalStateException("Não logado no TikTok");
        }
        
        String videoId = "tt_" + UUID.randomUUID().toString().substring(0, 8);
        VideoData video = new VideoData(videoId, descricao, videoUrl, hashtags, 
                                        musica, LocalDateTime.now());
        videos.put(videoId, video);
        
        System.out.println("Vídeo postado no TikTok: " + videoId);
        return video;
    }
    
    /**
     * Remove um vídeo
     */
    public boolean deletarVideo(String videoId) {
        if (!logado) {
            throw new IllegalStateException("Não logado no TikTok");
        }
        
        boolean removido = videos.remove(videoId) != null;
        if (removido) {
            System.out.println("🗑️ Vídeo deletado: " + videoId);
        }
        return removido;
    }
    
    /**
     * Obtém estatísticas de um vídeo
     */
    public VideoStats obterStats(String videoId) {
        if (!logado) {
            throw new IllegalStateException("Não logado no TikTok");
        }
        
        // Simulação de estatísticas
        Random random = new Random();
        return new VideoStats(
            videoId,
            random.nextInt(50000),  // views
            random.nextInt(5000),   // likes
            random.nextInt(500),    // comments
            random.nextInt(1000)    // shares
        );
    }
    
    /**
     * Lista todos os vídeos
     */
    public List<VideoData> listarVideos() {
        return new ArrayList<>(videos.values());
    }
    
    /**
     * Verifica se está logado
     */
    public boolean estaLogado() {
        return logado;
    }
    
    // Classes internas para representar dados específicos do TikTok
    public static class VideoData {
        private String id;
        private String descricao;
        private String videoUrl;
        private List<String> hashtags;
        private String musica;
        private LocalDateTime dataUpload;
        
        public VideoData(String id, String descricao, String videoUrl, 
                        List<String> hashtags, String musica, LocalDateTime dataUpload) {
            this.id = id;
            this.descricao = descricao;
            this.videoUrl = videoUrl;
            this.hashtags = hashtags;
            this.musica = musica;
            this.dataUpload = dataUpload;
        }
        
        // Getters
        public String getId() { return id; }
        public String getDescricao() { return descricao; }
        public String getVideoUrl() { return videoUrl; }
        public List<String> getHashtags() { return hashtags; }
        public String getMusica() { return musica; }
        public LocalDateTime getDataUpload() { return dataUpload; }
    }
    
    public static class VideoStats {
        private String videoId;
        private int views;
        private int likes;
        private int comments;
        private int shares;
        
        public VideoStats(String videoId, int views, int likes, int comments, int shares) {
            this.videoId = videoId;
            this.views = views;
            this.likes = likes;
            this.comments = comments;
            this.shares = shares;
        }
        
        // Getters
        public String getVideoId() { return videoId; }
        public int getViews() { return views; }
        public int getLikes() { return likes; }
        public int getComments() { return comments; }
        public int getShares() { return shares; }
    }
}
