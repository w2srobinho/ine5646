ine5646
=======

Projetos feitos na aula INE5616 - Programação Web da UFSC

# Infraestrutura:
  Para os trabalhos são necessários os seguintes programas:

### Instalar a JVM  

1. Para baixar o JDK, execute o comando:

        wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u11-b12/jdk-8u11-linux-x64.tar.gz

2. Execute o comando `tar zxf jdk-8u11-linux-x64.tar.gz` para descompactar o arquivo. Será criado o diretório `jdk1.8.0_11` e dentro dele o JDK estará instalado
3. Atualize o arquivo `.profile` que está no seu diretório `home` adicionando a variável de ambiente `JAVA_HOME` e a inclua na variável `PATH`. Você deve adicionar  as seguintes linhas no final do arquivo `.profile`:    

        export JAVA_HOME=<diretório onde o jdk foi instalado. Exemplo: export JAVA_HOME=/home/leandro.komosinski/jdk1.8.0_11
        export PATH=$JAVA_HOME/bin:$PATH

4. Execute o comando `source .profile`  e depois verifique se tudo deu certo executando `javac -version`  e `java -version`

### Instalar Tomcat

##### Faça a instalação do servidor Tomcat 8.

Após instalado, coloque o servidor no ar executando o script `startup.sh`. Teste se o servidor está acessível digitando, em um browser, `http://<sua máquina virtual>:8080` . Deverá aparecer a página inicial do Tomcat.

##### Instalação segura de aplicação no Tomcat

Configure o Tomcat de modo que este aceite conexões usando o protocolo HTTPS (HTTP Seguro).

*Dicas:*
* Configure o Tomcat conforme as instruções contidas em [How To ssl][ssl-howto].
* Usar o formato JKS
* Usar a implementação JSSE de SSL
* Configure a aplicação manager conforme as instruções contidas em [Manager How To][manager-howto].
* Para testar se tudo deu certo:
  * Crie uma aplicação de teste (`teste.war`)
  * Acesse o Tomcat de forma segura (`https://<host>:8443`).
  * Acesse a aplicação manager e faça upload do arquivo `.war`
  * Acesse a aplicação para verificar se ela está funcionando corretamente
  * Feito o teste, tire o servidor do ar executando o script `shutdown.sh`

### Instalar MySQL

1. Digite `sudo apt-get install mysql-server` para instalar o servidor MySQL. Durante a instalação será pedido uma *senha* para o *usuário root* (administrador do banco). Não esqueça a senha que você digitou.
2. Digite `sudo apt-get install mysql-client` para instalar um cliente MySQL.
3. Para testar se a instalação foi feita corretamente acesse o banco digitando `mysql -u root` ou então `mysql -u root -p`, caso tenha colocado uma senha.

O Manual de Referência do MySQL está disponível on-line em [mysql doc][mysql-doc]
Se você esquecer a senha de root siga as instruções contidas neste [site][mysql-root-pass]

### Instalar Glassfish

1. Digite `wget http://download.java.net/glassfish/4.0/release/glassfish-4.0.zip`
2. Copie o arquivo baixado para o diretório onde você deseja fazer a instalação.
3. Vá para o diretório de instalação, onde o arquivo foi copiado.
4. Descompacte o arquivo `unzip -qq glassfish-4.0.zip`

O Glassfish agora está instalado dentro do diretório glassfish4.

##### Colocando o servidor no ar

Vá até o diretório de instalação e digite `./bin/asadmin start-domain`
Obs: note o "." antes de /bin/...

##### Tirando o servidor do ar

Vá até o diretório de instalação e digite `./bin/asadmin stop-domain`

##### Definindo uma senha para o administrador

Com o servidor no ar, digite `./bin/asadmin change-admin-password --domain_name domain1`, tecle <enter> quando for solicitada a senha atual do administrador, em seguida digite a senha desejada e sua confirmação.

##### Habilitando a administração remota

Com o servidor no ar, digite `./bin/asadmin enable-secure-admin --port 4848` e siga as instruções.

Em seguida tire o servidor do ar e coloque-o no ar novamente. Agora a interface web de administração remota do servidor está disponível na porta 4848.

A documentação do Glassfish está disponível em [Glassfish doc][glassfish-doc]

### Instalar Play

1. Execute o comando  `wget http://downloads.typesafe.com/typesafe-activator/1.2.8/typesafe-activator-1.2.8.zip`
2. Descompacte o arquivo executando `unzip typesafe-activator-1.2.8.zip`
3. Edite o arquivo `.profile` (que fica no seu home) de modo a incluir a variável de ambiente `ACTIVATOR_HOME`. Esta variável deve apontar para o diretório *activator-1.2.8*
4. Edite o arquivo `.profile` de modo a incluir `ACTIVATOR_HOME` no `PATH`
5. Execute o comando `source .profile`  para ativar as modificações
6. Teste se tudo deu certo executando o comando `activator --version`, a resposta será `sbt launcher version 0.13.5`

[jdk8]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[ssl-howto]: http://tomcat.apache.org/tomcat-8.0-doc/ssl-howto.html
[manager-howto]: http://tomcat.apache.org/tomcat-8.0-doc/manager-howto.html
[mysql-doc]: http://dev.mysql.com/doc/
[mysql-root-pass]: http://www.geekride.com/recover-mysql-root-password/
[glassfish-doc]: https://glassfish.java.net/documentation.html