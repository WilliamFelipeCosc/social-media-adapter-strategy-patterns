package config;

import factory.ConfiguracaoPlataforma;
import java.util.*;

/**
 * Gerenciador de configurações por ambiente
 * Implementa configuração dinâmica baseada no ambiente
 */
public class GerenciadorConfiguracao {
    
    private static GerenciadorConfiguracao instance;
    private String ambienteAtual;
    private Map<String, Map<String, ConfiguracaoPlataforma>> configuracoesPorAmbiente;
    
    private GerenciadorConfiguracao() {
        configuracoesPorAmbiente = new HashMap<>();
        inicializarConfiguracoes();
        ambienteAtual = "DESENVOLVIMENTO"; // padrão
    }
    
    /**
     * Singleton - obtém instância única
     */
    public static GerenciadorConfiguracao getInstance() {
        if (instance == null) {
            instance = new GerenciadorConfiguracao();
        }
        return instance;
    }
    
    /**
     * Inicializa configurações para cada ambiente
     */
    private void inicializarConfiguracoes() {
        // DESENVOLVIMENTO
        Map<String, ConfiguracaoPlataforma> dev = new HashMap<>();
        dev.put("TWITTER", new ConfiguracaoPlataforma("TWITTER", "OAUTH2", "IMEDIATA", true));
        dev.put("INSTAGRAM", new ConfiguracaoPlataforma("INSTAGRAM", "APIKEY", "IMEDIATA", true));
        dev.put("LINKEDIN", new ConfiguracaoPlataforma("LINKEDIN", "OAUTH2", "IMEDIATA", false));
        dev.put("TIKTOK", new ConfiguracaoPlataforma("TIKTOK", "JWT", "IMEDIATA", true));
        configuracoesPorAmbiente.put("DESENVOLVIMENTO", dev);
        
        // HOMOLOGAÇÃO
        Map<String, ConfiguracaoPlataforma> homolog = new HashMap<>();
        homolog.put("TWITTER", new ConfiguracaoPlataforma("TWITTER", "OAUTH2", "AGENDADA", true));
        homolog.put("INSTAGRAM", new ConfiguracaoPlataforma("INSTAGRAM", "APIKEY", "AGENDADA", true));
        homolog.put("LINKEDIN", new ConfiguracaoPlataforma("LINKEDIN", "OAUTH2", "AGENDADA", true));
        homolog.put("TIKTOK", new ConfiguracaoPlataforma("TIKTOK", "JWT", "AGENDADA", true));
        configuracoesPorAmbiente.put("HOMOLOGACAO", homolog);
        
        // PRODUÇÃO
        Map<String, ConfiguracaoPlataforma> prod = new HashMap<>();
        prod.put("TWITTER", new ConfiguracaoPlataforma("TWITTER", "OAUTH2", "AGENDADA", true));
        prod.put("INSTAGRAM", new ConfiguracaoPlataforma("INSTAGRAM", "OAUTH2", "AGENDADA", true));
        prod.put("LINKEDIN", new ConfiguracaoPlataforma("LINKEDIN", "OAUTH2", "AGENDADA", true));
        prod.put("TIKTOK", new ConfiguracaoPlataforma("TIKTOK", "JWT", "AGENDADA", true));
        configuracoesPorAmbiente.put("PRODUCAO", prod);
    }
    
    /**
     * Define o ambiente atual
     */
    public void setAmbiente(String ambiente) {
        if (!configuracoesPorAmbiente.containsKey(ambiente.toUpperCase())) {
            throw new IllegalArgumentException("Ambiente não configurado: " + ambiente);
        }
        this.ambienteAtual = ambiente.toUpperCase();
        System.out.println("Ambiente alterado para: " + ambienteAtual);
    }
    
    /**
     * Obtém o ambiente atual
     */
    public String getAmbienteAtual() {
        return ambienteAtual;
    }
    
    /**
     * Obtém configuração de uma plataforma no ambiente atual
     */
    public ConfiguracaoPlataforma obterConfiguracao(String plataforma) {
        Map<String, ConfiguracaoPlataforma> configs = configuracoesPorAmbiente.get(ambienteAtual);
        
        if (configs == null) {
            throw new IllegalStateException("Configurações não encontradas para ambiente: " + ambienteAtual);
        }
        
        ConfiguracaoPlataforma config = configs.get(plataforma.toUpperCase());
        
        if (config == null) {
            throw new IllegalArgumentException("Plataforma não configurada: " + plataforma);
        }
        
        return config;
    }
    
    /**
     * Obtém todas as plataformas ativas no ambiente atual
     */
    public List<ConfiguracaoPlataforma> obterPlataformasAtivas() {
        List<ConfiguracaoPlataforma> ativas = new ArrayList<>();
        Map<String, ConfiguracaoPlataforma> configs = configuracoesPorAmbiente.get(ambienteAtual);
        
        for (ConfiguracaoPlataforma config : configs.values()) {
            if (config.isAtiva()) {
                ativas.add(config);
            }
        }
        
        return ativas;
    }
    
    /**
     * Lista todas as plataformas configuradas
     */
    public List<String> listarPlataformas() {
        Map<String, ConfiguracaoPlataforma> configs = configuracoesPorAmbiente.get(ambienteAtual);
        return new ArrayList<>(configs.keySet());
    }
    
    /**
     * Adiciona ou atualiza configuração de plataforma
     */
    public void adicionarConfiguracao(String ambiente, ConfiguracaoPlataforma config) {
        String ambienteUpper = ambiente.toUpperCase();
        
        if (!configuracoesPorAmbiente.containsKey(ambienteUpper)) {
            configuracoesPorAmbiente.put(ambienteUpper, new HashMap<>());
        }
        
        Map<String, ConfiguracaoPlataforma> configs = configuracoesPorAmbiente.get(ambienteUpper);
        configs.put(config.getNomePlataforma().toUpperCase(), config);
        
        System.out.println("Configuração adicionada: " + config.getNomePlataforma() + " no ambiente " + ambiente);
    }
    
    /**
     * Exibe todas as configurações do ambiente atual
     */
    public void exibirConfiguracoes() {
        System.out.println("\n📋 CONFIGURAÇÕES DO AMBIENTE: " + ambienteAtual);
        System.out.println("═".repeat(60));
        
        Map<String, ConfiguracaoPlataforma> configs = configuracoesPorAmbiente.get(ambienteAtual);
        
        for (ConfiguracaoPlataforma config : configs.values()) {
            String status = config.isAtiva() ? "✅ ATIVA" : "❌ INATIVA";
            System.out.printf("%-15s | %-10s | %-10s | %s%n",
                config.getNomePlataforma(),
                config.getTipoAutenticacao(),
                config.getTipoPublicacao(),
                status
            );
        }
        
        System.out.println("═".repeat(60));
    }
}
