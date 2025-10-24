import config.GerenciadorConfiguracao;
import factory.ConfiguracaoPlataforma;
import factory.SocialMediaFactory;
import interfaces.GerenciadorMidiaSocial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.*;
import strategy.*;

/**
 * Classe principal de demonstração do sistema de gerenciamento de redes sociais
 * Demonstra o uso dos padrões Adapter, Strategy e Factory Method
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GERENCIAMENTO DE REDES SOCIAIS                    ║");
        System.out.println("║   Padrões: Adapter + Strategy + Factory Method                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        try {
            // Demonstração 1: Uso básico com Factory
            demonstrarUsoBasico();
            
            // Demonstração 2: Configuração por ambiente
            demonstrarConfiguracaoPorAmbiente();
            
            // Demonstração 3: Publicação em múltiplas plataformas
            demonstrarPublicacaoMultiplataforma();
            
            // Demonstração 4: Diferentes estratégias
            demonstrarEstrategias();
            
            // Demonstração 5: Estatísticas
            demonstrarEstatisticas();
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("DEMONSTRAÇÃO CONCLUÍDA COM SUCESSO!\n");

    }
    
    /**
     * Demonstração 1: Uso básico do sistema com Factory
     */
    private static void demonstrarUsoBasico() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("DEMONSTRAÇÃO 1: USO BÁSICO COM FACTORY");
        System.out.println("═".repeat(70));
        
        // Criar gerenciador usando Factory
        GerenciadorMidiaSocial twitter = SocialMediaFactory.criarGerenciadorPadrao("TWITTER");
        
        // Criar credenciais
        Credenciais credenciais = new Credenciais(
            "agencia_marketing",
            "twitter_api_key_123",
            "twitter_api_secret_456",
            "oauth_token_789",
            "refresh_token_abc"
        );
        
        // Autenticar
        twitter.autenticar(credenciais);
        
        // Criar conteúdo
        Conteudo conteudo = new Conteudo(
            "Testando o sistema de gerenciamento de redes sociais!",
            Arrays.asList("imagem1.jpg", "imagem2.jpg"),
            Arrays.asList("#marketing", "#socialmedia", "#tecnologia"),
            LocalDateTime.now(),
            TipoConteudo.TEXTO
        );
        
        // Validar e publicar
        if (twitter.validarConteudo(conteudo)) {
            Publicacao pub = twitter.publicar(conteudo);
            System.out.println("\nPublicação criada: " + pub);
        }
    }
    
    /**
     * Demonstração 2: Configuração dinâmica por ambiente
     */
    private static void demonstrarConfiguracaoPorAmbiente() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("DEMONSTRAÇÃO 2: CONFIGURAÇÃO POR AMBIENTE");
        System.out.println("═".repeat(70));
        
        GerenciadorConfiguracao config = GerenciadorConfiguracao.getInstance();
        
        // Ambiente de desenvolvimento
        config.setAmbiente("DESENVOLVIMENTO");
        config.exibirConfiguracoes();
        
        // Ambiente de produção
        config.setAmbiente("PRODUCAO");
        config.exibirConfiguracoes();
        
        // Criar gerenciador baseado na configuração
        ConfiguracaoPlataforma configInstagram = config.obterConfiguracao("INSTAGRAM");
        GerenciadorMidiaSocial instagram = SocialMediaFactory.criarDaConfiguracao(configInstagram);
        
        System.out.println("\nGerenciador Instagram criado a partir da configuração de PRODUÇÃO");
    }
    
    /**
     * Demonstração 3: Publicação em múltiplas plataformas
     */
    private static void demonstrarPublicacaoMultiplataforma() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("DEMONSTRAÇÃO 3: PUBLICAÇÃO EM MÚLTIPLAS PLATAFORMAS");
        System.out.println("═".repeat(70));
        
        // Criar lista de gerenciadores
        List<GerenciadorMidiaSocial> plataformas = Arrays.asList(
            SocialMediaFactory.criarGerenciadorPadrao("TWITTER"),
            SocialMediaFactory.criarGerenciadorPadrao("INSTAGRAM"),
            SocialMediaFactory.criarGerenciadorPadrao("LINKEDIN")
        );
        
        // Credenciais diferentes para cada plataforma
        List<Credenciais> credenciaisList = Arrays.asList(
            new Credenciais("agencia", "tw_key", "tw_secret", "tw_token", "tw_refresh"),
            new Credenciais("agencia", "ig_key", "ig_secret", "ig_token", "ig_refresh"),
            new Credenciais("agencia", "li_key", "li_secret", "li_token", "li_refresh")
        );
        
        // Autenticar em todas as plataformas
        for (int i = 0; i < plataformas.size(); i++) {
            plataformas.get(i).autenticar(credenciaisList.get(i));
        }
        
        // Criar conteúdo adaptado para cada plataforma
        System.out.println("\nPublicando conteúdo em todas as plataformas...\n");
        
        // Twitter - texto curto
        Conteudo conteudoTwitter = new Conteudo(
            "Lançamento do nosso novo produto! #inovacao #tecnologia",
            Arrays.asList("produto.jpg"),
            Arrays.asList("#inovacao", "#tecnologia"),
            LocalDateTime.now(),
            TipoConteudo.TEXTO
        );
        
        // Instagram - foco em imagem
        Conteudo conteudoInstagram = new Conteudo(
            "✨ Apresentamos nosso mais novo produto! Revolucione seu dia a dia com tecnologia de ponta.",
            Arrays.asList("produto_hd.jpg"),
            Arrays.asList("#inovacao", "#tecnologia", "#produto", "#lancamento"),
            LocalDateTime.now(),
            TipoConteudo.IMAGEM
        );
        
        // LinkedIn - profissional
        Conteudo conteudoLinkedIn = new Conteudo(
            "Estamos orgulhosos de anunciar o lançamento do nosso mais novo produto, " +
            "resultado de meses de pesquisa e desenvolvimento. Esta solução inovadora " +
            "promete transformar a maneira como empresas gerenciam seus processos.",
            Arrays.asList("produto_profissional.jpg"),
            Arrays.asList("#inovacao", "#business"),
            LocalDateTime.now(),
            TipoConteudo.TEXTO
        );
        
        List<Conteudo> conteudos = Arrays.asList(conteudoTwitter, conteudoInstagram, conteudoLinkedIn);
        
        // Publicar em todas as plataformas
        List<Publicacao> publicacoes = new ArrayList<>();
        for (int i = 0; i < plataformas.size(); i++) {
            GerenciadorMidiaSocial plataforma = plataformas.get(i);
            Conteudo conteudo = conteudos.get(i);
            
            if (plataforma.validarConteudo(conteudo)) {
                Publicacao pub = plataforma.publicar(conteudo);
                publicacoes.add(pub);
                System.out.println("Publicado em " + plataforma.getNomePlataforma());
            }
        }
        
        System.out.println("\nTotal de publicações: " + publicacoes.size());
    }
    
    /**
     * Demonstração 4: Diferentes estratégias de publicação e autenticação
     */
    private static void demonstrarEstrategias() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("DEMONSTRAÇÃO 4: DIFERENTES ESTRATÉGIAS");
        System.out.println("═".repeat(70));
        
        // Estratégia 1: Publicação Imediata com OAuth2
        System.out.println("\nEstratégia 1: Publicação Imediata + OAuth2");
        GerenciadorMidiaSocial gerenciador1 = SocialMediaFactory.criarGerenciador(
            "TWITTER",
            new AutenticacaoOAuth2(),
            new PublicacaoImediata()
        );
        
        Credenciais cred1 = new Credenciais("user1", "key1", "secret1", "token1", "refresh1");
        gerenciador1.autenticar(cred1);
        
        Conteudo conteudo1 = new Conteudo(
            "Publicação imediata no Twitter!",
            new ArrayList<>(),
            Arrays.asList("#now"),
            LocalDateTime.now(),
            TipoConteudo.TEXTO
        );
        gerenciador1.publicar(conteudo1);
        
        // Estratégia 2: Publicação Agendada com API Key
        System.out.println("\nEstratégia 2: Publicação Agendada + API Key");
        GerenciadorMidiaSocial gerenciador2 = SocialMediaFactory.criarGerenciador(
            "INSTAGRAM",
            new AutenticacaoApiKey(),
            new PublicacaoAgendada()
        );
        
        Credenciais cred2 = new Credenciais("user2", "apikey123", "", "token2", "");
        gerenciador2.autenticar(cred2);
        
        Conteudo conteudo2 = new Conteudo(
            "Post agendado para amanhã!",
            Arrays.asList("foto.jpg"),
            Arrays.asList("#scheduled"),
            LocalDateTime.now().plusDays(1),
            TipoConteudo.IMAGEM
        );
        gerenciador2.agendar(conteudo2);
        
        // Estratégia 3: JWT
        System.out.println("\nEstratégia 3: JWT Authentication");
        GerenciadorMidiaSocial gerenciador3 = SocialMediaFactory.criarGerenciador(
            "TIKTOK",
            new AutenticacaoJWT(),
            new PublicacaoImediata()
        );
        
        Credenciais cred3 = new Credenciais("user3", "", "", "jwt_token_xyz", "");
        gerenciador3.autenticar(cred3);
    }
    
    /**
     * Demonstração 5: Obtenção de estatísticas
     */
    private static void demonstrarEstatisticas() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("DEMONSTRAÇÃO 5: ESTATÍSTICAS E MÉTRICAS");
        System.out.println("═".repeat(70));
        
        // Criar e autenticar gerenciadores
        GerenciadorMidiaSocial twitter = SocialMediaFactory.criarGerenciadorPadrao("TWITTER");
        GerenciadorMidiaSocial instagram = SocialMediaFactory.criarGerenciadorPadrao("INSTAGRAM");
        
        Credenciais cred = new Credenciais("stats_user", "key", "secret", "token", "refresh");
        twitter.autenticar(cred);
        instagram.autenticar(cred);
        
        // Publicar conteúdo
        Conteudo conteudo = new Conteudo(
            "Post de teste para métricas",
            Arrays.asList("image.jpg"),
            Arrays.asList("#test"),
            LocalDateTime.now(),
            TipoConteudo.TEXTO
        );
        
        Publicacao pubTwitter = twitter.publicar(conteudo);
        
        Conteudo conteudoIG = new Conteudo(
            "Post de teste no Instagram",
            Arrays.asList("image.jpg"),
            Arrays.asList("#test"),
            LocalDateTime.now(),
            TipoConteudo.IMAGEM
        );
        Publicacao pubInstagram = instagram.publicar(conteudoIG);
        
        // Obter estatísticas
        System.out.println("\nESTATÍSTICAS DAS PUBLICAÇÕES:\n");
        
        Estatisticas statsTwitter = twitter.obterEstatisticas(pubTwitter.getId());
        System.out.println("Twitter: " + statsTwitter);
        
        Estatisticas statsInstagram = instagram.obterEstatisticas(pubInstagram.getId());
        System.out.println("Instagram: " + statsInstagram);
        
        // Listar todas as publicações
        System.out.println("\nLISTA DE PUBLICAÇÕES:\n");
        
        List<Publicacao> pubsTwitter = twitter.listarPublicacoes();
        System.out.println("Twitter: " + pubsTwitter.size() + " publicações");
        
        List<Publicacao> pubsInstagram = instagram.listarPublicacoes();
        System.out.println("Instagram: " + pubsInstagram.size() + " publicações");
    }
}
