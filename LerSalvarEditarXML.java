/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.caa.save;

import br.udesc.caa.objects.ItemDispositivoXML;
import br.udesc.caa.objects.ItemDispositivos;
import br.udesc.caa.objects.ItemImagemAcaoLista;
import br.udesc.caa.objects.ItemImagemAcaoXML;
import br.udesc.caa.objects.ItemImagemOpcAcaoLista;
import br.udesc.caa.objects.ItemImagemOpcAcaoXML;
import br.udesc.caa.objects.ItemImagemSujeitoLista;
import br.udesc.caa.objects.ItemImagemSujeitoXML;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Andrei Carniel Usando a biblioteca
 * http://x-stream.github.io/download.html Xtream Core Only
 */
public class LerSalvarEditarXML {

    private String path = "";
    //instancia um objeto para trabalhar com os Arquivos de Imagens
    public LerSalvarEditarFILES objFILES;

    public LerSalvarEditarXML(String path) {
        this.path = path;
        objFILES = new LerSalvarEditarFILES(path);
    }

    //salva um novo arquivo de XML de acordo com o caminh escolhido e a lista de itens de dispositivos
    public String salvarXMLMain(List<ItemDispositivoXML> listaDispositivos) {

        String retorno = "Ok";
        OutputStream outputStream = null;
        Writer writer = null;

        //armazena a lista de dispositivos no objeto principal
        ItemDispositivos objetoDispositivos = new ItemDispositivos();
        objetoDispositivos.setLista(listaDispositivos);

        //objeto para trabalhar com o XStrem XML.
        //converte oobjeto para xml
        final XStream xstream = new XStream(new DomDriver());
        //alterando a alias do root
        xstream.alias("dispositivos", ItemDispositivos.class);
        xstream.alias("dispositivo", ItemDispositivoXML.class);

        try {
            //cria o arquivo para salvar
            final File file = new File(path + "/" + "dispositivos.xml");
            
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
            //converte o objeto em xml e salva em uma string
            xstream.toXML(objetoDispositivos, writer);
            
            //encerra as variáveis
            outputStream.flush();
            outputStream.close();
            writer.flush();
            writer.close();

            return retorno;

        } catch (Exception ex) {
            //adiciona o erro a variável
            retorno = "Erro a o salvar: " + ex.getMessage();

            System.err.println(retorno);
            return retorno;
        }

    }
        
    
    //salva um novo arquivo de XML de sujeitos, de acordo com o caminho do sujeito escolhido
    public String salvarXMLSujeito(ItemImagemSujeitoLista objetoSujeito, String email) {

        String retorno = "Ok";
        final XStream xstream = new XStream(new DomDriver());
        OutputStream outputStream = null;
        Writer writer = null;
        
        try {
            //cria o arquivo para salvar
            final File file = new File(path + "/" + email + "/" + "sujeito.xml");

            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
            xstream.toXML(objetoSujeito, writer);
            
            //encerra as variáveis
            outputStream.flush();
            outputStream.close();
            writer.flush();
            writer.close();

            return retorno;

        } catch (Exception ex) {
            //adiciona o erro a variável
            retorno = "Erro a o salvar: " + ex.getMessage();

            System.err.println(retorno);
            return retorno;
        } 

    }
   
    
    //salva um novo arquivo de XML de ação, de acordo com o caminho do sujeito escolhido
    public String salvarXMLAcao(ItemImagemAcaoLista objetoAcao, String caminho) {

        String retorno = "Ok";

        //objeto para trabalhar com o XStrem XML.
        //converte oobjeto para xml
        final XStream xstream = new XStream(new DomDriver());
        OutputStream outputStream = null;
        Writer writer = null;
        
        try {
            //cria o arquivo para salvar
            final File file = new File(caminho + "/" +  "acao.xml");
            
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
            xstream.toXML(objetoAcao, writer);
            
            //encerra as variáveis
            outputStream.flush();
            outputStream.close();
            writer.flush();
            writer.close();

            return retorno;

        } catch (Exception ex) {
            //adiciona o erro a variável
            retorno = "Erro a o salvar: " + ex.getMessage();

            
            System.err.println(retorno);
            return retorno;
        } 

    }


