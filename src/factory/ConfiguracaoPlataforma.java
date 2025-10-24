package factory;

/**
 * Classe que representa a configuração de uma plataforma
 */
public class ConfiguracaoPlataforma {
    private String nomePlataforma;
    private String tipoAutenticacao;
    private String tipoPublicacao;
    private boolean ativa;
    
    public ConfiguracaoPlataforma(String nomePlataforma, String tipoAutenticacao, 
                                 String tipoPublicacao, boolean ativa) {
        this.nomePlataforma = nomePlataforma;
        this.tipoAutenticacao = tipoAutenticacao;
        this.tipoPublicacao = tipoPublicacao;
        this.ativa = ativa;
    }
    
    // Getters e Setters
    public String getNomePlataforma() {
        return nomePlataforma;
    }
    
    public void setNomePlataforma(String nomePlataforma) {
        this.nomePlataforma = nomePlataforma;
    }
    
    public String getTipoAutenticacao() {
        return tipoAutenticacao;
    }
    
    public void setTipoAutenticacao(String tipoAutenticacao) {
        this.tipoAutenticacao = tipoAutenticacao;
    }
    
    public String getTipoPublicacao() {
        return tipoPublicacao;
    }
    
    public void setTipoPublicacao(String tipoPublicacao) {
        this.tipoPublicacao = tipoPublicacao;
    }
    
    public boolean isAtiva() {
        return ativa;
    }
    
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    
    @Override
    public String toString() {
        return "ConfiguracaoPlataforma{" +
                "nomePlataforma='" + nomePlataforma + '\'' +
                ", tipoAutenticacao='" + tipoAutenticacao + '\'' +
                ", tipoPublicacao='" + tipoPublicacao + '\'' +
                ", ativa=" + ativa +
                '}';
    }
}
