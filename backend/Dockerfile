FROM java:8-jre
FROM tomcat

ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

RUN mkdir -p "$CATALINA_HOME"
WORKDIR $CATALINA_HOME

COPY ./build/libs/InvoiceSystem.war $CATALINA_HOME/webapps/ROOT.war
