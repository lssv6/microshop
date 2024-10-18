FROM mariadb:11-ubi AS base

# Copy the configs to the container.
COPY ./config /etc/my.cnf.d/

# Copy the certificates to the container.
COPY ./certificates/server-cert.pem /etc/my.cnf.d/certificates/server-cert.pem
COPY ./certificates/server-key.pem  /etc/my.cnf.d/certificates/server-key.pem
COPY ./certificates/ca.pem  /etc/my.cnf.d/certificates/ca.pem



 
