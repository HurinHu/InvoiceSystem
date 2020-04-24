FROM php:7.2.9-apache
RUN docker-php-ext-install mysqli pdo pdo_mysql
RUN mkdir -p /etc/apache2/
RUN mkdir -p /etc/apache2/sites-available
RUN mkdir -p /etc/apache2/sites-enabled
RUN echo "" >> /etc/apache2/apache2.conf \
    && echo "# Include the configurations from the host machine" >> /etc/apache2/apache2.conf \
    && echo "IncludeOptional from-host/*.conf" >> /etc/apache2/apache2.conf
