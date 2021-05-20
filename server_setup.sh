#!/bin/bash

echo "Installo i pacchetti necessari"
> deploy_output
{
    sudo apt-get update
    sudo apt-get upgrade
    curl -sL https://deb.nodesource.com/setup_16.x | sudo -E bash -
    sudo apt-get install default-jdk certbot nodejs mariadb-server git nginx -y
} >> deploy_output
echo "Setup dei pacchetti terminato"
echo "Inizio il setup di Tomcat"

#setup tomcat
{
sudo useradd -r -m -U -d /opt/tomcat -s /bin/false tomcat
wget https://downloads.apache.org/tomcat/tomcat-10/v10.0.6/bin/apache-tomcat-10.0.6.tar.gz -P /tmp
sudo tar xf /tmp/apache-tomcat-10*.tar.gz -C /opt/tomcat
sudo ln -s /opt/tomcat/apache-tomcat-10.0.6 /opt/tomcat/latest
sudo chown -RH tomcat: /opt/tomcat/latest
sudo sh -c 'chmod +x /opt/tomcat/latest/bin/*.sh'

sudo sh -c "cat >/etc/systemd/system/tomcat.service <<EOL
[Unit]
Description=Tomcat 10 servlet container
After=network.target

[Service]
Type=forking

User=tomcat
Group=tomcat

Environment=\"JAVA_HOME=/usr/lib/jvm/default-java\"
Environment=\"JAVA_OPTS=-Djava.security.egd=file:///dev/urandom -Djava.awt.headless=true\"

Environment=\"CATALINA_BASE=/opt/tomcat/latest\"
Environment=\"CATALINA_HOME=/opt/tomcat/latest\"
Environment=\"CATALINA_PID=/opt/tomcat/latest/temp/tomcat.pid\"
Environment=\"CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC\"

ExecStart=/opt/tomcat/latest/bin/startup.sh
ExecStop=/opt/tomcat/latest/bin/shutdown.sh

[Install]
WantedBy=multi-user.target

EOL"

sudo sed -i -e 's/8080/5000/g' /opt/tomcat/latest/conf/server.xml

sudo systemctl daemon-reload
sudo systemctl start tomcat
sudo systemctl enable tomcat
} >> deploy_output
echo "Setup di Tomcat completato"

echo "Inizio il setup del server MariaDB"
#setup mysql
sudo mysql_secure_installation
sudo sed -i -e 's/127.0.0.1/0.0.0.0/g' /etc/mysql/mariadb.conf.d/50-server.cnf
https://www.configserverfirewall.com/ubuntu-linux/mysql-allow-remote-connections/
echo "Setup del server terminato"

#GITHUB setup
echo "Genero la chiave RSA per GitHub"
ssh-keygen -t rsa -f $HOME/.ssh/id_rsa -b 4096 -C "ingsft" -N '' >> deploy_output
echo "Chiave RSA:"
echo ___________________
echo
cat $HOME/.ssh/id_rsa.pub
echo ___________________
echo "Copia la chiave per intero e inseriscila sulla sezione Deploy Keys del repository"
read -p "Premi INVIO quando hai fatto" -s
echo
echo "Clono il repository nella cartella '$HOME/ing-sft'..."
git clone git@github.com:albertorosati/ing-sft.git >> deploy_output
echo "Repository clonato con successo"

#React App setup
echo "Effettuo il setup dell'applicazione React"
{
    cd $home
    cd ing-sft
    npm install
    cd client
    npm install
    npm run build
} >> deploy_output
echo "Setup effettuato con successo"

echo "GENERAZIONE DEL CERTIFICATO HTTPS INIZIATA"
echo
echo
#generazione del certificato HTTPS
sudo certbot certonly --standalone --preferred-challenges http -d ingsft-signal.duckdns.org 

#Setup Server NGINX
sudo rm -f /etc/nginx/sites-available/default
sudo rm -f /etc/nginx/sites-enabled/default

sudo sh -c "cat >/etc/nginx/sites-available/ingsft <<EOL
upstream nodeprocess {
  server localhost:5000;
}
server {
  listen 443 http2 ssl;
  server_name ingsft-signal.duckdns.org;

  ssl_certificate /etc/letsencrypt/live/ingsft-signal.duckdns.org/fullchain.pem;
  ssl_certificate_key /etc/letsencrypt/live/ingsft-signal.duckdns.org/privkey.pem; 

  root ${HOME}/ing-sft/client/build;
  index index.html;
  
  location / {
    try_files \\\$uri /index.html =404;
  }
  location ^~ /api {
    proxy_pass http://nodeprocess;
  }
}
EOL"

sudo ln -s /etc/nginx/sites-available/ingsft /etc/nginx/sites-enabled >> deploy_output
sudo systemctl restart nginx