    //salva um novo arquivo de XML de ação, de acordo com o caminho do sujeito escolhido
    public String salvarXMLOpcAcao(ItemImagemOpcAcaoLista objetoOpcAcao, String caminho) {

        String retorno = "Ok";
        final XStream xstream = new XStream(new DomDriver());
        OutputStream outputStream = null;
        Writer writer = null;
        
        try {
            //cria o arquivo para salvar
            final File file = new File(caminho + "/" +  "opc-acao.xml");
            
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
            xstream.toXML(objetoOpcAcao, writer);
            
            //encerra as variáveis
            outputStream.flush();
            outputStream.close();
            writer.flush();
            writer.close();
            
            return retorno;

        } catch (Exception ex) {
            //adiciona o erro a variável
            retorno = "Erro a o salvar: " + ex.getMessage();

            System.err.println(retorno);
            return retorno;
        } 
    }
   

    //realiza a leitura do arquivo de XML e devolte um List com os dados encontrados
    public List<ItemDispositivoXML> lerXMLMain() {

        String endereco = path + "/" + "dispositivos.xml";
        //tenta acessar o diretório passado por parâmetro
        final File arquivo = new File(endereco);

        //verifica a existência, se existir ele lê, se não, cria um novo
        if (arquivo.exists()) {
            //começa a leitura de arquivo
            try {
                //lendo o arquivo
                //FileReader file = new FileReader(endereco);
                final BufferedReader input = new BufferedReader(new InputStreamReader (new FileInputStream (endereco), "UTF-8"));

                final XStream xstream = new XStream(new DomDriver());
                xstream.alias("dispositivos", ItemDispositivos.class);
                xstream.alias("dispositivo", ItemDispositivoXML.class);

                //corverte de xml para um arquivo, o arquivo é um List
                ItemDispositivos dispositivosLista = (ItemDispositivos) xstream.fromXML(input);

                //cria lista de arquivos
                //List<DispositivoXMLItem> listaDispositivos = new ArrayList<DispositivoXMLItem>();

                input.close();
                
                return dispositivosLista.getLista();
            } catch (Exception ex) {
                System.out.println("Erro ao abrir a lista de dispositivos principais.\nErro: " + ex.getMessage());
                return null;
            }
        } else {
            //em caso de erro, ou lista inexistente retorna null
            
            return new ArrayList<ItemDispositivoXML>();
        }

    }
    
    //realiza a leitura do arquivo de XML e devolte um List com os dados encontrados
    public List<ItemImagemSujeitoXML> lerXMLSujeito(String email) {

        String endereco = path + "/" + email + "/" + "sujeito.xml";
        
        //tenta acessar o diretório passado por parâmetro
        final File arquivo = new File(endereco);
        
        //verifica a existência, se existir ele lê, se não, cria um novo
        if (arquivo.exists()) {
            //começa a leitura de arquivo
            try {
                //lendo o arquivo
                //FileReader file = new FileReader(endereco);
                
                final XStream xstream = new XStream(new DomDriver());
     
                final BufferedReader input = new BufferedReader(new InputStreamReader (new FileInputStream (endereco), "UTF-8"));
                
                ItemImagemSujeitoLista lista = (ItemImagemSujeitoLista) xstream.fromXML(input);
                
                input.close();
                
                return lista.getLista();
            } catch (Exception ex) {
                System.out.println("Erro ao abrir a lista de Sujeitos.\nErro: " + ex.getMessage());
                return null;
            }
        } else {
            //em caso de erro, ou lista inexistente retorna null
            return new ArrayList<ItemImagemSujeitoXML>();
             
        }

    }
    
    //realiza a leitura do arquivo de XML e devolte um List com os dados encontrados
    public List<ItemImagemAcaoXML> lerXMLAcao(String caminho) {

        String endereco = caminho + "/" + "acao.xml";
        //tenta acessar o diretório passado por parâmetro
        final File arquivo = new File(endereco);

        //verifica a existência, se existir ele lê, se não, cria um novo
        if (arquivo.exists()) {
            //começa a leitura de arquivo
            try {
                //lendo o arquivo
                //FileReader file = new FileReader(endereco);
                final BufferedReader input = new BufferedReader(new InputStreamReader (new FileInputStream (endereco), "UTF-8"));

                //converte de xml para um arquivo, o arquivo é um List
                XStream xstream = new XStream(new DomDriver());
                //ItemImagemAcaoLista lista = (ItemImagemAcaoLista) xstream.fromXML(file);
                ItemImagemAcaoLista lista = (ItemImagemAcaoLista) xstream.fromXML(input);

                input.close();
                
                return lista.getLista();
            } catch (Exception ex) {
                System.out.println("Erro ao abrir a lista de Ação.\nErro: " + ex.getMessage());
                return null;
            }
        } else {
            //em caso de erro, ou lista inexistente retorna null
            return new ArrayList<ItemImagemAcaoXML>();
        }

    }

