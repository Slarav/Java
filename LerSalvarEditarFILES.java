/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.caa.save;

import br.udesc.caa.objects.ItemImagemAcaoLista;
import br.udesc.caa.objects.ItemImagemAcaoXML;
import br.udesc.caa.objects.ItemImagemOpcAcaoLista;
import br.udesc.caa.objects.ItemImagemOpcAcaoXML;
import br.udesc.caa.objects.ItemImagemSujeitoXML;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrei Carniel Usando a biblioteca
 *
 */
public class LerSalvarEditarFILES {

    private String path = "";

    public LerSalvarEditarFILES(String path) {
        this.path = path;
    }

    public LerSalvarEditarFILES() {
    }

    //copia imagem para a pasta do usuário e cria um diretório para a imagem para o Sujeito
    public String criaCopiaImagemComDiretorioSujeito(String endereco, int posicaoTela, String nomeImagem, String enderecoImagem, String email) throws IOException {

        String ret = "Ok::";
        String nomeImagemSE = "";
        
        String extensao = nomeImagem.substring((nomeImagem.length() - 4), nomeImagem.length());
        String extensaoLower = extensao.toLowerCase();
        nomeImagem = nomeImagem.replace(extensao, extensaoLower);

        if (nomeImagem.contains(".jpg") || nomeImagem.contains(".jpeg") || nomeImagem.contains(".png")) {
            try {

                //formata o destino de acordo com a imagem selecionada
                String destino = (endereco + "/" + nomeImagem);
                //salva a imagem
                final File auxImage = new File(enderecoImagem);
                final File auxDestino = new File(destino);
                Files.copy(auxImage.toPath(), auxDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                nomeImagemSE = removeEstensao(nomeImagem);

                //cria um diretório com esse nome
                final File diretorio = new File(endereco + "/" + nomeImagemSE);
                diretorio.mkdir();
                
                LerSalvarEditarXML xml = new LerSalvarEditarXML(diretorio.getAbsolutePath());
                String retorno = "";
                List<ItemImagemAcaoXML> laca = new ArrayList<ItemImagemAcaoXML>();
                retorno = xml.salvarXMLAcao(new ItemImagemAcaoLista(laca), diretorio.getAbsolutePath());

                //cria um objeto para salvar no xml
                LerSalvarEditarXML lse = new LerSalvarEditarXML(path);

                ItemImagemSujeitoXML aux = new ItemImagemSujeitoXML(posicaoTela, nomeImagem, nomeImagemSE, nomeImagem);
                //adiciona a imagem ao XML
                lse.novoCriarSujeitoXML(aux, email);

                return ret;
            } catch (Exception e) {
                ret = "Erro ao copiar Sujeito: " + e.getMessage();
                return ret;
            }
        } else {
            ret = "Por favor, utilize apenas os formatos JPG e PNG.";
            return ret;
        }

    }

    //copia imagem para a pasta do usuário e cria um diretório para a imagem para a Ação
    public String criaCopiaImagemComDiretorioAcao(String endereco, String nomeImagem, String enderecoImagem, String email, String sujeito) throws IOException {

        String ret = "Ok::";
        String nomeImagemSE = "";
        
        String extensao = nomeImagem.substring((nomeImagem.length() - 4), nomeImagem.length());
        String extensaoLower = extensao.toLowerCase();
        nomeImagem = nomeImagem.replace(extensao, extensaoLower);

        if (nomeImagem.contains(".jpg") || nomeImagem.contains(".jpeg") || nomeImagem.contains(".png")) {
            try {

                String destino = (endereco + "/" + sujeito + "/" + nomeImagem);
                //salva a imagem
                final File auxImage = new File(enderecoImagem);
                final File auxDestino = new File(destino);
                Files.copy(auxImage.toPath(), auxDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                nomeImagemSE = removeEstensao(nomeImagem);

                //cria um diretório com esse nome
                final File diretorio = new File(endereco + "/" + sujeito + "/" + nomeImagemSE);
                diretorio.mkdir();
                
                final LerSalvarEditarXML xml = new LerSalvarEditarXML(diretorio.getAbsolutePath());
                String retorno = "";
                List<ItemImagemOpcAcaoXML> lop = new ArrayList<ItemImagemOpcAcaoXML>();
                retorno = xml.salvarXMLOpcAcao(new ItemImagemOpcAcaoLista(lop), diretorio.getAbsolutePath());

                //cria um objeto para salvar no xml
                final LerSalvarEditarXML lse = new LerSalvarEditarXML(path);

                //String auxEndImg = sujeito + "/" + nomeImagem;
                ItemImagemAcaoXML aux = new ItemImagemAcaoXML(nomeImagem, nomeImagemSE, nomeImagem);
                //adiciona a imagem ao XML
                
                final File dirAux = new File(endereco + "/" + sujeito);
                lse.novoCriarAcaoXML(aux, dirAux.getAbsolutePath());

                return ret;
            } catch (Exception e) {
                ret = "Erro ao copiar Ação: " + e.getMessage();
                return ret;
            }
        } else {
            ret = "Por favor, utilize apenas os formatos JPG e PNG.";
            return ret;
        }

    }

    //copia imagem para a pasta do usuário e NÃO cria um diretório para a imagem
    public String criaCopiaImagemSemDiretorio(String endereco, String nomeImagem, String enderecoImagem, String caminho, String acao, String sujeito) throws IOException {

        String ret = "Ok::";

        String extensao = nomeImagem.substring((nomeImagem.length() - 4), nomeImagem.length());
        String extensaoLower = extensao.toLowerCase();
        nomeImagem = nomeImagem.replace(extensao, extensaoLower);
        
        if (nomeImagem.contains(".jpg") || nomeImagem.contains(".jpeg") || nomeImagem.contains(".png")) {
            try {

                //formata o destino de acordo com a imagem selecionada
                String destino = (endereco + "/" + sujeito + "/" + acao + "/" + nomeImagem);
                //salva a imagem
                final File auxImage = new File(enderecoImagem);
                final File auxDestino = new File(destino);
                Files.copy(auxImage.toPath(), auxDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                //cria um objeto para salvar no xml
                LerSalvarEditarXML lse = new LerSalvarEditarXML(path);
                //altera o endereço da imagem
                //String auxEndImg = sujeito + "/" + acao + "/" + nomeImagem;
                ItemImagemOpcAcaoXML aux = new ItemImagemOpcAcaoXML(nomeImagem, removeEstensao(nomeImagem), nomeImagem);

                //adiciona a imagem ao XML
                lse.novoCriarOpcAcaoXML(aux, caminho);

                return ret;
            } catch (Exception e) {
                ret = "Erro ao copiar: " + e.getMessage();
                return ret;
            }
        } else {
            ret = "Por favor, utilize apenas os formatos JPG e PNG.";
            return ret;
        }

    }

    //Edita imagem para a pasta do respectivo usuário e seu diretório
    //String caminhoDestinoImagem, String nomeAtual, String nomeComEstensao, String caminhoNovaImagem, String nomeNovaImagem) throws IOException {
    public String editaImagem(String legenda, String nomeComEstensao, String caminhoDestinoImage, String nomeNovaImagem, String caminhoNovaImagem) {

        String ret = "Ok::";

        //deletar
        //trocar o nome sem mexer na extensão
        //copiar a nova imagem
        
        if (caminhoNovaImagem.toLowerCase().contains(".jpg") || caminhoNovaImagem.toLowerCase().contains(".jpeg") || caminhoNovaImagem.toLowerCase().contains(".png")) {
            try {

                //cria o caminho final e deleta a imagem antiga
                //final File destImage = new File(caminhoDestinoImage);
                //destImage.delete();

                String auxNome = trocarNome(nomeNovaImagem, legenda);
                //formata o destino de acordo com a imagem selecionada
                String destino = caminhoDestinoImage.replace(nomeComEstensao, auxNome);

                final File fImagemOrigem = new File(caminhoNovaImagem);
                final File fImagemDestino = new File(destino);
                //copia
                Files.copy(fImagemOrigem.toPath(), fImagemDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                ret += auxNome;
                
                return ret;
            } catch (Exception e) {
                ret = "Erro ao copiar: " + e.getMessage();
                return ret;
            }
        } else {
            ret = "Por favor, utilize apenas os formatos JPG e PNG.";
            return ret;
        }

    }

    //Edita a legenda da imagem selecionada, bem como o respectivo diretório
    //editaLegenda(enderecoSujeito, nomeAtual, nomeAtualCompleto, novoNome, novoNomeCompleto)
    public String editaLegenda(String caminho, String nomeAtual, String nomeAtualCompleto, String novoNome, String novoNomeCompleto) {

        String ret = "Ok::";

        try {

            //renomeando a imagem
            final File arqAtual = new File(caminho + "/" + nomeAtualCompleto);
            final File arqNovo = new File(caminho + "/" + novoNomeCompleto);
            boolean arq = arqAtual.renameTo(arqNovo);

            //se deu erro, para aqui e retorna para o software
            if(!arq){
                ret = "Erro: Não foi possível renomear o arquivo. Tente novamente";
            } else {

                //renomeando o diretório
                final File dirAtual = new File(caminho + "/" + nomeAtual);
                final File dirNovo = new File(caminho + "/" + novoNome);
                final boolean dir = dirAtual.renameTo(dirNovo);

                //se deu erro, retorna a imagem ao nome anterior para
                if (!dir){
                    ret = "Erro: Não foi possível renomear o diretório. Tente novamente";
                    arq = arqNovo.renameTo(arqAtual);
                } 
            }
            
            //Retorna ok se não deu erro
            return ret;
            
        } catch (Exception e) {
            ret = "Erro ao alterar o nome ou pasta. Erro: " + e.getMessage();
            return ret;
        }
    }
    
    //Edita a legenda da imagem selecionada
    //editaLegenda(enderecoSujeito, nomeAtual, nomeAtualCompleto, novoNome, novoNomeCompleto)
    public String editaLegendaOpcAcao(String caminho, String nomeAtualCompleto, String novoNomeCompleto) {

        String ret = "Ok::";

        try {

            //renomeando a imagem
            final File arqAtual = new File(caminho + "/" + nomeAtualCompleto);
            final File arqNovo = new File(caminho + "/" + novoNomeCompleto);
            final boolean arq = arqAtual.renameTo(arqNovo);

            if(!arq){
                ret = "Erro: Não foi possível renomear o arquivo. Tente novamente";
            }
            
            return ret;

        } catch (Exception e) {
            ret = "Erro ao alterar o nome ou pasta. Erro: " + e.getMessage();
            return ret;
        }
    }

    //Exclui a imagem selecionada, para a OPÇÃO DE AÇÃO
    public String excluirImagem(String caminho, String nomeCompleto) {
        String ret = "Ok::";

        try {

            //criando arquivos
            final File arq = new File(caminho + "/" + nomeCompleto);

            //deletando arquivos
            arq.delete();

            //se nenhum erro ocorreu, retorna ok
            return ret;
        } catch (Exception e) {
            ret = "Erro ao alterar o nome ou pasta. Erro: " + e.getMessage();
            return ret;
        }
    }

    //Exclui a imagem e o diretório para a imagem selecionada, para SUJEITO e AÇÃO
    public String excluirImagemEDiretorio(String caminho, String nome, String nomeCompleto) {

        String ret = "Ok::";

        try {

            //criando arquivos
            final File arq = new File(caminho + "/" + nomeCompleto);
            final File dir = new File(caminho + "/" + nome);

            //deletando arquivos
            arq.delete();
            removerArquivos(dir);

            //se nenhum erro ocorreu, retorna ok
            return ret;
        } catch (Exception e) {
            ret = "Erro ao deletar a pasta. Erro: " + e.getMessage();
            return ret;
        }
    }

    //excluí todos os arquivos do usuário
    public String excluirUsuario(String caminho, String imagem) {
        String ret = "Ok::";

        try {

            //criando arquivos
            final File dir = new File(caminho);
            final File arq = new File(imagem);
            //deletando arquivos
            removerArquivos(dir);
            arq.delete();

            //se nenhum erro ocorreu, retorna ok
            return ret;
        } catch (Exception e) {
            ret = "Erro ao alterar o nome ou pasta. Erro: " + e.getMessage();
            return ret;
        }
    }
    
    //copia todos os arquivos do usuário
    public String copiarUsuario(String origem, String destino) {
        String ret = "Ok::";

        final File f = new File(destino);

        boolean retorno = f.mkdir();

        try {
            if(retorno){
                copiarRecursivo(origem, origem, destino);
                return ret;
            } else {
                return "Erro, não foi possível criar diretório, verifique se outros programas não estão utilizando esta pasta.";
            }
        } catch (IOException ex) {
            Logger.getLogger(LerSalvarEditarFILES.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

    /**copia rquivos recursivamente
     * @param origemMain endereço da pasta do usuário
     * @param origemNew endereço das pastas e subpastas presentes dentro de origemMain
     * @param destino endereço da pasta do novo usuário
    */
    public void copiarRecursivo(String origemMain, String origemNew, String destino) throws IOException {
        
        final File f = new File(origemNew);
        final File[] files = f.listFiles();

        for (File file : files) {
            String caminhoOrigem = file.getAbsolutePath();
            caminhoOrigem = caminhoOrigem.replace("\\", "/");
            String caminhoDestino = caminhoOrigem.replace(origemMain, destino);
            File aux = new File(caminhoDestino);
            if (file.isDirectory()) {
                boolean a = aux.mkdirs();
                System.out.println("A: " + a);
                copiarRecursivo(origemMain, caminhoOrigem, destino);
            } else {
                System.out.println("Copy: " + Files.copy(file.toPath(), aux.toPath(), StandardCopyOption.REPLACE_EXISTING));
            }
        }
    }

    //função para remover a extensão dos arquivos png e jpg
    public String removeEstensao(String nome) {
        
        String extensao = nome.substring((nome.length() - 4), nome.length());
        
        String aux = nome.replace(extensao, "");

        return aux;
    }

    //função para excluir recursivamente o diretório e todos os arquivos de dentro do diretório
    public void removerArquivos(File f) throws Exception {
        if (f.isDirectory()) {
            final File[] files = f.listFiles();
            for (File file : files) {
                removerArquivos(file);
            }
        }
        f.delete();
    }

    //função para remover a extensão dos arquivos png e jpg
    public String trocarNome(String nomeAtual, String nomeNovo) {

        String extensao = nomeAtual.substring((nomeAtual.length() - 4), nomeAtual.length());
        String aux = "";
        if (extensao.toLowerCase().contains(".jpg")) {
            aux = nomeNovo + ".jpg";
        } else if (extensao.toLowerCase().contains(".png")) {
            aux = nomeNovo + ".png";
        } else if (extensao.toLowerCase().contains(".jpeg")) {
            aux = nomeNovo + ".jpeg";
        }

        return aux;
    }

    //adiciona uma foto ao usuário
    public String adicionaFotoUsuario(String endereco, String email, String caminhoImagemAtual, String nomeImagem, String enderecoImagem) {

        String ret = "Ok::";

        if (nomeImagem.toLowerCase().contains(".jpg") || nomeImagem.toLowerCase().contains(".jpeg") || nomeImagem.toLowerCase().contains(".png")) {
            try {

                String auxNome = trocarNome(nomeImagem, email);
                //formata o destino de acordo com a imagem selecionada
                String destino = (endereco + "/" + auxNome);

                //salva a imagem
                final File fImagemDeletar = new File(caminhoImagemAtual);
                final File fImagemOrigem = new File(enderecoImagem);
                final File fImagemDestino = new File(destino);
                fImagemDeletar.delete();
                Files.copy(fImagemOrigem.toPath(), fImagemDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                //retorna o caminho
                ret = ret + destino;

                return ret;
            } catch (Exception e) {
                ret = "Erro ao copiar para o usuário: " + e.getMessage();
                return ret;
            }

        } else {
            ret = "Por favor, utilize apenas os formatos JPG e PNG.";
            return ret;
        }

    }

    public String adicionaFotoUsuario(String endereco, String email, String nomeImagem, String enderecoImagem) {

        String ret = "Ok::";
        
        if (nomeImagem.contains(".jpg") || nomeImagem.contains(".jpeg") || nomeImagem.contains(".png")) {
            try {

                String auxNome = trocarNome(nomeImagem, email);
                //formata o destino de acordo com a imagem selecionada
                String destino = (endereco + "/" + auxNome);

                //salva a imagem
                final File fImagemOrigem = new File(enderecoImagem);
                final File fImagemDestino = new File(destino);
                Files.copy(fImagemOrigem.toPath(), fImagemDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                //retorna o caminho
                ret = ret + destino;

                return ret;
            } catch (Exception e) {
                ret = "Erro ao copiar para o usuário: " + e.getMessage();
                return ret;
            }

        } else {
            ret = "Por favor, utilize apenas os formatos JPG e PNG.";
            return ret;
        }
    }

    //cria o arquivo config.db, esse arquivo possui apenas o diretório com as imagens 
    public boolean criarConfigDB(String nPath) {

        //tentando criar arquivo
        try {
            Formatter saida = new Formatter("config.db");
            saida.format(nPath);
            saida.close();
            //se ococrreu tudo bem, retorna true
            return true;
        } //mostrando erro em caso se nao for possivel gerar arquivo
        catch (Exception erro) {
            //se deur erro, imprime o erro e retorna false
            System.out.println("Erro ao criar o arquivo config.db . Erro: " + erro.getMessage());
            return false;
        }
    }

    //procura o arquivo de configuração
    public String lerConfigDB() {
        String ret = "";
        String linha = "";
        String path = "config.db";
        final File arq = new File(path);

        //Arquivo existe
        if (arq.exists()) {
            //tentando ler arquivo
            try {

                //abrindo arquivo para leitura
                final FileReader reader = new FileReader(path);
                //leitor do arquivo
                final BufferedReader leitor = new BufferedReader(reader);
                while (true) {
                    linha = leitor.readLine();
                    if (linha == null) {
                        break;
                    }
                    ret += linha;
                }
                
                leitor.close();
            } catch (Exception e) {
                ret = "Erro ao ler config.db";
            }

            return ret;
        } else {
            ret = "config.db não existe";
            return ret;
        }

    }

    //função para criar um diretóio com base em um endereço qualquer
    boolean criaPrimeiroDiretorio(String pathDestino) {
        final File diretorio = new File(pathDestino);

        boolean ret = diretorio.mkdirs();

        return ret;
    }

}
