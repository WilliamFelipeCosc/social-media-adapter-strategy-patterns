package api;

import java.time.LocalDateTime;
import java.util.*;

/**
 * API simulada do LinkedIn
 * Adaptee - possui interface própria incompatível com a interface unificada
 */
public class LinkedInAPI {
    
    private String authToken;
    private boolean conectado = false;
    private Map<String, ShareData> shares = new HashMap<>();
    
    /**
     * Estabelece conexão com LinkedIn
     */
    public boolean estabelecerConexao(String clientId, String clientSecret, String authToken) {
        System.out.println("Estabelecendo conexão com LinkedIn...");
        this.authToken = authToken;
        this.conectado = true;
        System.out.println("Conectado ao LinkedIn com sucesso!");
        return true;
    }
    
    /**
     * Compartilha conteúdo
     */
    public ShareData compartilharConteudo(String texto, String linkUrl, String imagemUrl) {
        if (!conectado) {
            throw new IllegalStateException("Não conectado ao LinkedIn");
        }
        
        String shareId = "li_" + UUID.randomUUID().toString().substring(0, 8);
        ShareData share = new ShareData(shareId, texto, linkUrl, imagemUrl, 
                                        LocalDateTime.now(), "SHARE");
        shares.put(shareId, share);
        
        System.out.println("Conteúdo compartilhado no LinkedIn: " + shareId);
        return share;
    }
    
    /**
     * Publica artigo
     */
    public ShareData publicarArtigo(String titulo, String conteudo, String imagemCapa) {
        if (!conectado) {
            throw new IllegalStateException("Não conectado ao LinkedIn");
        }
        
        String shareId = "article_" + UUID.randomUUID().toString().substring(0, 8);
        ShareData share = new ShareData(shareId, titulo + "\n" + conteudo, "", 
                                        imagemCapa, LocalDateTime.now(), "ARTICLE");
        shares.put(shareId, share);
        
        System.out.println("Artigo publicado no LinkedIn: " + shareId);
        return share;
    }
    
    /**
     * Remove compartilhamento
     */
    public boolean removerShare(String shareId) {
        if (!conectado) {
            throw new IllegalStateException("Não conectado ao LinkedIn");
        }
        
        boolean removido = shares.remove(shareId) != null;
        if (removido) {
            System.out.println("Share removido: " + shareId);
        }
        return removido;
    }
    
    /**
     * Obtém analytics
     */
    public ShareAnalytics obterAnalytics(String shareId) {
        if (!conectado) {
            throw new IllegalStateException("Não conectado ao LinkedIn");
        }
        
        // Simulação de analytics
        Random random = new Random();
        return new ShareAnalytics(
            shareId,
            random.nextInt(3000),   // views
            random.nextInt(300),    // reactions
            random.nextInt(50),     // comments
            random.nextInt(100)     // shares
        );
    }
    
    /**
     * Lista todos os shares
     */
    public List<ShareData> listarShares() {
        return new ArrayList<>(shares.values());
    }
    
    /**
     * Verifica se está conectado
     */
    public boolean estaConectado() {
        return conectado;
    }
    
    // Classes internas para representar dados específicos do LinkedIn
    public static class ShareData {
        private String id;
        private String texto;
        private String linkUrl;
        private String imagemUrl;
        private LocalDateTime dataPublicacao;
        private String tipo;
        
        public ShareData(String id, String texto, String linkUrl, String imagemUrl,
                        LocalDateTime dataPublicacao, String tipo) {
            this.id = id;
            this.texto = texto;
            this.linkUrl = linkUrl;
            this.imagemUrl = imagemUrl;
            this.dataPublicacao = dataPublicacao;
            this.tipo = tipo;
        }
        
        // Getters
        public String getId() { return id; }
        public String getTexto() { return texto; }
        public String getLinkUrl() { return linkUrl; }
        public String getImagemUrl() { return imagemUrl; }
        public LocalDateTime getDataPublicacao() { return dataPublicacao; }
        public String getTipo() { return tipo; }
    }
    
    public static class ShareAnalytics {
        private String shareId;
        private int views;
        private int reactions;
        private int comments;
        private int shares;
        
        public ShareAnalytics(String shareId, int views, int reactions, 
                             int comments, int shares) {
            this.shareId = shareId;
            this.views = views;
            this.reactions = reactions;
            this.comments = comments;
            this.shares = shares;
        }
        
        // Getters
        public String getShareId() { return shareId; }
        public int getViews() { return views; }
        public int getReactions() { return reactions; }
        public int getComments() { return comments; }
        public int getShares() { return shares; }
    }
}