    //realiza a leitura do arquivo de XML e devolte um List com os dados encontrados
    public List<ItemImagemOpcAcaoXML> lerXMLOpcAcao(String caminho) {

        String endereco = caminho + "/" + "opc-acao.xml";
        //tenta acessar o diretório passado por parâmetro
        final File arquivo = new File(endereco);

        //verifica a existência, se existir ele lê, se não, cria um novo
        if (arquivo.exists()) {
            //começa a leitura de arquivo
            try {
                //lendo o arquivo
                //FileReader file = new FileReader(endereco);
                final BufferedReader input = new BufferedReader(new InputStreamReader (new FileInputStream (endereco), "UTF-8"));
                
                //converte de xml para um arquivo, o arquivo é um List
                XStream xstream = new XStream(new DomDriver());
                //ItemImagemOpcAcaoLista lista = (ItemImagemOpcAcaoLista) xstream.fromXML(file);
                ItemImagemOpcAcaoLista lista = (ItemImagemOpcAcaoLista) xstream.fromXML(input);

                input.close();
                
                return lista.getLista();
            } catch (Exception ex) {
                System.out.println("Erro ao abrir a lista de opção de Ação.\nErro: " + ex.getMessage());
                return null;
            }
        } else {
            //em caso de erro, ou lista inexistente retorna null
            return new ArrayList<ItemImagemOpcAcaoXML>();
        }

    }
    
    //cria a lista para adicionar novo usuário/pasta CAA ou criar um arquivo xml
    public String novoCriarXML(ItemDispositivoXML disp) {
        List<ItemDispositivoXML> list = lerXMLMain();

        //verifica se a lista está vazia, se sim, o arquivo não existe ou está vazio
        if (list == null || list.isEmpty()) {
            //execeuta o processo de criar um novo arquivo e chamar a criação de um novo
            //inicializa o List
            list = new ArrayList<ItemDispositivoXML>();

            //adiciona o objeto passado por parâmetro com o novo id ao List
            list.add(disp);

            return salvarXMLMain(list);

        } else {
            //executa o processo de recuperar o ultimo ID, adicionar um novo item ao vetor, e salvar novamente
            //recupera o tamanho do array
            list.add(disp);

            return salvarXMLMain(list);

        }
    }
    
    //cria a lista para adicionar novo usuário ou criar um arquivo xml
    public String novoCriarSujeitoXML(ItemImagemSujeitoXML disp, String pathSujeitoXML) {
        List<ItemImagemSujeitoXML> list = lerXMLSujeito(pathSujeitoXML);
        
        ItemImagemSujeitoLista obj;

        //verifica se a lista está vazia, se sim, o arquivo não existe ou está vazio
        if (list == null || list.isEmpty()) {
            //execeuta o processo de criar um novo arquivo e chamar a criação de um novo
            //inicializa o List
            list = new ArrayList<ItemImagemSujeitoXML>();

            //adiciona o objeto passado por parâmetro com o novo id ao List
            list.add(disp);
            
            obj = new ItemImagemSujeitoLista();
            obj.setLista(list);

            return salvarXMLSujeito(obj, pathSujeitoXML);

        } else {
            //executa o processo de recuperar o ultimo ID, adicionar um novo item ao vetor, e salvar novamente
            //recupera o tamanho do array
            list.add(disp);

            obj = new ItemImagemSujeitoLista();
            obj.setLista(list);

            return salvarXMLSujeito(obj, pathSujeitoXML);

        }
    }
    
    //cria a lista para adicionar novo usuário ou criar um arquivo xml
    public String novoCriarAcaoXML(ItemImagemAcaoXML disp, String pathAcaoXML) {
        List<ItemImagemAcaoXML> list = lerXMLAcao(pathAcaoXML);
        
        ItemImagemAcaoLista obj;

        //verifica se a lista está vazia, se sim, o arquivo não existe ou está vazio
        if (list == null || list.isEmpty()) {
            //execeuta o processo de criar um novo arquivo e chamar a criação de um novo
            //inicializa o List
            list = new ArrayList<ItemImagemAcaoXML>();

            //adiciona o objeto passado por parâmetro com o novo id ao List
            list.add(disp);
            
            obj = new ItemImagemAcaoLista();
            obj.setLista(list);

            return salvarXMLAcao(obj, pathAcaoXML);

        } else {
            //executa o processo de recuperar o ultimo ID, adicionar um novo item ao vetor, e salvar novamente
            //recupera o tamanho do array
            list.add(disp);

            obj = new ItemImagemAcaoLista();
            obj.setLista(list);

            return salvarXMLAcao(obj, pathAcaoXML);

        }
    }

