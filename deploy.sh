git pull
cd Server
mv Signal.war /opt/tomcat/latest/webapps
cd ..
cd Client
npm i -f
npm run build