    //cria a lista para adicionar novo usuário ou criar um arquivo xml
    public String novoCriarOpcAcaoXML(ItemImagemOpcAcaoXML disp, String pathOpcAcaoXML) {
        List<ItemImagemOpcAcaoXML> list = lerXMLOpcAcao(pathOpcAcaoXML);
        
        ItemImagemOpcAcaoLista obj;

        //verifica se a lista está vazia, se sim, o arquivo não existe ou está vazio
        if (list == null || list.isEmpty()) {
            //execeuta o processo de criar um novo arquivo e chamar a criação de um novo
            //inicializa o List
            list = new ArrayList<ItemImagemOpcAcaoXML>();

            //adiciona o objeto passado por parâmetro com o novo id ao List
            list.add(disp);
            
            obj = new ItemImagemOpcAcaoLista();
            obj.setLista(list);

            return salvarXMLOpcAcao(obj, pathOpcAcaoXML);

        } else {
            //executa o processo de recuperar o ultimo ID, adicionar um novo item ao vetor, e salvar novamente
            //recupera o tamanho do array
            list.add(disp);

            obj = new ItemImagemOpcAcaoLista();
            obj.setLista(list);

            return salvarXMLOpcAcao(obj, pathOpcAcaoXML);

        }
    }
    
    //cria a lista para editar/criar um arquivo xml
    public void editarXML(ItemDispositivoXML disp) {
        List<ItemDispositivoXML> list = lerXMLMain();

        
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEmail().equals(disp.getEmail())) {
                list.get(i).setUsuario(disp.getUsuario());
                list.get(i).setTamanho(disp.getTamanho());
                list.get(i).setObservacao(disp.getObservacao());
                list.get(i).setCaminhoFoto(disp.getCaminhoFoto());
                break;
            }
        }
        
        salvarXMLMain(list);

    }

    //deleta um arquivo do list e salva
    public void excluirXMLItem(String email) {
        List<ItemDispositivoXML> list = lerXMLMain();

        //boolean d = list.remove(disp); //get(disp.getId() - 1) = disp;
        for (ItemDispositivoXML aux : list) {
            //list.get(aux.getId() + " - " + aux.getUsuario());

            if (aux.getEmail().equals(email)) {
                list.remove(aux);
                break;
            }
            //limpar a lista ou apenas salvar um novo elemento na lista???????? segunda opção parece mais viável
        }

        salvarXMLMain(list);
    }
    
    //deleta uma etrada de XML da lista de sujeitos
    public boolean excluirXMLSujeito(String email, String sujeito) {
        String log = "0";
        
        try{
            
            List<ItemImagemSujeitoXML> listSujeito = lerXMLSujeito(email);
            List<ItemImagemSujeitoXML> listSujeitoAUX = new ArrayList(listSujeito);
           
            //percorre a lista e elimina o sujeito escolhido
            for (ItemImagemSujeitoXML auxS : listSujeito) {
                if (auxS.getLegendaSujeito().equals(sujeito)) {
                    System.out.println("SUJEITO: " + auxS.getLegendaSujeito());
                    listSujeitoAUX.remove(auxS);
                    break;
                }
            }
            
            //Salva as listas
            //primeiro de Sujeito
            ItemImagemSujeitoLista listaSalvar = new ItemImagemSujeitoLista(listSujeitoAUX);
            salvarXMLSujeito(listaSalvar, email);
            
            return true;
        } catch(Exception e){
            System.out.println("Erro ao deletar XML do sujeito. Erro: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar XML do sujeito. Path: "+ path + " Email: " + email + ", Sujeito "+ sujeito + ", Cod = " + log +  "Erro: " + e.getMessage());
            return false;
        }
    }

    //deleta uma etrada de XML da lista de acoes
    public boolean excluirXMLAcao(String caminho, String acao) {
        
        try{
            List<ItemImagemAcaoXML> listAcao = lerXMLAcao(caminho);
            List<ItemImagemAcaoXML> listAcaoAUX = new ArrayList(listAcao);

            //percorre a lista e elimina a ação escolhida
            for (ItemImagemAcaoXML aux : listAcao) {
                System.out.println("SUJ-E: " + acao + " *** SUJ-A" + aux.getNomeAcao() + " *** LEGENDA_OPC: " + aux.getLegendaAcao() + " *** OPC_AÇÃO: "+ acao);
                if (aux.getLegendaAcao().equals(acao)) {
                    System.out.println("IF: True");
                    listAcaoAUX.remove(aux);
                    break;
                }
            }
            
            //salva alterações
            ItemImagemAcaoLista listaSalvarAcao = new ItemImagemAcaoLista(listAcaoAUX);
            salvarXMLAcao(listaSalvarAcao, caminho);
                                    
            return true;
        } catch(Exception e){
            System.out.println("Erro ao deletar XML da Ação. Erro: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar XML da Ação. Erro: " + e.getMessage());
            return false;
        }
    }
    
    //deleta uma etrada de XML da lista de acoes
    public boolean excluirXMLOpcAcao(String caminho, String opcAcao) {
        
        try{
            List<ItemImagemOpcAcaoXML> listOpcAcao = lerXMLOpcAcao(caminho);
            List<ItemImagemOpcAcaoXML> listOpcAcaoAUX = new ArrayList(listOpcAcao);

            //percorre a lista de opção de ação e elimina escolhida
            for (ItemImagemOpcAcaoXML aux : listOpcAcao) {
                if (aux.getLegendaOpcAcao().equals(opcAcao)) {
                    //System.out.println("IF: true");
                    listOpcAcaoAUX.remove(aux);
                    break;
                }
            }

            ItemImagemOpcAcaoLista listaSalvar = new ItemImagemOpcAcaoLista(listOpcAcaoAUX);
            salvarXMLOpcAcao(listaSalvar, caminho);
            
            return true;
        } catch(Exception e){
            System.out.println("Erro ao deletar XML da Opção de Ação. Erro: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar XML da Opção de Ação. Erro: " + e.getMessage());
            return false;
        }
    }
    
    //verificar se o e-mail cadastrado já está no servidor
    public boolean verificaEmailCadastrado(String email){
        List<ItemDispositivoXML> list = lerXMLMain();

        //boolean d = list.remove(disp); //get(disp.getId() - 1) = disp;
        for (ItemDispositivoXML aux : list) {
            //list.get(aux.getId() + " - " + aux.getUsuario());

            if (aux.getEmail().equals(email)) {
                return true;
            }
            //limpar a lista ou apenas salvar um novo elemento na lista???????? segunda opção parece mais viável
        }
        return false;
    }
    
    //função para criar o diretório principal da pasta de comunicação
    public boolean criarDiretorio(String email){
        String pathSujeito = path + "/" + email;
        String aux = ""; //usado no debug apenas...
        //URL caminhoSistema = getClass().getResource("/br/udesc/caa/images");
        //String caminhoImgSistema = String.valueOf(caminhoSistema).replace("file:/", "") + "/Não está aqui.png";
        //String caminhoImgSistema = "imagens/Não está aqui.png";
        
        //chama a função para criar o diretório
        boolean ret = objFILES.criaPrimeiroDiretorio(pathSujeito);
        
        if(ret){
            List<ItemImagemSujeitoXML> lsuj = new ArrayList<ItemImagemSujeitoXML>();
            //List<ItemImagemAcaoXML> laca = new ArrayList<ItemImagemAcaoXML>();
            //List<ItemImagemOpcAcaoXML> lop = new ArrayList<ItemImagemOpcAcaoXML>();
            
            aux = salvarXMLSujeito(new ItemImagemSujeitoLista(lsuj), email);
            //aux = salvarXMLAcao(new ItemImagemAcaoLista(laca), email);
            //aux = salvarXMLOpcAcao(new ItemImagemOpcAcaoLista(lop), email);
           
            try {
                //copia a imagem para a pasta, e define a posição 1
                //a posição 1 será gerenciada pelo aplicativo android
               objFILES.criaCopiaImagemComDiretorioSujeito(pathSujeito, 1, "Não está aqui.png", "Não está aqui.png", email);
               
            } catch (IOException ex) {
                System.out.println("Erro ao copiar imagem Não Está aqui, para a nova pasta. Erro: " + ex.getMessage());
            }
        }
        
        return ret;
        
    }

}
